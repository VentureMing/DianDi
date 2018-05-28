<%--
  Created by IntelliJ IDEA.
  User: shang
  Date: 2018/5/18
  Time: 13:37
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
    <title>${main["title"]}</title>
    <link id="favicon-link" rel="shortcut icon" href="${pageContext.request.contextPath}/internal/image/favicon.ico">


    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/external/layui/css/layui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/external/layui/css/modules/layui-icon-extend/iconfont.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/external/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/external/Tipso/tipso.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/internal/css/comment.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/internal/css/read_article.css">

</head>
<body>

<div class="fixed-block">

    <button id="toTop" class="layui-btn layui-btn-primary my-fixed-btn"
            style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;display: none"><i class="iconfont">&#xe892</i>
    </button>
    <c:if test="${main.authorId eq sessionScope.authorId}">
        <button id="toContribute" onclick="contributeArticle()" class="layui-btn layui-btn-primary my-fixed-btn"
                data-tipso="投稿" style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;">
            <i class="iconfont">&#xe893</i>
        </button>
    </c:if>
    <c:if test="${(not empty sessionScope['authorLoginSignal']) and (main.authorId ne sessionScope.authorId)}">
        <c:choose>
            <c:when test="${not empty likeState}">
                <button id="toLike" data-tipso="取消喜欢" onclick="likeArticle(this)"
                        class="layui-btn layui-btn-primary my-fixed-btn"
                        style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;">
                    <i class="iconfont" style="color: #5FB878">&#xe86f</i>
                </button>
            </c:when>
            <c:otherwise>
                <button id="toLike" data-tipso="添加喜欢" onclick="likeArticle(this)"
                        class="layui-btn layui-btn-primary my-fixed-btn"
                        style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;">
                    <i class="iconfont">&#xe870</i>
                </button>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty collectState}">
                <button id="toCollect" data-tipso="取消收藏" onclick="collectArticle(this)"
                        class="layui-btn layui-btn-primary my-fixed-btn"
                        style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;">
                    <i class="iconfont" style="color: #5FB878">&#xe86d</i>
                </button>
            </c:when>
            <c:otherwise>
                <button id="toCollect" data-tipso="点击收藏" onclick="collectArticle(this)"
                        class="layui-btn layui-btn-primary my-fixed-btn"
                        style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;">
                    <i class="iconfont">&#xe86e</i>
                </button>
            </c:otherwise>
        </c:choose>
    </c:if>

</div>

<div class="layui-header header ">

    <a href="javascript:;">
        <img src="${pageContext.request.contextPath}/internal/image/read_logo.png" style="padding-top: 27px">
    </a>

    <ul class="layui-nav">
        <li class="layui-nav-item" style="margin-right: 5px">
            <img id="toHome" data-tipso="前往主页"
                 onclick="window.open('${pageContext.request.contextPath}/pages/author/home.jsp?authorId=${main.authorId}', '_self')"
                 src="http://t.cn/RCzsdCq" class="layui-nav-img" style="width:24px;height: 24px;padding-top: 2px">
            <%--点击前往作者主页--%>
        </li>

        <c:choose>
            <c:when test="${main.authorId eq sessionScope.authorId}">
                <li class="layui-nav-item" lay-unselect="">
                    <button class="layui-btn layui-btn-sm layui-btn-primary my-read-btn">
                        <i class="layui-icon ">&#xe671</i>
                    </button>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">编辑文章</a></dd>
                        <dd><a href="javascript:;">删除文章</a></dd>
                    </dl>
                </li>

            </c:when>
            <c:otherwise>

                <li class="layui-nav-item">
                    <button id="toFollowed" data-tipso="关注作者"
                            class=" layui-btn layui-btn-sm layui-btn-primary my-read-btn">
                        <i class="layui-icon layui-icon-add-1"></i>
                    </button>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
<div class="article-container">

    <div class="article-container-box">
        <div class="article-container-box-title">
            ${main["title"]}
        </div>
        <hr>
        <div>
            ${main["content"]}
        </div>
    </div>

