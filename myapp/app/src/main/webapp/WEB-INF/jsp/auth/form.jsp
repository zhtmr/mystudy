<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>


<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>비트캠프 5기</title>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<h1>로그인</h1>
<form action='/app/auth/login' method='post'>
    <div>
        <label>
            이메일:
            <input type='text' name='email' value='${email}'>
        </label>
    </div>
    <div>
        <label>
            암호:
            <input type='password' name='password'>
        </label>
    </div>
    <button>로그인</button>
    <label>
        <input type='checkbox' name='saveEmail'> 이메일 저장
    </label>
</form>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
