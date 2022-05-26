<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="kopo.poly.util.CmmUtil" %>

<%
    //Controller에 저장된 세션으로 로그인할때 생성됨
    String SS_USER_ID = CmmUtil.nvl((String)session.getAttribute("SS_USER_ID"));
    String res = CmmUtil.nvl((String)request.getAttribute("res"));

%>
<!DOCTYPE html>

<html>
<head>

    <meta charset="UTF-8">
    <title>로그인되었습니다.</title>
    <%
        String loginmsg = "";
        if (res.equals("1")){
            loginmsg = SS_USER_ID +"님이 로그인 되었습니다.";

        }else if (res.equals("0")){
            loginmsg = "아이디, 비밀번호가 일치하지 않습니다.";
        }else{
            loginmsg = "시스템에 문제가 발생하였습니다. 잠시후 다시 시도하여 주시길 바랍니다.";

        }
    %>
    <script type="text/javascript">
        alert("<%=loginmsg%> ");
    </script>
</head>
<body>
 <script>
     location.href="/main/main"
 </script>
</body>
</html>