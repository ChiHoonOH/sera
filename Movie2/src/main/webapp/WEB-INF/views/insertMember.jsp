<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CoderBy</title>
</head>
<body>
<h1>회원정보 입력</h1>
<form action="./insert_Member" method="post">
<table border="1">
<thead class="thead-light">
<tr>
	<th>이메일(아이디)</th>
	<td><input type="text" name="id" required></td>
</tr>
<tr>
	<th>비밀번호</th>
	<td><input type="text" name="pass"></td>
</tr>
<tr>
	<th>성별</th>
	<td><input type="text" name="gender" required></td>
</tr>
<tr>
	<th>나이</th>
	<td><input type="text" name="age" required></td>
</tr>
<tr>
	<th>&nbsp;</th>
	<td>
		<input type="submit" value="저장"> 
		<input type="reset" value="취소">
	</td>
</tr>
</thead>
</table>
</form>
</body>
</html>