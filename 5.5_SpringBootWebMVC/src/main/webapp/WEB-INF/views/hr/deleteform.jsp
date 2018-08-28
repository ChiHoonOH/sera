<%@ page contentType="text/html; charset=UTF-8"%>
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
<h1>사원정보 삭제</h1>
${emp.employeeId}사원 ${emp.firstName} ${emp.lastName}의 정보를 삭제합니다.<p>
삭제후 데이터는 복구될 수 없습니다.<p>
${emp.employeeId}사원의 이메일을 입력하세요.
<form action="./delete" method="post">
이메일 : <input type="text" name="email">
<input type="hidden" name="empid" value="${emp.employeeId}">
<input type="submit" value="삭제">
</form>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>