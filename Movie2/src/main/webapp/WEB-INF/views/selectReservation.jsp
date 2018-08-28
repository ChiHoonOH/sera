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


<%-- <c:out value="${result}"/>
<c:out value="${a}"/> --%>
<table border="1">
<tr>
<th>주문번호</th><th>고객번호</th><th>영화번호</th><th>주문시간</th>
</tr>
<tr>
</tr>

<c:forEach var="ticketOrder" items="${result}">
<tr><td>${ticketOrder.order_num}<!-- model로 출력 되는게 아니라 db도 출력 됨. -->
<td>${ticketOrder.customer_num}
<td>${ticketOrder.movie_list_num}
<td>${ticketOrder.time}</tr>
</c:forEach>

</table>
</body>
</html>