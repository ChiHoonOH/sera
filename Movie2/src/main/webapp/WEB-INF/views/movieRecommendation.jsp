<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- ${result} --%>
<%-- <c:out value="${result}"/> --%>
<table align="center">
<%-- <c:set value=1 var="rank"/> --%>
<c:set value="1" var="rank"/>
<c:forEach var="movie" items="${result}">
<tr><td>${rank}순위:${movie.movie_name}</td></tr>
<c:set var="rank" value="${rank+1}"/>
</c:forEach>
<!-- starTime
endTime
mSec -->
</table>
</body>
</html>