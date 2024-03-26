<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
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

    <form id="viewFrm" action='' method='post' enctype='multipart/form-data'>
        <input name='category' type='hidden' value='${category}'>
        <div>
            번호: <input readonly type='text' name='no' value=${board.no}>
        </div>
        <div>
            제목: <input type='text' name='title' value=${board.title}>
        </div>
        <div>
            작성자: <input readonly type='text' value=${board.writer.name} disabled>
        </div>
        <div id="editor">
            ${board.content}
        </div>
        <input type="hidden" name="content" value="">
        <c:if test="${category == 1}">

            <div>
                <div class="input-group mb-3">
                    <input type="file" class="form-control" id="inputGroupFile02" name='attachedFiles' multiple>
                    <label class="input-group-text" for="inputGroupFile02">Upload</label>
                </div>
                <ul>
                    <c:forEach items="${board.files}" var="file">
                        <c:if test="${file.filePath != null}">
                            <li><a href='/upload/board/${file.filePath}'>${file.filePath}</a>
                                [<a href='/app/board/file/delete?category=${category}&no=${file.no}'>삭제</a>]
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
        <div>
            <button id="submit">변경</button>
            <a href='/app/board/delete?category=${category}&no=${board.no}'>삭제</a>
        </div>
    </form>
    <br>
    <h1>뷰어</h1>
    <hr class="border border-primary border-3 opacity-75">
    <div id="viewer"></div>
</div>

<script>
    const {Editor} = toastui;
    const {codeSyntaxHighlight} = Editor.plugin;

    const editor = new Editor({
        el: document.querySelector('#editor'),
        previewStyle: 'vertical',
        height: '500px',
        plugins: [codeSyntaxHighlight],
        theme: 'dark'
    });

    // let html = editor.getHTML();
    let markdown = editor.getMarkdown();

    const viewer = Editor.factory({
        el: document.querySelector('#viewer'),
        previewStyle: 'vertical',
        height: '500px',
        theme: 'dark',
        initialEditType: 'markdown',
        plugins: [codeSyntaxHighlight],
        viewer: true,
        initialValue: markdown   // viewer 에는 마크다운 형식으로 해야 된다
    });

    let $form = $("#viewFrm");
    $(function () {

        $('button[id="submit"]').on('click', function () {
            let content = editor.getHTML();   // 서버 전송시에는 html 로 변환해 전송
            // let markdown = editor.getMarkdown();
            $form.children('form input[name="content"]').val(content)
            $form.attr('action', '/app/board/update').submit()
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
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>
