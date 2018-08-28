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
<table border="1" align="center" >
<%-- <c:if test="${id.equals("admin")}"> --%>
<%-- </c:if> --%>
<!-- <form name="f2" action="./select_schedule" method="post">
<tr><td><input type="submit" value="상영시간 조회"></td></tr>
</form> -->
<form name="f3" action="./reservation" method="post">
<tr><td><input type="submit" value="예매하기"></td></tr>
</form>
<form name="f4" action="./select_reservation" method="post">
<tr><td><input type="submit" value="예매내역 조회"></td></tr>
</form>

<form name="f5" action="./movie_recommandation" method="post">
<tr><td><input type="submit" value="영화추천"></td></tr>
</form>

</table>


</body>
</html>