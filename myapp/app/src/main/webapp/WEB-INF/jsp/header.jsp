<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset='UTF-8'>
    <!-- TUI 에디터 CSS CDN -->
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>
    <!-- code syntax-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/prism/1.23.0/themes/prism.min.css"/>
    <link rel="stylesheet" href="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight.min.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
        integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<!-- Editor -->
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<!-- Viewer -->
<!--
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.js"></script>
-->
<!-- Editor's Plugin -->
<script src="https://uicdn.toast.com/editor-plugin-code-syntax-highlight/latest/toastui-editor-plugin-code-syntax-highlight-all.min.js"></script>
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
