<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>비트캠프 5기</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>

<h1>${title}
</h1>

<form action='/app/board/add?category=${category}' method='post' enctype='multipart/form-data'>
    <input name='category' type='hidden' value='${category}'>
    <div>
        제목: <input type='text' name='title'>
    </div>
    <div>
        <label for='content'>내용: </label><textarea name='content' id='content'></textarea>
    </div>
    <c:if test="${category == 1}">
        <div>
            첨부파일: <input name='files' type='file' multiple>
        </div>
    </c:if>
    <div>
        <button>등록</button>
    </div>
</form>


<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>