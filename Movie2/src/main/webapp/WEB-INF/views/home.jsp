<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">	
<!-- login.jsp-->

<script type="text/javascript">
	function loginCheck(){
		if (f.id.value==""){
			alert("아이디를 입력하세요")
			f.id.focus()
			return
		}
		if (f.passwd.value==""){
			alert("비밀번호를 입력하세요")
			f.passwd.focus()
			return
		}
		document.f.submit()
	}
</script>
</head>

<p>
<form name="f" action="./login" method="post">
	<table width="60%" align="center" height="120">
		<tr>
		<td>아이디:</td>
			<td width="40%">	
				<input type="text" name="id" tabindex="1">			
			</td>
			<td rowspan="2" width="30%" valign="middle">							
			</td>
		</tr>
		<tr>	
		<td>비밀번호:</td>		
			<td>
				<input type="password" name="pass"  tabindex="2">
			</td>
		</tr>
		
		<tr>
		<td>&nbsp;</td>
		<td>
		<input type='submit' name='login' value="로그인">
</form>

<form name="f2" action="./insert" method="post">
		<input type='submit' name='join' value="회원가입">
</form>
		</td>
		</tr>			
</table>

