<%@ page contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CoderBy</title>
<style type="text/css">
.error {
	color : red
}
</style>
</head>
<body>
<h1>URL 입력</h1>
<c:url value="/form" var="actionURL" scope="page" />
<form action="./result" method="get">
<p>
	<strong>URL</strong>
	<input type="text" name="name" value="URL 입력">
</p>

<p>
	<input type="submit" value="제출">
</p>
</form>
</body>
</html>
