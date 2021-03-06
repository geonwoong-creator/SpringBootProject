<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.BnoDTO" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<% UserInfoDTO rDTO = (UserInfoDTO) request.getAttribute("rDTO");
    BnoDTO pDTO = (BnoDTO) request.getAttribute("pDTO");

    if (rDTO == null) {
        rDTO = new UserInfoDTO();

    }

    String userRole = rDTO.getUser_role();
    if(userRole == null) {
        userRole = "";
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
    <title>개인정보</title>
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
    <!-- Navbar--><ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
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
                <div class="row justify-content-center">
                    <div class="col-lg-7">
                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                            <div class="card-header">
                                <h3>마이페이지</h3>
                            </div>
                            <div class="card-body">
                                <h5>아이디</h5>
                                <div class="form-control mb-3">
                                    <div class="text-black" id="userid">
                                        <label for="userid"><%=session.getAttribute("SS_USER_ID")%></label>
                                    </div>
                                </div>
                                <h5>이름</h5>
                                <div class="form-control mb-3">
                                    <div class="text-black" id="usernm">
                                        <label for="usernm"><%=CmmUtil.nvl(rDTO.getUser_name())%></label>
                                    </div>
                                </div>
                                <h5>이메일</h5>
                                <div class="form-control mb-3">
                                    <div class="text-black" id="usere">
                                        <label for="usere"><%=CmmUtil.nvl(rDTO.getEmail())%></label>
                                    </div>
                                </div>
                                <div class="form-control mb-3">
                                    <form name="f" method="post" action="/main/Bno">
                                        <input class="form-control" type="text" name="b_no" placeholder="사업자번호등록" />
                                        <input type="submit" value="등록">
                                    </form>
                                </div>
                            </div>
                            <div class="card-footer">
                                <a class="btn btn-success" href="/main/UserBno">사업자 정보 확인하기</a>
                                <button class="btn btn-danger" onclick="deleteUser()">회원탈퇴</button>
                                <%
                                    if(!userRole.equals("")) {
                                %>
                                <a class="btn btn-primary" href="/chat/rooms">상담채팅</a>
                                <%
                                    }
                                %>
                            </div>
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
<script>
    function deleteUser() {
        if(confirm("정말로 탈퇴하시겠습니까?")) {
            location.href = "/UserDelete";
        }
    }
</script>
<%}%>
</body>
</html>
