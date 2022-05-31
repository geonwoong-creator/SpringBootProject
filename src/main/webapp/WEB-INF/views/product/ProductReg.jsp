<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>게시판 글쓰기</title>
<script type="text/javascript">

//전송시 유효성 체크



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

</script>
</head>
<body onload="doOnload();">
<form name="f" method="post" action="/product/ProductInsert" target= "ifrPrc" enctype="multipart/form-data"   >
	<table border="1">
		<col width="100px" />
		<col width="500px" />
		<tr>
			<td align="center">제목</td>
			<td><input type="text" name="product_name" maxlength="100" style="width: 450px" /></td>
		</tr>
		<tr>
			<td align="center">주소</td>
			<td>예<input type="text" name="addr" maxlength="100" style="width: 450px" />

			</td>
		</tr>
		<tr>
			<td align="center">이미지</td>
			<td>예<input type="file" name="files" />

			</td>
		</tr>
		<tr>
			<td colspan="2">
				<textarea name="contents" style="width: 550px; height: 400px"></textarea>
			</td>
		</tr>
	<tr>
		<td align="center" colspan="2">
			<input type="submit" value="등록" />
			<input type="reset" value="다시 작성" />
		</td>
	</tr>		
	</table>
</form>
<!-- 프로세스 처리용 iframe / form 태그에서 target을 iframe으로 한다. -->
<iframe name="ifrPrc" style="display:none"></iframe>
</body>
</html>