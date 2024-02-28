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
<jsp:include page="/header.jsp"></jsp:include>
<h1>과제</h1>

<form action='/assignment/add' method='post'>
    <div>
        제목: <input type='text' name='title'>
    </div>
    <div>
        <label for='content'>내용: <textarea id='content' name='content'></textarea></label>
    </div>
    <div>
        제출일: <input name='deadline' type='date'>
    </div>
    <div>
        <button>등록</button>
    </div>
</form>

<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
