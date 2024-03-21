<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <a href='/'><img src='https://www.google.com/logos/doodles/2024/teachers-day-2024-jan-15-6753651837110419-2xa.gif'
                     height=100px></a>
    <a href='/app/assignment/list'>과제</a>
    <a href='/app/board/list?category=1'>게시글</a>
    <a href='/app/member/list'>회원</a>
    <a href='/app/board/list?category=2'>가입인사</a>

    <c:if test="${empty loginUser}">
        <a href='/app/auth/form'>로그인</a>
    </c:if>
    <c:if test="${not empty loginUser}">
        <span>${loginUser.name}</span>
        <a href='/app/auth/logout'>로그아웃</a>
    </c:if>
    <a href='/app/about'>소개</a>
</header>

<script>
    // sse 테스트
    <%--window.addEventListener('DOMContentLoaded', () => {--%>
    <%--    const evtSrc = new EventSource('/api/v1/users/notification');--%>

    <%--    evtSrc.addEventListener('error', (err) => {--%>
    <%--        // 기본적으로 네트워크가 끊어지면 자동 재접속하므로 방지하려면 여기서 명시적으로 close 해줘야 함--%>
    <%--        evtSrc.close();--%>
    <%--    });--%>

    <%--    // 전송되는 데이터에 "event: " 가 없고 "data: " 만 있으면 여기로 옴--%>
    <%--    evtSrc.addEventListener('message', (evt) => {--%>
    <%--        console.log(`message = ${evt.data}`);--%>
    <%--    });--%>

    <%--    // event: progress_value--%>
    <%--    evtSrc.addEventListener('progress_value', (evt) => {--%>
    <%--        const progress_value = evt.data;--%>
    <%--        progress_bar.value = progress_value;--%>
    <%--        progress_percentage.textContent = `${progress_value} %`;--%>
    <%--    });--%>
    <%--    // event: complete--%>
    <%--    evtSrc.addEventListener('complete', (evt) => {--%>
    <%--        alert(JSON.parse(evt.data));--%>
    <%--    });--%>
    <%--});--%>
</script>
