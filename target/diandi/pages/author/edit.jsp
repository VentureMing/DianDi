<%--
  Created by IntelliJ IDEA.
  User: shang
  Date: 2018/5/14
  Time: 19:11
  Description:
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--解析表达式--%>
<%@ page isELIgnored="false" %>
<%--引入jstl--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/pages/nav/nav_edit.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>点滴-写文章</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/internal/image/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/internal/editormd/css/editormd.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/internal/css/common.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/external/jQuery.js"></script>
    <script type="application/javascript">

        var authorId  = ${sessionScope["authorId"]};
    </script>
    <c:choose>
        <c:when test="${not empty articleContentMd}">
            <script type="application/javascript">
                // 编辑模式
                var editMode = 1;
                var articleId = ${articleId};
            </script>
        </c:when>
        <c:otherwise>
            <script type="application/javascript">
                // 创作模式
                var editMode = 2;
            </script>
        </c:otherwise>
    </c:choose>
</head>
<body>

<div class="editormd" id="editormd-container">
    <textarea class="editormd-markdown-textarea" name="test-editormd-markdown-doc"
              id="editormd"></textarea>

    <!-- 第二个隐藏文本域，用来构造生成的HTML代码，方便表单POST提交，这里的name可以任意取，后台接受时以这个name键为准 -->
    <!-- html textarea 需要开启配置项 saveHTMLToTextarea == true -->
    <textarea class="editormd-html-textarea" name="editorhtml"
              id="editorHtml"></textarea>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/internal/editormd/editormd.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/internal/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/internal/js/edit.js"></script>
<script>
    $('#editormd').html("${articleContentMd}");
</script>
</body>
</html>
