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

<h1>${title}
</h1>

<form action='/board/update' method='post' enctype='multipart/form-data'>
    <input name='category' type='hidden' value='${category}'>
    <div>
        번호: <input readonly type='text' name='no' value=${board.no}>
    </div>
    <div>
        제목: <input type='text' name='title' value=${board.title}>
    </div>
    <div>
        <label for='content'>내용: </label><textarea id="content" name='content'>${board.content}</textarea>
    </div>
    <div>
        작성자: <input readonly type='text' value=${board.writer.name} disabled>
    </div>
    <c:if test="${category == 1}">

    <div>
        첨부파일: <input name='files' type='file' multiple>
        <ul>
            <c:forEach items="${files}" var="file">
                <li><a href='/upload/board/${file.filePath}'>${file.filePath}</a>
                    [<a href='/board/file/delete?category=${category}&no=${file.no}'>삭제</a>]
                </li>
            </c:forEach>
            </c:if>
        </ul>
    </div>
    <div>
        <button>변경</button>
        <a href='/board/delete?category=${category}&no=${board.no}'>삭제</a>
    </div>
</form>


<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
