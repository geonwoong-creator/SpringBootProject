<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!--j쿼리 -->
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>

    <script type="text/javascript">
        //회원가입 정보의 유효성 체크하기
        function doRegUserCheck(f){

            if (f.user_id.value==""){
                alert("아이디를 입력하세요.");
                f.user_id.focus() ;
                return false;
            }

            if (f.user_name.value==""){
                alert("이름을 입력하세요.");
                f.user_name.focus();
                return false;
            }

            if (f.password.value==""){
                alert("비밀번호를 입력하세요.");
                f.password.focus();
                return false;
            }

            if (f.password2.value==""){
                alert("비밀번호확인을 입력하세요.");
                f.password2.focus();
                return false;
            }

            if (f.email.value==""){
                alert("이메일을 입력하세요.");
                f.email.focus();
                return false;
            }

            if (f.addr1.value==""){
                alert("주소를 입력하세요.");
                f.addr1.focus();
                return false;
            }

            if (f.addr2.value==""){
                alert("상세주소를 입력하세요.");
                f.addr2.focus();
                return false;
            }
        }
    </script>
    <script>
        //아이디 중복 검사
        function checkId() {
            var id = $('#inputFirstName').val();
            $.ajax({
                url:'/user/idCheck',
                type:'post',
                data:{user_id : id},
                success:function (cnt) {
                    if (cnt != 1) {
                        $('.id_input_re_1').css("display","inline-block");
                        $('.id_input_re_2').css("display","none");
                    }else {
                        $('.id_input_re_1').css("display","none");
                        $('.id_input_re_2').css("display","inline-block");
                    }
                    console.log("성공");
                },
                error:function (){
                    alert("에러입니다")
                }
            });
        };
    </script>
    <style>
        .id_input_re_1{color: green; display: none;}
        .id_input_re_2{color: red; display: none;}
    </style>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Register</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="bg-primary">
<div id="layoutAuthentication">
    <div id="layoutAuthentication_content">
        <main>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-7">
                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                            <div class="card-header"><h3 class="text-center font-weight-light my-4">Create Account</h3></div>
                            <div class="card-body">
                                <form name="f" method="post" accept-charset="UTF-8" action="/user/insertUserInfo" onsubmit="return doRegUserCheck(this);">
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <input class="form-control" id="inputFirstName" name="user_id" type="text" placeholder="Enter your ID" required oninput="checkId()" />
                                                <label for="inputFirstName">ID</label>
                                            </div>
                                            <div class="row mb-3">
                                            <span class="id_input_re_1" >사용 가능한 아이디 입니다.</span>
                                            <span class="id_input_re_2" >아이디가 이미 존재합니다.</span>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-floating">
                                                <input class="form-control" id="inputLastName" name="user_name" type="text" placeholder="Enter your name" />
                                                <label for="inputLastName">name</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="inputEmail" name="email" type="email" placeholder="name@example.com" />
                                        <label for="inputEmail">Email address</label>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <input class="form-control" id="inputPassword" name="password" type="password" placeholder="Create a password" />
                                                <label for="inputPassword">Password</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <input class="form-control" id="inputPasswordConfirm" name="password2" type="password" placeholder="Confirm password" />
                                                <label for="inputPasswordConfirm">Confirm Password</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <input class="form-control" id="addr1" name="addr1" type="text" placeholder="Enter your address" />
                                                <label for="addr1">address</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-floating mb-3 mb-md-0">
                                                <input class="form-control" id="addr2" name="addr2" type="text" placeholder="Enter your address" />
                                                <label for="addr2">address</label>
                                            </div>
                                        </div>
                                        <div class="form-select-button mb-3">
                                            <label for="inputUserrole">관리자로 가입하기</label>
                                            <input  class="form-check " id="inputUserrole" name="userrole" type="checkbox" value="관리자"  />
                                        </div>
                                    </div>
                                    <div class="mt-4 mb-0">
                                        <div class="d-grid"><input type="submit" value="Register" /></div>
                                    </div>
                                </form>
                            </div>
                            <div class="card-footer text-center py-3">
                                <div class="small"><a href="/user/loginForm">Have an account? Go to login</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
    <div id="layoutAuthentication_footer">
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
</body>
</html>
