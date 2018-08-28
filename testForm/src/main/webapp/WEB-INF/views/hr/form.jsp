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
<c:url value="/hr/form" var="actionURL" scope="page" />
<form:form action="${actionURL}" modelAttribute="emp">
<table border="1">
<tr>
	<th>URL input</th>
	<td><form:input path="employeeId" />
		<form:errors path="employeeId" class="error" /></td>
</tr>
<tr>
	<th>&nbsp;</th>
	<td>
		<input type="submit" value="입력"> 
	</td>
</tr>
</table>
</form:form>
</body>
</html>