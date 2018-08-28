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
<h1>사원 목록</h1>
<a href="<c:url value='/hr/insert'/>">신규 사원 정보 입력</a>
<table class="table table-bordered">
  <thead class="thead-light">
<tr>
	<th scope="col">EMPLOYEE_ID</th>
	<th scope="col">FIRST_NAME</th>
	<th scope="col">LAST_NAME</th>
	<th scope="col">EMAIL</th>
	<th scope="col">PHONE_NUMBER</th>
	<th scope="col">HIRE_DATE</th>
	<th scope="col">JOB_ID</th>
	<th scope="col">SALARY</th>
	<th scope="col">COMMISSION_PCT</th>
	<th scope="col">MANAGER_ID</th>
	<th scope="col">DEPARTMENT_ID</th>
</tr>
  </thead>
  <tbody>
<c:forEach var="emp" items="${empList}">
<tr>
	<th scope="row"><a href="<c:url value='/hr/${emp.employeeId}'/>">${emp.employeeId}</a></th>
	<td>${emp.firstName}</td>
	<td>${emp.lastName}</td>
	<td>${emp.email}</td>
	<td>${emp.phoneNumber}</td>
	<td>${emp.hireDate}</td>
	<td>${emp.jobId}</td>
	<td>${emp.salary}</td>
	<td>${emp.commissionPct}</td>
	<td>${emp.managerId}</td>
	<td>${emp.departmentId}</td>
</tr>
</c:forEach>
  </tbody>
</table>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>