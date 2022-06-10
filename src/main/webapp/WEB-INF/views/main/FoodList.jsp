<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
    List<HashMap<String, Object>> pList = (List<HashMap<String, Object>>) request.getAttribute("tList");
    if(pList == null) {
        pList = new ArrayList<>();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%
        if(session.getAttribute("SS_USER_ID") == null) { //로그인이 안되었을때
            //로그인 화면으로 이동
            response.sendRedirect("/user/loginForm");
        } else {
    %>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Dashboard - SB Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>

    <!--j쿼리 -->
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        function getSmallList() {
            let middleCode = document.getElementById("mSelect").value;


            $.ajax({
                type : "get",
                url : "/main/Food2",
                dataType : "json",
                data : {
                    "mCode" : middleCode
                },
                contentType: 'application/json',
                success : function(result) {
                    let sList = JSON.stringify(result);
                    let myObject = eval('(' + sList + ')');
                    $("#sSelect").empty();
                    let Sdefault = document.createElement('option');
                    Sdefault.value = "";
                    Sdefault.innerText = "전체";
                    document.getElementById("sSelect").appendChild(Sdefault);
                    for (i in myObject)
                    {
                        let sCode = myObject[i]["indsSclsCd"];
                        let sName = myObject[i]["indsSclsNm"];
                        let option = document.createElement('option');
                        option.value = sCode;
                        option.innerText = sName;
                        document.getElementById("sSelect").appendChild(option);
                    }
                },
                error : function(req, status, error) {
                    console.log(error);
                },
                complete : function(){
                    console.log("ajax끝");
                }
            });
        }

        function callApi() {
            console.log($("#mSelect").val());
            console.log($("#sSelect").val());

<!--가게 조회-->
        }
        function MaketlList() {
            let smallCode = document.getElementById("sSelect").value;


            $.ajax({
                type : "get",
                url : "/main/Food3",
                dataType : "json",
                data : {
                    "sCode" : smallCode
                },
                contentType: 'application/json',
                success : function(result) {
                    let sList = JSON.stringify(result);
                    let myObject = eval('(' + sList + ')');
                    $("#maSelect").empty();
                    for (i in myObject)
                    {
                        let Mcode = myObject[i]["bizesId"];
                        let Mname = myObject[i]["bizesNm"];
                        let Maddr = myObject[i]["rdnmAdr"]

                        let color = ["bg-primary", "bg-success", "bg-warning", "bg-danger"];
                        let randomNum = Math.floor(Math.random() * 4);
                        let seletedColor = color[randomNum];
                        $("#maSelect").append($(
                        `<div class="col-xl-3 col-md-6">
                                <div class="card \${seletedColor} text-white mb-4" name="twingkle">
                                    <div class="card-body">\${Mname}</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="/main/FoodMap?code=\${Maddr}&name=\${Mname}">\${Maddr}</a>
                                        <div class="small text-white"><svg class="svg-inline--fa fa-angle-right" aria-hidden="true" focusable="false" data-prefix="fas" data-icon="angle-right" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 256 512" data-fa-i2svg=""><path fill="currentColor" d="M64 448c-8.188 0-16.38-3.125-22.62-9.375c-12.5-12.5-12.5-32.75 0-45.25L178.8 256L41.38 118.6c-12.5-12.5-12.5-32.75 0-45.25s32.75-12.5 45.25 0l160 160c12.5 12.5 12.5 32.75 0 45.25l-160 160C80.38 444.9 72.19 448 64 448z"></path></svg><!-- <i class="fas fa-angle-right"></i> Font Awesome fontawesome.com --></div>
                                    </div>
                                </div>
                            </div>`
                        ));
                    }
                },
                error : function(req, status, error) {
                    console.log(error);
                },
                complete : function(){
                    console.log("ajax끝");
                }
            });
        }

        function callApi() {
            console.log($("#mSelect").val());
            console.log($("#sSelect").val());
            console.log($("#maSelect").val());


        }

        function chgColor() {
            $("div[name=twingkle]").each(function(index, item){
                let color = ["bg-primary", "bg-success", "bg-warning", "bg-danger"];
                let randomNum = Math.floor(Math.random() * 4);
                let seletedColor = color[randomNum];
                $(item).attr('class','card ' + seletedColor + ' text-white mb-4');
            });
        }
        window.onload = function () {

            setInterval(function() {
                chgColor();
            }, 1000);
        }
    </script>
</head>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" href="/main/main">Start Bootstrap</a>
    <!-- Sidebar Toggle-->
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
    <!-- Navbar Search-->
    <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
        <div class="input-group">
            <input class="form-control" type="text" placeholder="Search for..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
            <button class="btn btn-primary" id="btnNavbarSearch" type="button"><i class="fas fa-search"></i></button>
        </div>
    </form>
    <!-- Navbar-->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="/main/UserSetting">Settings</a></li>
                <li><a class="dropdown-item" href="#!">Activity Log</a></li>
                <li><hr class="dropdown-divider" /></li>
                <li><a class="dropdown-item" href="/user/Logout">Logout</a></li>
            </ul>
        </li>
    </ul>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <div class="sb-sidenav-menu-heading">Core</div>
                    <a class="nav-link" href="index.html">
                        <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        Dashboard
                    </a>
                    <div class="sb-sidenav-menu-heading">Interface</div>
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                        <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                        Layouts
                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                        <nav class="sb-sidenav-menu-nested nav">
                            <a class="nav-link" href="/product/ProductList">예약</a>
                            <a class="nav-link" href="layout-sidenav-light.html">Light Sidenav</a>
                        </nav>
                    </div>
                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                        <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                        Pages
                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                    </a>
                    <div class="collapse" id="collapsePages" aria-labelledby="headingTwo" data-bs-parent="#sidenavAccordion">
                        <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
                                Authentication
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="pagesCollapseAuth" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="login.html">Login</a>
                                    <a class="nav-link" href="register.html">Register</a>
                                    <a class="nav-link" href="password.html">Forgot Password</a>
                                </nav>
                            </div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseError" aria-expanded="false" aria-controls="pagesCollapseError">
                                Error
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="pagesCollapseError" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="401.html">401 Page</a>
                                    <a class="nav-link" href="404.html">404 Page</a>
                                    <a class="nav-link" href="500.html">500 Page</a>
                                </nav>
                            </div>
                        </nav>
                    </div>
                    <div class="sb-sidenav-menu-heading">Addons</div>
                    <a class="nav-link" href="charts.html">
                        <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                        Charts
                    </a>
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
                <div class="row justify-content-center">
                    <div class="col-lg-10">
                        <div class="card-body" style="align-content: center">
                            <select class="form-select" id="mSelect" onchange="getSmallList()">
                                <option value="">전체</option>
                                <%
                                    for(HashMap<String, Object> pDTO : pList) {
                                        String code = (String) pDTO.get("indsMclsCd");
                                        String name = (String) pDTO.get("indsMclsNm");
                                %>
                                <option value="<%=code%>" ><%=name%></option>
                                <%
                                    }
                                %>
                            </select>

                            <select class="form-select" id="sSelect" onchange="MaketlList()">
                            </select>
                            <button class="btn btn-outline-success" type="button" onclick="callApi()">호출</button>
                            <div class="container">
                                <div class="row" id="maSelect">
                                    <div class="col-xl-3 col-md-6">

                                    </div>
                                </div>
                            </div>
                         <!--   <select class="form-select" id="maSelect">
                            </select> -->

                        </div>
                    </div>
                </div>
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