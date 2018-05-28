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
    <link id="favicon-link" rel="shortcut icon" href="${pageContext.request.contextPath}/internal/image/favicon.ico">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/external/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/internal/css/nav_main.css">
    <script type="application/javascript" src="${pageContext.request.contextPath}/external/layui/layui.js"
            charset="utf-8"></script>

</head>
<body>

<div class="layui-header header ">
    <div class="layui-main">
        <a class="logo" href="${pageContext.request.contextPath}/pages/author/main.jsp">
            <img src="${pageContext.request.contextPath}/internal/image/logo.png" alt="layui">
        </a>

        <div class="search bar">
            <form>
                <i class="layui-icon qq">&#xe615;</i>
                <input type="text" placeholder="搜索">
            </form>

        </div>

        <ul class="layui-nav">
            <li class="layui-nav-item my-layui-nav-item">
                <a style="font-size: 16px " href="">读书</a>
            </li>
            <li class="layui-nav-item my-layui-nav-item">
                <a style="font-size: 16px " href="">电影</a>
            </li>
            <li class="layui-nav-item my-layui-nav-item">
                <a style="font-size: 16px " href="">旅行</a>
            </li>
            <c:choose>
                <c:when test="${empty authorLoginSignal}">
                    <li class="layui-nav-item ">
                        <button class="layui-btn layui-btn-radius layui-btn-primary to-register-btn"
                                onclick="window.location='${pageContext.request.contextPath}/pages/author/register.jsp'">
                            注册
                        </button>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="layui-nav-item " lay-unselect="">
                        <a href="javascript:;"><img src="http://t.cn/RCzsdCq" class="layui-nav-img"></a>
                        <dl class="layui-nav-child">
                            <dd><a href="${pageContext.request.contextPath}/pages/author/home.jsp?authorId=${sessionScope.authorId}">我的主页</a></dd>
                            <dd><a href="javascript:;">我的关注</a></dd>
                            <dd><a href="javascript:;">收藏的文章</a></dd>
                            <dd><a href="javascript:;">喜欢的文章</a></dd>
                            <dd><a href="javascript:;">设置</a></dd>
                            <dd><a href="javascript:;" onclick="logout(${sessionScope['authorId']})" >退出</a></dd>
                        </dl>
                    </li>
                </c:otherwise>
            </c:choose>
            <li class="layui-nav-item ">
                <button class="layui-btn layui-btn-radius to-edit-btn"
                        onclick="window.location='/edit_article?authorId=${sessionScope['authorId']}'">写文章
                </button>
            </li>

        </ul>
    </div>
</div>


<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('element', function () {
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

    });
    function logout(authorId) {

        $.post(
            '/author/' + authorId + '/logout',
            function (result) {
                if (result.code === 0) {
                    location.href = "${pageContext.request.contextPath}/pages/author/main.jsp";
                } else {
                    toast(result.msg, 2000);
                }
            }
        );
    }

</script>

</body>
</html>
