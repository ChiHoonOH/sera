<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.1.3/css/bootstrap.min.css">
<title>CoderBy</title>
</head>
<body>
<h1>사원 정보 상세 조회</h1>
<table border="1">
<tr>
	<th>EMPLOYEE_ID</th>
	<td>${emp.employeeId}</td>
</tr>
<tr>
	<th>FIRST_NAME</th>
	<td>${emp.firstName}</td>
</tr>
<tr>
	<th>LAST_NAME</th>
	<td>${emp.lastName}</td>
</tr>
<tr>
	<th>EMAIL</th>
	<td>${emp.email}</td>
</tr>
<tr>
	<th>PHONE_NUMBER</th>
	<td>${emp.phoneNumber}</td>
</tr>
<tr>
	<th>HIRE_DATE</th>
	<td>${emp.hireDate}</td>
</tr>
<tr>
	<th>JOB_ID</th>
	<td>${emp.jobId}</td>
</tr>
<tr>
	<th>SALARY</th>
	<td>${emp.salary}</td>
</tr>
<tr>
	<th>COMMISSION_PCT</th>
	<td>${emp.commissionPct}</td>
</tr>
<tr>
	<th>MANAGER_ID</th>
	<td>${emp.managerId}</td>
</tr>
<tr>
	<th>DEPARTMENT_ID</th>
	<td>${emp.departmentId}</td>
</tr>
</table>
<a href="update?empid=${emp.employeeId}">수정하기</a> 
<a href="delete?empid=${emp.employeeId}">삭제하기</a>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>