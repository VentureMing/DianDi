<%--
  Created by IntelliJ IDEA.
  User: shang
  Date: 2018/5/6
  Time: 18:37
  Description:个人主页
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
    <meta charset="utf-8"/>
    <title>点滴-记录你的生活</title>
    <link id="favicon-link" rel="shortcut icon" href="${pageContext.request.contextPath}/internal/image/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/external/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/internal/css/nav_main.css" media="all">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/external/layui/css/modules/layui-icon-extend/iconfont.css"
          media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/internal/css/home.css" media="all">

</head>
<body>
<div class="home-list-container">
    <div class="layui-row ">
        <ul id="homeArticleList" class="layui-col-md8 flow-default" style="background-color: #FFFFFF;height:600px">
            <li>
                <div class="layui-row home-top">
                    <div class="layui-col-md2 layui-col-md-offset1">
                        <image class="home-top-image"
                               src="${pageContext.request.contextPath}/internal/image/head-image.jpg">
                        </image>
                    </div>
                    <div class="layui-col-md5 home-top-info">
                        <div class="home-top-author-name">作者
                        </div >
                        <div class="home-top-author-statistics" >
                            <a href="javascript:(0); " ><span >118</span>文章</a>
                            <i >丨</i>
                            <a href="javascript:(0);"><span >132121</span>字数</a>
                            <i>丨</i>
                            <a href="javascript:(0);"><span >456456</span>关注</a>
                        </div>
                    </div>
                    <div class="layui-col-md3" style="height: 80px">

                        <button class=" layui-btn layui-btn-radius layui-btn-primary"
                                style="width: 80px;margin-top:28px;border:1px solid #5FB878;color: #5FB878">
                         投稿
                        </button>
                        <button class=" layui-btn layui-btn-radius"
                                style="width: 80px;margin-top:28px;background-color: #5FB878;">
                            关注
                        </button>
                    </div>

                </div>
                <hr style="margin: 0 16px">
            </li>

            <%--<li class="layui-card my-home-list-card" onclick="window.open('pages/author/edit.jsp')">--%>
            <%--<div class="layui-card-header  my-home-card-header">--%>
            <%--<h2><a onclick="window.open('pages/author/edit.jsp')">卡片面板</a></h2>--%>
            <%--</div>--%>
            <%--<div class="layui-card-body  my-home-card-header layui-row">--%>
            <%--<div class="layui-col-md9">--%>
            <%--<h4><img class="home-item-head-image"--%>
            <%--src="${pageContext.request.contextPath}/internal/image/head-image.jpg"><span>爱听故事的人</span>2018-5-18--%>
            <%--</h4>--%>
            <%--<div class="home-card-content">--%>
            <%--快要离开杭州，我在想要和你说些啥才对得起你在公交上写下的那段话，可我想了很久还是不确定说的内容，就想到哪写到哪吧。在杭州的这三个月，遇到了挺多很好的人，没想到快要离开杭州前，又多了你带来的这份感动。你的这份又和他们的不大相同，这种感动大概两三年没有了吧。现在想想我印象中见你的第一面，应该是在8楼的一个会议室，我上去的时候，你在那玩手机。那天的排练你好像也一直在玩手机，你称赞我们排的整齐我好像还说你敷衍来着。之后排练的时间你一直都很欢乐，总是给我感觉充满阳光。在你没有给我发那段话之前，我真的以为你是个小公主，才会呈现出欢乐的状态。你说，总要逗自己乐吧，那种我们对生活的无奈，让我觉得我们其实有点像。你说，天南地北，常联系。说真的，我不知道有没有机会，生活上没有交集的人，我不大相信我们可以在微信上总能找到闲聊的话题。如果能，那可能是我们孤独这门高级课程的学分还没有修够吧。--%>
            <%--</div>--%>
            <%--<h4><i class="iconfont">&#xe605</i>&nbsp;5&nbsp;&nbsp;<i class="iconfont">&#xe86f</i>&nbsp;5&nbsp;&nbsp;<i--%>
            <%--class="iconfont">&#xe684</i>&nbsp;5</h4>--%>
            <%--</div>--%>
            <%--<div class="layui-col-md3 home-card-image">--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<hr style="margin: 0 16px">--%>
            <%--</li>--%>

        </ul>

        <div class="layui-col-md4" style="padding-left:10px;height: auto;background-color: #F6F6F6">
            <div class="home-column-right" style="    min-height: 400px;">
                <div style="width: auto;height:auto;">
                    <div class="layui-row home-column-right-item-title">
                        <div class="layui-col-md4">
                            专栏公告
                        </div>
                    </div>
                    <div >
                        本专题是一个学术类文章的综合性专题。收录文学、历史、哲学、艺术、法学、社会学、心理学、政治经济等领域相关文章。
                        思想、深度和趣味兼顾。学问不无聊，研究有意思。

                    </div>
                </div>
                <hr>

                <div>
                    <div class="home-column-right-item-title">
                        管理员
                    </div>
                    <div class="home-column-right-item">
                       <img src="${pageContext.request.contextPath}/internal/image/head-image.jpg" style="width:32px;height: 32px;border-radius: 100px;">
                        <span style="margin-left:8px">小明</span>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script type="application/javascript" src="${pageContext.request.contextPath}/external/jQuery.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/external/layui/layui.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/internal/js/common.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/internal/js/home.js"></script>

<script>
    var pageAuthorId =${param.authorId};
    layui.use('element', function () {
        var element = layui.element;

    });
</script>
</body>

</html>
