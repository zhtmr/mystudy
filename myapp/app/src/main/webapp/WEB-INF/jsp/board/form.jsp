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
        <div id='editor'></div>
        <input type="hidden" name='content' value="ddd">
        <c:if test="${category == 1}">
            <div class="input-group mb-3">
                <input type="file" class="form-control" id="inputGroupFile02" name='attachedFiles' multiple>
                <label class="input-group-text" for="inputGroupFile02">Upload</label>
            </div>
        </c:if>
        <div>
            <button id="submit" class="btn btn-success">등록</button>
        </div>
    </form>
<%--    <div id="viewer"></div>--%>

</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>

<script>

    const {Editor} = toastui;

    const editor = new Editor({
        el: document.querySelector('#editor'),
        previewStyle: 'vertical',
        height: '500px',
        initialEditType: 'markdown',
        theme: 'dark'
    });

    // const viewer = Editor.factory({
    //     el: document.querySelector('#viewer'),
    //     viewer: true,
    //     height: '500px',
    //     theme: 'dark'
    // });
</script>
<script>
    let $form = $("#myForm");
    // formData.append("content", markdown);
    $(function () {

        $('button[id="submit"]').on('click', function () {
            // let markdown = editor.getMarkdown();
            let content = editor.getHTML();
            $form.children('form input[name="content"]').val(content)
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
    })
</script>
</html>
