<%--
  Created by IntelliJ IDEA.
  User: shang
  Date: 2018/5/13
  Time: 20:56
  Description:
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--解析表达式--%>
<%@ page isELIgnored="false" %>
<%--引入jstl--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<html>
<head>
    <meta charset="UTF-8">
    <link  rel="shortcut icon" href="${pageContext.request.contextPath}/internal/image/favicon.ico">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/external/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/internal/css/nav_main.css">
    <script type="application/javascript" src="${pageContext.request.contextPath}/external/layui/layui.js"
            charset="utf-8"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/internal/js/nav_main.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/internal/js/nav_edit.js"></script>

</head>
<body>

<div class="layui-header header " spring="">
    <div class="layui-main">
        <a class="logo" href="pages/author/main.jsp">
            <img src="${pageContext.request.contextPath}/internal/image/logo.png" alt="layui">
        </a>

        <div class="search bar">
            <form>
                <i class="layui-icon qq">&#xe615;</i>
                <input type="text" placeholder="搜索">
            </form>

        </div>

        <ul class="layui-nav">

            <li class="layui-nav-item ">
                <button class="layui-btn layui-btn-radius to-edit-btn"
                        onclick="releaseArticle(editMode,funAfterReleaseArticleSuccess)">立即发布
                </button>
            </li>
            <li class="layui-nav-item my-layui-nav-item">
                <a style="font-size: 16px "  onClick="self.location=document.referrer;">返回</a>
            </li>
        </ul>
    </div>
</div>


<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('element', function () {
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    });
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var layer = layui.layer;
    });

    var oldTitle = '${articleTitle}';
    var oldSummary ='${articleSummary}';
</script>

</body>
</html>
