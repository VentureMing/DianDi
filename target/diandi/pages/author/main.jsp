<%--
  Created by IntelliJ IDEA.
  User: shang
  Date: 2018/5/10
  Time: 13:45
  Description:首页
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
    <title>点滴-记录你的生活</title>
    <link id="favicon-link" rel="shortcut icon" href="${pageContext.request.contextPath}/internal/image/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/external/layui/css/layui.css" media="all">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/external/layui/css/modules/layui-icon-extend/iconfont.css"
          media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/internal/css/nav_main.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/internal/css/main.css" media="all">

    <script type="application/javascript" src="${pageContext.request.contextPath}/external/jQuery.js"></script>


</head>
<body>

<%--<div class="operation-container border clickable-gray" onclick="scrollToTop();" style="padding: 11px">--%>
<%--&lt;%&ndash;<button articleId="scroll-to-top" title="回到顶部">TOP</button>&ndash;%&gt;--%>
<%--<img class="img24px" src="${pageContext.request.contextPath}/internal/images/icon/icons8-collapse-arrow-64.png" title="回到顶部">--%>
<%--</div>--%>

<div class="main-container">
    <%--左侧文章列表--%>
    <div class="layui-row " style="min-height: 600px">
        <ul id="articleList" class="layui-col-md8 flow-default">
            <%--<li class="layui-card my-list-card" onclick="window.open('pages/author/edit.jsp')">--%>
                <%--<div class="layui-card-header my-layui-card-header">--%>
                    <%--<h2><a onclick="window.open('pages/author/edit.jsp')">卡片面板</a></h2>--%>
                <%--</div>--%>
                <%--<div class="layui-card-body my-layui-card-header layui-row">--%>
                    <%--<div class="layui-col-md9">--%>
                        <%--<h4><img class="item-head-image"--%>
                                 <%--src="${pageContext.request.contextPath}/internal/image/head-image.jpg"><span>爱听故事的人</span>2018-5-18--%>
                        <%--</h4>--%>
                        <%--<div class="card-content">--%>
                            <%--快要离开杭州，我在想要和你说些啥才对得起你在公交上写下的那段话，可我想了很久还是不确定说的内容，就想到哪写到哪吧。在杭州的这三个月，遇到了挺多很好的人，没想到快要离开杭州前，又多了你带来的这份感动。你的这份又和他们的不大相同，这种感动大概两三年没有了吧。现在想想我印象中见你的第一面，应该是在8楼的一个会议室，我上去的时候，你在那玩手机。那天的排练你好像也一直在玩手机，你称赞我们排的整齐我好像还说你敷衍来着。之后排练的时间你一直都很欢乐，总是给我感觉充满阳光。在你没有给我发那段话之前，我真的以为你是个小公主，才会呈现出欢乐的状态。你说，总要逗自己乐吧，那种我们对生活的无奈，让我觉得我们其实有点像。你说，天南地北，常联系。说真的，我不知道有没有机会，生活上没有交集的人，我不大相信我们可以在微信上总能找到闲聊的话题。如果能，那可能是我们孤独这门高级课程的学分还没有修够吧。--%>
                        <%--</div>--%>
                        <%--<h4><i class="iconfont">&#xe605</i>&nbsp;5&nbsp;&nbsp;<i class="iconfont">&#xe86f</i>&nbsp;5&nbsp;&nbsp;<i--%>
                                <%--class="iconfont">&#xe684</i>&nbsp;5</h4>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md3 card-image">--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</li>--%>
        </ul>



        <%--右侧--%>
        <div class="layui-col-md4" style="padding-left:10px;height: auto;background-color: #F6F6F6">
            <div class="main-column-right">
                <div style="width: auto;height:auto; ">

                    <div class="main-column-right-item">
                        <div class="layui-row " style="height: 50px">
                            <div class="layui-col-md3" style="padding-top:12px">
                                <i class="iconfont"
                                   style="color:#FF5722; font-size: 32px;font-weight: normal">&#xe633</i>
                            </div>
                            <div class="layui-col-md7" style="font-size: 16px;padding-top:16px">
                                今日焦点
                            </div>
                            <div class="layui-col-md2" style="padding-top:20px">
                                <i class="iconfont" style="font-size: 10px;color: #FF5722">&#xe624</i>
                            </div>
                        </div>
                    </div>
                    <div class="main-column-right-item">
                        <div class="layui-row" style="height: 50px">
                            <div class="layui-col-md3" style="padding-top:12px">
                                <i class="iconfont"
                                   style=" color:#5FB878 ; font-size: 28px;font-weight: normal">&#xe638</i>
                            </div>

                            <div class="layui-col-md7" style="font-size: 16px;padding-top:16px">
                                最新内容
                            </div>
                            <div class="layui-col-md2" style="padding-top:20px">
                                <i class="iconfont" style="font-size: 10px;color: #5FB878">&#xe624</i>
                            </div>
                        </div>
                    </div>
                    <div class="main-column-right-item">
                        <div class="layui-row" style="height: 50px">
                            <div class="layui-col-md3" style="padding-top:12px">
                                <i class="iconfont"
                                   style="color:#FFCF00 ; font-size: 28px;font-weight: normal">&#xe726</i>
                            </div>

                            <div class="layui-col-md7" style="font-size: 16px;padding-top:16px">
                                本周热文
                            </div>
                            <div class="layui-col-md2" style="padding-top:20px">
                                <i class="iconfont" style="font-size: 10px;color: #FFCF00 ">&#xe624</i>
                            </div>
                        </div>
                    </div>
                    <div class="main-column-right-item">
                        <div class="layui-row" style="height: 50px">
                            <div class="layui-col-md3" style="padding-top:12px">
                                <i class="iconfont"
                                   style="color:#5478f0 ; font-size: 28px;font-weight: normal">&#xe727</i>
                            </div>

                            <div class="layui-col-md7" style="font-size: 16px;padding-top:16px">
                                本月热文
                            </div>
                            <div class="layui-col-md2" style="padding-top:20px">
                                <i class="iconfont" style="font-size: 10px;color: #5478f0">&#xe624</i>
                            </div>
                        </div>
                    </div>

                </div>
                <hr>

                <div style="margin-top: 24px">
                    <div style=" margin: 12px 0;color:#8D8D8D;">
                        推荐作者
                    </div>
                    <div>
                        <div style="height: 50px">
                            <div class="layui-row">
                                <img class="layui-col-md3" style="width: 40px;height:40px;border-radius: 100px"
                                     src="${pageContext.request.contextPath}/internal/image/demo/demo4.jpg">
                                <div class="layui-col-md7" style="padding-left: 8px;padding-top: 2px">
                                    <div>须僧</div>
                                    <div style="font-size: 12px;color:#8D8D8D;">56篇文章·66.3k字</div>
                                </div>
                                <div class="layui-col-md2">
                                    <a style="font-size:12px;color: #5FB878;" href=""> <i class="iconfont"
                                                                                          style="font-size:12px;">&#xe889</i>关注</a>
                                </div>
                            </div>
                        </div>
                        <div style="height: 50px">
                            <div class="layui-row">
                                <img class="layui-col-md3" style="width: 40px;height:40px;border-radius: 100px"
                                     src="${pageContext.request.contextPath}/internal/image/head-image.jpg">
                                <div class="layui-col-md7" style="padding-left: 8px;padding-top: 2px">
                                    <div>乔汉童</div>
                                    <div style="font-size: 12px;color:#8D8D8D;">177篇文章·156.3k字</div>
                                </div>
                                <div class="layui-col-md2">
                                    <a style="font-size:12px;color: #5FB878;" href=""> <i class="iconfont"
                                                                                          style="font-size:12px;">&#xe889</i>关注</a>
                                </div>
                            </div>
                        </div>

                        <div style="height: 50px">
                            <div class="layui-row">
                                <img class="layui-col-md3" style="width: 40px;height:40px;border-radius: 100px"
                                     src="${pageContext.request.contextPath}/internal/image/demo/demo1.jpg">
                                <div class="layui-col-md7" style="padding-left: 8px;padding-top: 2px">
                                    <div>寻麦</div>
                                    <div style="font-size: 12px;color:#8D8D8D;">17篇文章·85.3k字</div>
                                </div>
                                <div class="layui-col-md2">
                                    <a style="font-size:12px;color: #5FB878;" href=""> <i class="iconfont"
                                                                                          style="font-size:12px;">&#xe889</i>关注</a>
                                </div>
                            </div>
                        </div>

                        <div style="height: 50px">
                            <div class="layui-row">
                                <img class="layui-col-md3" style="width: 40px;height:40px;border-radius: 100px"
                                     src="${pageContext.request.contextPath}/internal/image/demo/demo3.jpg">
                                <div class="layui-col-md7" style="padding-left: 8px;padding-top: 2px">
                                    <div>经年鲤</div>
                                    <div style="font-size: 12px;color:#8D8D8D;">84篇文章·111.9k字</div>
                                </div>
                                <div class="layui-col-md2">
                                    <a style="font-size:12px;color: #5FB878;" href=""> <i class="iconfont"
                                                                                          style="font-size:12px;">&#xe889</i>关注</a>
                                </div>
                            </div>
                        </div>
                        <div style="height: 50px">
                            <div class="layui-row">
                                <img class="layui-col-md3" style="width: 40px;height:40px;border-radius: 100px"
                                     src="${pageContext.request.contextPath}/internal/image/demo/demo2.jpg">
                                <div class="layui-col-md7" style="padding-left: 8px;padding-top: 2px">
                                    <div>胡七筒</div>
                                    <div style="font-size: 12px;color:#8D8D8D;">77篇文章·99.3k字</div>
                                </div>
                                <div class="layui-col-md2">
                                    <a style="font-size:12px;color: #5FB878;" href=""> <i class="iconfont"
                                                                                          style="font-size:12px;">&#xe889</i>关注</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <button class="layui-btn" style="margin-top:8px;width: 100%;height: 36px;background-color: #5FB878">查看全部<i class="layui-icon">&#xe602</i></button>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>




<script type="application/javascript" src="${pageContext.request.contextPath}/external/layui/layui.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/internal/js/main.js"></script>
<script type="application/javascript">

    var loginAuthorId=1;
    <c:if test="${not empty sessionScope['authorLoginSignal']}">
     loginAuthorId = ${sessionScope["authorId"]};
    var authorLoginSignal = '${sessionScope['authorLoginSignal']}';
    </c:if>

    layui.use('element', function () {
        var element = layui.element;

    });
</script>

<script type="application/javascript" src="${pageContext.request.contextPath}/internal/js/common.js"></script>
<script type="application/javascript" src="${pageContext.request.contextPath}/internal/js/main.js"></script>
</body>
</html>
