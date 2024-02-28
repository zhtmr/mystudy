<%@ page import="bitcamp.myapp.vo.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>


<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>비트캠프 5기</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<% Member member = (Member) request.getAttribute("member"); %>
<h1>회원</h1>

<form action='/member/update' method='post' enctype='multipart/form-data'>
    <div>
        <%
            String photoUrl;
            if (member.getPhoto() != null) {
                photoUrl = "/upload/" + member.getPhoto();
            } else {
                photoUrl = "/img/default-photo.png";
            }
        %>
        사진: <a href='<%= photoUrl %>'><img src='<%= photoUrl %>' height='80px'></a><br>
        <input type='file' name='photo'></div>
    <div>
        번호:
        <input readonly type='text' name='no' value='<%= member.getNo()%>'>
    </div>
    <div>
        이름: <input type='text' name='name' value='<%=member.getName()%>'>
    </div>
    <div>
        이메일: <input type='text' name='email' value='<%=member.getEmail()%>'>
    </div>
    <div>
        비밀번호: <input type='password' name='password'>
    </div>
    <div>
        <div>
            가입일: <input readonly type='text' value='<%= member.getCreatedDate()%>'>
        </div>
    </div>
    <div>
        <button>변경</button>
        <a href='/member/delete?no=<%=member.getNo()%>'>삭제</a>
    </div>
</form>

<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>
