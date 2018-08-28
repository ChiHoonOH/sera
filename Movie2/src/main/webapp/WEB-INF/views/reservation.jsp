<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="f" action="./reservation_Movie" method="post">
<table align="center">
<tr>
<td>
<img src="${pageContext.request.contextPath}/resources/img/AboutTime.JPG" width="204" hieght="325">


</td>
<td>
<img src="${pageContext.request.contextPath}/resources/img/Avengers.JPG" width="204" hieght="325">
</td>
<td>
<img src="${pageContext.request.contextPath}/resources/img/AntMan.JPG" width="204" hieght="325">
</td>
<td>
<img src="${pageContext.request.contextPath}/resources/img/Avatar.JPG" width="204" hieght="325">
</td>
<td>
<img src="${pageContext.request.contextPath}/resources/img/Snowpiercer.JPG" width="204" hieght="325"> 
</td>
<tr>
<td align="center"><input type="submit" name="movie_name" value="abouttime"></td>
<td align="center"><input type="submit" name="movie_name" value="avengers"></td>
<td align="center"><input type="submit" name="movie_name" value="antman"></td>
<td align="center"><input type="submit" name="movie_name" value="avatar"></td>
<td align="center"><input type="submit" name="movie_name" value="snowpiercer"></td>


</tr>
</form>
<tr></tr>

</table>
</body>
</html>