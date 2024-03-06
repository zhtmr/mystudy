<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>


<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>비트캠프 5기</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>

<h1>회원</h1>

<form action='/app/member/add' method='post' enctype='multipart/form-data'>
    <div>
        <label>
            이름:
            <input type='text' name='name'>
        </label>
    </div>
    <div>
        <label>
            이메일:
            <input type='text' name='email'>
        </label>
    </div>
    <div>
        <label>
            암호:
            <input type='password' name='password'>
        </label>
    </div>
    <div>
        <label>
            사진:
            <input type='file' name='file'>
        </label>
    </div>
    <div>
        <button>등록</button>
    </div>
</form>
<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
