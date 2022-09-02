<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<title>게시판 글쓰기</title>
	<link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
	<link href="/css/styles.css" rel="stylesheet" />
	<script src="https://code.jquery.com/jquery-latest.min.js"></script> <!-- j쿼리-->
	<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
	<!--썸머노트-->
	<script src="/js/summernote-lite.js"></script>
	<script src="/js/summernote/lang/summernote-ko-KR.js"></script>
	<link rel="stylesheet" href="/css/summernote/font/summernote-lite.css">
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/lang/summernote-ko-KR.js"></script>
	<!-- include summernote css/js-->
	<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote-bs4.css" rel="stylesheet">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote-bs4.js"></script>
	<script type="text/javascript">
		//전송시 유효성 체크
		function doSubmit(f){
			if(f.title.value == ""){
				alert("제목을 입력하시기 바랍니다.");
				f.title.focus();
				return false;
			}

			if(calBytes(f.title.value) > 200){
				alert("최대 200Bytes까지 입력 가능합니다.");
				f.title.focus();
				return false;
			}

			var noticeCheck = false; //체크 여부 확인 변수
			for(var i=0;i<f.noticeYn.length;i++){
				if (f.noticeYn[i].checked){
					noticeCheck = true;
				}
			}


			if(f.contents.value == ""){
				alert("내용을 입력하시기 바랍니다.");
				f.contents.focus();
				return false;
			}

			if(calBytes(f.contents.value) > 4000){
				alert("최대 4000Bytes까지 입력 가능합니다.");
				f.contents.focus();
				return false;
			}


		}

		//글자 길이 바이트 단위로 체크하기(바이트값 전달)
		function calBytes(str){

			var tcount = 0;
			var tmpStr = new String(str);
			var strCnt = tmpStr.length;

			var onechar;
			for (i=0;i<strCnt;i++){
				onechar = tmpStr.charAt(i);

				if (escape(onechar).length > 4){
					tcount += 2;
				}else{
					tcount += 1;
				}
			}

			return tcount;
		}


		function summernotestart() {
			//여기 아래 부분
				console.log("시작");
			$('#summernote').summernote({
				// 에디터 높이
				height: 150,
				// 에디터 한글 설정
				lang: "ko-KR",
				focus : true,
				toolbar: [
					// 글꼴 설정
					['fontName', ['fontNames']],
					// 글자 크기 설정
					['fontSize', ['fontSizes']],
					// 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
					['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
					// 글자색
					['color', ['forecolor','color']],
					// 표만들기
					['table', ['table']],
					// 글머리 기호, 번호매기기, 문단정렬
					['para', ['ul', 'ol', 'paragraph']],
					// 줄간격
					['height', ['height']],
					// 코드보기, 확대해서보기, 도움말
					['view', ['codeview','fullscreen', 'help']]
				],
				// 추가한 글꼴
				fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
				// 추가한 폰트사이즈
				fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']});
		};


	</script>
</head>
<body class="sb-nav-fixed" onload="summernotestart()" >
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
	<!-- Navbar Brand-->
	<a class="navbar-brand ps-3" href="/main/main">COQUAT</a>
	<!-- Sidebar Toggle-->
	<button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
	<!-- Navbar Search-->
	<form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0" >
		<div class="input-group">
			<input class="form-control" type="hidden" placeholder="Search for..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
		</div>
	</form>
	<!-- Navbar-->
	<ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
		<li class="nav-item dropdown">
			<a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
			<ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
				<li><a class="dropdown-item" href="#!">마이페이지</a></li>
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
			<div class="container-fluid px-4">
				<h1 class="mt-4">게시판</h1>
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-table me-1"></i>
						게시판
					</div>
					<div class="card-body">
						<form name="f" method="post" action="/notice/NoticeInsert" target= "ifrPrc" onsubmit="return doSubmit(this);">
						<div id="card-body" style="text-align: center">
							<col width="100px" />
							<col width="500px" />
							<div class="row btn-light">
								<div class="col-2" >제목</div>
								<div class="col-2"><input type="text" name="title" maxlength="100" style="width: 450px" /></div>
							</div>
							<div class="row">
								<div class="col-2">content</div>
								<div class="col-6">
									<textarea name="contents" id="summernote" rows="10" cols="100" required></textarea>
								</div>
							</div>
							<div class="row">
								<div class="col-2"  colspan="2">
									<input type="submit" value="등록" />
									<input type="reset" value="다시 작성" />
								</div>
							</div>
						</div>
						</form>
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
