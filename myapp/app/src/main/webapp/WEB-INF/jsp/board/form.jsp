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
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">

    <h1>${title}
    </h1>

    <form id="myForm" action="" enctype="multipart/form-data" method="post">
        <input name='category' type='hidden' value='${category}'>
        <div>
            제목: <input type='text' name='title'>
        </div>
        <div id='content'></div>
        <input type="hidden" name="content">
        <c:if test="${category == 1}">
            <div>
                첨부파일: <input name='attachedFiles' type='file' multiple>
            </div>
        </c:if>
        <div>
            <button>등록</button>
        </div>
    </form>
    <h1>viewer</h1>
    <div id="viewer"></div>

</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>

<script>

    const {Editor} = toastui;

    const editor = new Editor({
        el: document.querySelector('#content'),
        previewStyle: 'vertical',
        height: '500px',
        theme: 'dark'
    });

    const viewer = Editor.factory({
        el: document.querySelector('#viewer'),
        viewer: true,
        height: '500px',
        theme: 'dark'
    });

    // let markdown = editor.getMarkdown();
    let markdown = editor.getHTML();

    const $form = $("#myForm");
    // formData.append("content", markdown);

    $('form button').on('click', function () {
        $form.children('input[name=content]')
        $form.attr('action', '/app/board/add').submit()
        // $.ajax({
        //     enctype: 'multipart/form-data',
        //     processData: false,
        //     contentType: false,
        //     type: 'post',
        //     url: '/app/board/add',
        //     async: false,
        //     dataType: 'text',
        //     data: formData,
        //     success: function (result) {
        //         console.log(result);
        //     },
        //     error: function (request, status, error) {
        //         console.log(error)
        //     }
        // })
    });
</script>
</html>
