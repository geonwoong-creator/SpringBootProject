<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    session.getAttribute("SS_USER_ID"); //세션 강제 적용, 로그인된 상태로 보여주기 위함

    List<NoticeDTO> rList = (List<NoticeDTO>) request.getAttribute("rList");

//게시판 조회 결과 보여주기
    if (rList == null) {
        rList = new ArrayList<NoticeDTO>();

    }

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Tables - SB Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
    <script type="text/javascript">

        //상세보기 이동
        function doDetail(seq) {
            location.href = "/notice/NoticeInfo?nSeq=" + seq;
        }
        function noticeED() { //게시판 글쓰기
            location.href = "/notice/NoticeReg"
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
                <li><a class="dropdown-item" href="#!">Settings</a></li>
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
                    <a class="nav-link" href="/main/main">
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
                            <a class="nav-link" href="layout-static.html">Static Navigation</a>
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
            <div class="container-fluid px-4">
                <h1 class="mt-4">Tables</h1>
                <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
                    <li class="breadcrumb-item active">Tables</li>
                </ol>
                <div class="card mb-4">
                    <div class="card-body">
                        DataTables is a third party plugin that is used to generate the demo table below. For more information about DataTables, please visit the
                        <a target="_blank" href="https://datatables.net/">official DataTables documentation</a>
                        .
                    </div>
                </div>
                <div class="card mb-4">
                    <div class="card-header">
                        <i class="fas fa-table me-1"></i>
                        게시판
                        <input type="button" onclick="noticeED()" value="글쓰기" />
                    </div>
                    <div class="card-body">
                        <div class="card-body" style="text-align: center">
                            <div class="row btn-light">
                                <div class="col-2 btn-dark">순번</div>
                                <div class="col-4 btn-danger">제목</div>
                                <div class="col-2 btn-primary">조회수</div>
                                <div class="col-2 btn-warning">등록자</div>
                                <div class="col-2 btn-success">등록일</div>
                            </div>
                            <%
                                for (int i = 0; i < rList.size(); i++) {
                                    NoticeDTO rDTO = rList.get(i);

                                    if (rDTO == null) {
                                        rDTO = new NoticeDTO();
                                    }

                            %>
                            <div class="row">
                                <div class="col-2">
                                    <%
                                        //공지글이라면, [공지]표시
                                        if (CmmUtil.nvl(rDTO.getNotice_yn()).equals("1")) {
                                            out.print("<b>[공지]</b>");

                                            //공지글이 아니라면, 글번호 보여주기
                                        } else {
                                            out.print(CmmUtil.nvl(rDTO.getNotice_seq()));

                                        }
                                    %></div>
                                <div class="col-4">
                                    <a href="javascript:doDetail('<%=CmmUtil.nvl(rDTO.getNotice_seq())%>');">
                                        <%=CmmUtil.nvl(rDTO.getTitle()) %>
                                    </a>
                                </div>
                                <div class="col-2"><%=CmmUtil.nvl(rDTO.getRead_cnt()) %>
                                </div>
                                <div class="col-2"><%=CmmUtil.nvl(rDTO.getUser_id()) %>
                                </div>
                                <div class="col-2"><%=CmmUtil.nvl(rDTO.getReg_dt()) %>
                                </div>
                            </div>
                            <hr />
                            <%
                                }
                            %>
                        </div>
<%--                        <table id="datatablesSimple">--%>
<%--                            <tr>--%>
<%--                                <td width="100" align="center">순번</td>--%>
<%--                                <td width="200" align="center">제목</td>--%>
<%--                                <td width="100" align="center">조회수</td>--%>
<%--                                <td width="100" align="center">등록자</td>--%>
<%--                                <td width="100" align="center">등록일</td>--%>
<%--                            </tr>--%>
<%--                            <%--%>
<%--                                for (int i = 0; i < rList.size(); i++) {--%>
<%--                                    NoticeDTO rDTO = rList.get(i);--%>

<%--                                    if (rDTO == null) {--%>
<%--                                        rDTO = new NoticeDTO();--%>
<%--                                    }--%>

<%--                            %>--%>
<%--                            <tr>--%>
<%--                                <td align="center">--%>
<%--                                    <%--%>
<%--                                        //공지글이라면, [공지]표시--%>
<%--                                        if (CmmUtil.nvl(rDTO.getNotice_yn()).equals("1")) {--%>
<%--                                            out.print("<b>[공지]</b>");--%>

<%--                                            //공지글이 아니라면, 글번호 보여주기--%>
<%--                                        } else {--%>
<%--                                            out.print(CmmUtil.nvl(rDTO.getNotice_seq()));--%>

<%--                                        }--%>
<%--                                    %></td>--%>
<%--                                <td align="center">--%>
<%--                                    <a href="javascript:doDetail('<%=CmmUtil.nvl(rDTO.getNotice_seq())%>');">--%>
<%--                                        <%=CmmUtil.nvl(rDTO.getTitle()) %>--%>
<%--                                    </a>--%>
<%--                                </td>--%>
<%--                                <td align="center"><%=CmmUtil.nvl(rDTO.getRead_cnt()) %>--%>
<%--                                </td>--%>
<%--                                <td align="center"><%=CmmUtil.nvl(rDTO.getUser_id()) %>--%>
<%--                                </td>--%>
<%--                                <td align="center"><%=CmmUtil.nvl(rDTO.getReg_dt()) %>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                            <%--%>
<%--                                }--%>
<%--                            %>--%>
<%--                        </table>--%>
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
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
<script src="/js/datatables-simple-demo.js"></script>
</body>
</html>
