<%@ page import="bitcamp.myapp.vo.Board" %>
<%@ page import="bitcamp.myapp.vo.AttachedFile" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>


<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>비트캠프 5기</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<%
    int category = (int) request.getAttribute("category");
    String title = category == 1 ? "게시글" : "가입인사";
    Board board = (Board) request.getAttribute("board");
    List<AttachedFile> files = (List<AttachedFile>) request.getAttribute("files");
%>

<h1><%=title%>
</h1>

<form action='/board/update' method='post' enctype='multipart/form-data'>
    <input name='category' type='hidden' value='<%=category%>'>
    <div>
        번호: <input readonly type='text' name='no' value=<%=board.getNo()%>>
    </div>
    <div>
        제목: <input type='text' name='title' value=<%=board.getTitle()%>>
    </div>
    <div>
        <label for='content'>내용: </label><textarea id="content" name='content'><%=board.getContent()%></textarea>
    </div>
    <div>
        작성자: <input readonly type='text' value=<%=board.getWriter().getName()%> disabled>
    </div>
    <% if (category == 1) { %>
    <div>
        첨부파일: <input name='files' type='file' multiple>
        <ul>
            <% for (AttachedFile file : files) { %>
            <li><a href='/upload/board/<%=file.getFilePath()%>'><%=file.getFilePath()%></a> [<a
                    href='/board/file/delete?category=<%=category%>&no=<%=file.getNo()%>'>삭제</a>]
            </li>
            <% } %>
        </ul>
    </div>
    <% } %>
    <div>
        <button>변경</button>
        <a href='/board/delete?category=<%=category%>&no=<%=board.getNo()%>'>삭제</a>
    </div>
</form>


<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
