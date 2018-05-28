<%--
  Created by IntelliJ IDEA.
  User: shang
  Date: 2018/5/24
  Time: 13:49
  Description:
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--解析表达式--%>
<%@ page isELIgnored="false" %>
<%--引入jstl--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/pages/nav/nav.jsp" %>
<html>
<head>
    <title>点滴专栏</title>
    <link id="favicon-link" rel="shortcut icon" href="${pageContext.request.contextPath}/internal/image/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/external/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/internal/css/nav_main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/internal/css/create-special-column.css">
    <script type="application/javascript" src="${pageContext.request.contextPath}/external/jQuery.js"></script>
</head>
<body>

<div class="sepcial-column-main">
    <div class=" layui-col-md-offset1 sepcial-column-header">
        新建专栏
    </div>
    <div class=" layui-col-md-offset1 layui-row layui-upload" style="height: 120px">
        <div class="layui-col-md2 layui-upload-list">
            <img style="height: 100px;width:100px;border-radius: 6px" src="${pageContext.request.contextPath}/internal/image/head-image.jpg">

            </img>
        </div>
        <div class="layui-col-md2 ">
            <button type="button" class="layui-btn my-special-btn" id="test1" style="margin-top: 40px;margin-left: 16px">上传图片</button>
        </div>
    </div>

    <form class="layui-form layui-col-md-offset1 layui-col-md10" style="margin-top: 20px" action="">
        <div class="layui-form-item">
            <label class="layui-form-label " >专栏的名称</label>
            <div class="layui-input-block">
                <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入名称"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label ">专栏的描述</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入相关描述" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label ">其他管理员</label>
            <div class="layui-input-block">
                <input type="text" name="title" lay-verify="title" autocomplete="off" placeholder="输入用户名"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item" style="margin-top: 48px">
            <div class="layui-col-md-offset11">
                <button class="layui-btn my-special-btn" lay-submit="" lay-filter="demo1">创建专栏</button>
            </div>
        </div>
    </form>
</div>


<script>
    var upload = layui.upload; //得到 upload 对象
</script>

</body>
</html>