</div>
<div style="height: 24px"></div>
<div class="comment-footer">


    <div class="commentAll">

        <div style="height: 90px;line-height: 90px;text-align: center; ">
            <button class="layui-btn layui-btn-radius" style="font-size:16px;background-color: #5FB878;width: 80px">登录</button><span style="margin-left: 12px">后发表评论</span>
        </div>
        <%--<div class="reviewArea clearfix">--%>
        <%--<textarea class="content comment-input  my-textarea" placeholder="Please enter a comment&hellip;"--%>
                  <%--onkeyup="keyUP(this)"></textarea>--%>
            <%--<button href="javascript:;" class=" plBtn ">评论</button>--%>
        <%--</div>--%>
        <!--评论区域 end-->
        <!--回复区域 end-->

        <div class="comment-show">
            <div class="comment-show-con">
                <div class=" pl-info layui-row ">

                    <div class="layui-col-md1">
                        <img class="comment-show-con-img" src="images/header-img-comment_03.png">
                    </div>
                    <div class="layui-col-md6" style="margin-left: 8px">
                        <div class="comment-size-name">张三</div>
                        <div class="comment-size-time">2017-5-2 11:11:39</div>
                    </div>
                </div>
                <div class="comment-content" style=" margin: 8px 0 10px 0;font-size: 16px;">
                    一个实例对象通过Class方法获取的Class就是它的isa指针指向的类对象，而类对象不是元类，类对象的isa指针指向的对象是元类，是否这样理解？
                </div>
                <div class="layui-row date-dz">
                    <div class="layui-col-md2" style="width: auto">
                        <i class="layui-icon" style="margin-right:8px">&#xe6c6</i><span>588人赞</span>
                    </div>
                    <div class="layui-col-md2 date-dz-hf">
                        <i class="layui-icon" style="margin:0 8px 0 14px">&#xe66c</i> <span
                            class=" pl-hf hf-con-inline">回复</span>
                    </div>
                    <div class="layui-col-md-offset11 removeInline">
                        删除
                    </div>
                </div>

                <div class="hf-list-con">
                    <div class="hf-list-con-item">
                        <div class="pl-info" style="height: auto;">
                            <span class="comment-size-name">我的名字</span><span style="padding-right: 8px">：@我切奇</span>何况期望和切奇企鹅红旗二号顷刻间去
                        </div>
                        <div class="layui-row  date-dz hf-list-data-dz">
                            <div class="layui-col-md2 hf-list-time" style="width: auto">
                                2017-5-2 11:11:39
                            </div>
                            <div class="layui-col-md2 date-dz-hf hf-list-date-dz-hf">
                                <i class="layui-icon" style="margin:0 2px 0 14px">&#xe66c</i> <a
                                    class=" pl-hf ">回复</a>
                            </div>
                            <div class="layui-col-md-offset11 removeInline">
                                删除
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </div>
    </div>
</div>
<div style="margin-bottom: 72px"></div>
<div></div>
<script src="${pageContext.request.contextPath}/external/jQuery.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/external/layui/layui.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/external/Tipso/tipso.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/external/jquery.flexText.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/internal/js/read_article.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/internal/js/comment.js" charset="utf-8"></script>

<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>

    layui.use('element', function () {
        var element = layui.element;
    });
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var layer = layui.layer;
    });


    function contributeArticle() {

    }

    $('#toContribute').tipso({
        useTitle: false,
        position: 'left',
        width: 96
    });
    $('#toLike').tipso({
        useTitle: false,
        position: 'left',
        width: 96
    });
    $('#toCollect').tipso({
        useTitle: false,
        position: 'left',
        width: 96
    });
    $('#toFollowed').tipso({
        useTitle: false,
        position: 'bottom',
        width: 96
    });
    $('#toHome').tipso({
        useTitle: false,
        position: 'bottom',
        width: 96
    });
    $(function () {
        $(window).scroll(function () {
            var t = $(this).scrollTop();
            if (t > 300) {
                $("#toTop").stop().fadeIn(300);
            } else {
                $("#toTop").stop().fadeOut(300);
            }
        });
        $("#toTop").click(function () {
            $("body,html").stop().animate({
                scrollTop: 0
            }, 300); //html是为了兼容火狐和IE
        });
    });

    var articleOwnerAuthorId = ${main["authorId"]};
    var loginAuthorId = 1;
    var articleId =${main["articleId"]};
    <c:if test="${not empty sessionScope['authorLoginSignal']}">
    var loginAuthorId = ${sessionScope["authorId"]};
    var authorLoginSignal = '${sessionScope['authorLoginSignal']}';
    </c:if>
</script>
</body>
</html>
