<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>비트캠프 5기</title>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<h1>회원</h1>

<form action='/app/member/update' method='post' enctype='multipart/form-data'>
    <div>
        사진:
        <c:if test="${not empty member.photo}">
            <a href='/upload/${member.photo}'><img src='/upload/${member.photo}' height='80px'></a><br>
        </c:if>
        <c:if test="${empty member.photo}">
            <a href='/img/default-photo.png'><img src='/img/default-photo.png' height='80px'></a><br>
        </c:if>
        <input type='file' name='file'></div>
    <div>
        번호:
        <input readonly type='text' name='no' value='${member.no}'>
    </div>
    <div>
        이름: <input type='text' name='name' value='${member.email}'>
    </div>
    <div>
        이메일: <input type='text' name='email' value='${member.name}'>
    </div>
    <div>
        비밀번호: <input type='password' name='password'>
    </div>
    <div>
        <div>
            가입일: <input readonly type='text' value='<fmt:formatDate value="${member.createdDate}" pattern="yyyy-MM-dd"/>'>
        </div>
    </div>
    <div>
        <button>변경</button>
        <a href='/app/member/delete?no=${member.no}'>삭제</a>
    </div>
</form>

<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
