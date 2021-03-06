<%@ page import="kopo.poly.dto.ChatRoomDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.BookDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    ChatRoomDTO cDTO = (ChatRoomDTO) request.getAttribute("room");


    if(cDTO == null) {
        cDTO = new ChatRoomDTO();
    }

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!--j쿼리 -->
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>


    <%
        if(session.getAttribute("SS_USER_ID") == null) { //로그인이 안되었을때
            //로그인 화면으로 이동
            response.sendRedirect("/user/loginForm");
        } else {
    %>
    <script>
        //채팅방 전체 대화 가져오기
        function getAllMsg() {


        }

        // 대화 메시지 전체 출력하기
        function doOutputMsg(json) {

            // 메시지 대화가 존재하면 출력
            if (json != null) {

                let totalMsg = "";

                for (let i = 0; i < json.length; i++) {
                    totalMsg += (json[i].message + " | " + json[i].user_id + " | " + json[i].roomid + "<br/>");
                }

                // 채팅방마다 한줄씩 추가
                $("#total_msg").html(totalMsg);
            }
        }
    </script>
    <script>

        $(document).ready(function(){

            var roomName = "<%= cDTO.getName()%>";
            var roomId = "<%=cDTO.getRoomid()%>";
            var username = "<%= session.getAttribute("SS_USER_ID")%>";

            console.log(roomId + ", " + username);

            var sockJs = new SockJS("/stomp/chat");
            //1. SockJS를 내부에 들고있는 stomp를 내어줌
            var stomp = Stomp.over(sockJs);

            //2. connection이 맺어지면 실행
            stomp.connect({}, function (){
                console.log("STOMP Connection")

                //4. subscribe(path, callback)으로 메세지를 받을 수 있음
                stomp.subscribe("/sub/chat/room/" + roomId, function (chat) {
                    console.log("메세지 받음");
                    let content = JSON.parse(chat.body);

                    let writer = content.user_id;
                    let message = content.message;
                    let str = '';

                    if(writer === username){
                        str = "<div class='col-6'>";
                        str += "<div class='alert alert-secondary'>";
                        str += "<b>" + writer + " : " + message + "</b>";
                        str += "</div></div>";
                        $("#msgArea").append(str);
                    }
                    else{
                        str = "<div class='col-6'>";
                        str += "<div class='alert alert-warning'>";
                        str += "<b>" + writer + " : " + message + "</b>";
                        str += "</div></div>";
                        $("#msgArea").append(str);
                    }
                });

                //3. send(path, header, message)로 메세지를 보낼 수 있음
                stomp.send('/pub/chat/enter', {}, JSON.stringify({roomid: roomId, user_id: username}))
            });


            $("#button-send").on("click", function(e){
                var msg = document.getElementById("msg");

                console.log(username + ":" + msg.value);
                stomp.send('/pub/chat/message',  {}, JSON.stringify({roomid: roomId, message: msg.value, user_id: username}));
                msg.value = '';
            });


        });
    </script>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>채팅</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="/main/main">COQUAT</a>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
    <!-- Navbar Search-->
    <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
        <div class="input-group">
            <input class="form-control" type="hidden" placeholder="Search for..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
        </div>
    </form>
    <!-- Navbar-->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="/main/UserSetting">마이페이지</a></li>
                <li><hr class="dropdown-divider" /></li>
                <li><a class="dropdown-item" href="/user/Logout">로그아웃</a></li>
            </ul>
        </li>
    </ul>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <div class="sb-sidenav-menu-heading">기능</div>
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                        <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                        예약
                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                        <nav class="sb-sidenav-menu-nested nav">
                            <a class="nav-link" href="/product/ProductList">예약</a>
                            <a class="nav-link" href="/product/BookResult">예약확인</a>
                        </nav>
                    </div>
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                        <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                        동일업종조회
                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="collapsePages" aria-labelledby="headingTwo" data-bs-parent="#sidenavAccordion">
                        <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
                                동일업종조회
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="pagesCollapseAuth" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="/main/Food">동일업종조회</a>
                                    <a class="nav-link" href="/main/BookMap">주변동일업종조회</a>
                                </nav>
                            </div>
                        </nav>
                    </div>
                    <div class="sb-sidenav-menu-heading">자유게시판</div>
                    <a class="nav-link" href="/notice/NoticeList">
                        <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                        게시판
                    </a>
                </div>
            </div>
            <div class="sb-sidenav-footer">
                <div class="small">Logged in as:</div>
                <%=session.getAttribute("SS_USER_ID")%>님
            </div>
        </nav>
    </div>
    <div id="layoutSidenav_content">
        <main>
                    <div class="container">
                        <div class="col-6">
                        </div>
                        <div>
                            <div class="col-6">
                                <div class="input-group mb-3">
                                    <input type="text" id="msg" class="form-control">
                                    <div class="input-group-append">
                                        <button class="btn btn-outline-secondary" type="button" id="button-send">전송</button>
                                        <button class="btn btn-outline-success" type="button" value="<%=CmmUtil.nvl(cDTO.getRoomid())%>" onclick="getAllMsg()">불러오기</button>
                                    </div>
                                </div>
                                <div id="chatDiv" style="overflow: scroll; height: 500px">
                                    <div id="msgArea" class="col"></div>
                                </div>

                            </div>
                        </div>
                        <div class="col-6"></div>
                    </div>

        </main>
        <footer class="py-4 bg-light mt-auto">
            <div class="container-fluid px-4">
                <div class="d-flex align-items-center justify-content-between small">
                    <div class="text-muted">Copyright &copy; Your Website 2022</div>
                    <div>
                        <a href="#">Privacy Policy</a>
                        &middot;
                        <a href="#">Terms &amp; Conditions</a>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="/js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="/assets/demo/chart-area-demo.js"></script>
<script src="/assets/demo/chart-bar-demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
<script src="/js/datatables-simple-demo.js"></script>
<%}%>
</body>
</html>
