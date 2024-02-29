<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>비트캠프 5기</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<h1>과제</h1>

<form action='/assignment/update' method='post'>
    <div>
        번호: <input readonly type='text' name='no' value=${assignment.no}>
    </div>
    <div>
        제목: <input type='text' name='title' value=${assignment.title}>
    </div>
    <div>
        내용: <textarea name='content'>${assignment.content}</textarea>
    </div>
    <div>
        마감일: <input type='date' name='deadline' value=${assignment.deadline}>
    </div>
    <button>변경</button>
    <a href='/assignment/delete?no=${assignment.no}'>삭제</a>
</form>

<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
