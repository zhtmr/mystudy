<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang='en'>
<head>
    <!-- TUI 에디터 CSS CDN -->
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css"/>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
    <meta charset='UTF-8'>
    <title>비트캠프 5기</title>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>

<h1>${title}
</h1>

<form id="myForm">
    <input name='category' type='hidden' value='${category}'>
    <div>
        제목: <input type='text' name='title'>
    </div>
    <div id='content'>
        <%--        <label for='content'>내용: </label><textarea name='content'></textarea>--%>
    </div>
    <c:if test="${category == 1}">
        <div>
            첨부파일: <input name='attachedFiles' type='file' multiple>
        </div>
    </c:if>
    <div>
        <button>등록</button>
    </div>
</form>
<div id="viewer"></div>

<jsp:include page="../footer.jsp"></jsp:include>
</body>
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script>
    // const editor = new toastui.Editor({
    //     el: document.querySelector('#content'), // 에디터를 적용할 요소 (컨테이너)
    //     height: '500px',                        // 에디터 영역의 높이 값 (OOOpx || auto)
    //     initialEditType: 'markdown',            // 최초로 보여줄 에디터 타입 (markdown || wysiwyg)
    //     initialValue: '내용을 입력해 주세요.',     // 내용의 초기 값으로, 반드시 마크다운 문자열 형태여야 함
    //     previewStyle: 'vertical'                // 마크다운 프리뷰 스타일 (tab || vertical)
    // });
    const content = `
![image](https://uicdn.toast.com/toastui/img/tui-editor-bi.png)

# Awesome Editor!

It has been _released as opensource in 2018_ and has ~~continually~~ evolved to **receive 10k GitHub ⭐️ Stars**.

## Create Instance

You can create an instance with the following code and use \`getHtml()\` and \`getMarkdown()\` of the [Editor](https://github.com/nhn/tui.editor).

\`\`\`js
const editor = new Editor(options);
\`\`\`

> See the table below for default options
> > More API information can be found in the document

| name | type | description |
| --- | --- | --- |
| el | \`HTMLElement\` | container element |

## Features

* CommonMark + GFM Specifications
   * Live Preview
   * Scroll Sync
   * Auto Indent
   * Syntax Highlight
        1. Markdown
        2. Preview

## Support Wrappers

> * Wrappers
>    1. [x] React
>    2. [x] Vue
>    3. [ ] Ember
`


    const {Editor} = toastui;

    const editor = new Editor({
        el: document.querySelector('#content'),
        previewStyle: 'vertical',
        height: '500px',
        initialValue: content,
        theme: 'dark'
    });

    const viewer = Editor.factory({
        el: document.querySelector('#viewer'),
        viewer: true,
        height: '500px',
        initialValue: content,
        theme: 'dark'
    });

    let markdown = editor.getMarkdown();
</script>
<script>
    const formData = new FormData(document.getElementById("myForm"));
    formData.append("content", markdown);

    function add() {
        $.ajax({
            type: 'post',           // 타입 (get, post, put 등등)
            url: '/app/board/add',           // 요청할 서버url
            async: true,            // 비동기화 여부 (default : true)
            headers: {              // Http header
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            dataType: 'text',       // 데이터 타입 (html, xml, json, text 등등)
            data: JSON.stringify({  // 보낼 데이터 (Object , String, Array)
                "content":markdown
            }),
            success: function (result) { // 결과 성공 콜백함수
                console.log(result);
            },
            error: function (request, status, error) { // 결과 에러 콜백함수
                console.log(error)
            }
        })
    }
    console.dir(formData)

    let entries = formData.entries();
    for (const x of entries) {
        console.log(x[0], ": ", x[1]);
    }

    $(document).ready(function(){
        $("button").click(add);
    });
</script>
</html>
