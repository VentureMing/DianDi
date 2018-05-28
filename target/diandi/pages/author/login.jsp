<%--
  Created by IntelliJ IDEA.
  User: shang
  Date: 2018/5/12
  Time: 12:26
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
    <title>点滴登录</title>
    <link id="favicon-link" rel="shortcut icon" href="${pageContext.request.contextPath}/internal/image/favicon.ico">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/internal/css/login-and-register.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/external/layui/css/layui.css" media="all">
    <script src="${pageContext.request.contextPath}/external/jQuery.js" charset="utf-8"></script>

</head>
<body>

<section id="account-view" class="account-view section">
    <!-- <div class="beta"></div> -->
    <div class="container" style="height: 668px; margin-top: 60px;">
        <div class="login-box action-box" state="leave-state">
            <div class="logo-block">
                <a class="logo-pic account-sprite" href="home_page.jsp"></a>
                <span class="logo-span account-sprite"></span>
            </div>

            <div class="layui-tab layui-tab-brief " lay-filter="docDemoTabBrief">
                <ul class="layui-tab-title">
                    <li class="layui-this">邮箱登录</li>
                    <li>微信登录</li>
                    <li>电话登录</li>
                </ul>
                <div class="layui-tab-content ">
                    <div class="layui-tab-item my-action-box layui-show">
                        <div class="input-box">
                            <input id="loginEmail"  placeholder="邮箱" type="text">
                        </div>
                        <div class="input-box password">
                            <input id="loginEmailPassword"  placeholder="密码" type="password">
                            <span class="error-span"></span>
                            <span class="forget-password">忘记密码？</span>
                        </div>

                        <div class="error-msg" id="loginErrorMsg"></div>
                        <button class="login-submit-btn submit-btn btn dark" id="emailLoginButton"
                                onclick="emailLogin()">登录
                        </button>
                        <div class="change-block">
                            没有帐号？<a href=${pageContext.request.contextPath}/pages/author/register.jsp" class="to-register">注册</a>
                        </div>
                    </div>
                    <div class="layui-tab-item">

                        <img class="wechat-action-img wechat-login" alt="微信二维码"
                             src="${pageContext.request.contextPath}/internal/image/qrcode.png">
                        <span class="wechat-action-info">微信扫一扫即可登录</span>
                        <div class="change-block">
                            <div>
                                没有帐号？<a href="${pageContext.request.contextPath}/pages/author/register.jsp" class="to-register">注册</a>
                            </div>
                        </div>

                    </div>
                    <div class="layui-tab-item my-action-box">

                        <div class="input-box">
                            <input class="phone-input" name="phone" placeholder="手机号码" type="text">
                            <span class="error-span"></span>
                        </div>
                        <div class="input-box password">
                            <input class="password-input" name="password" placeholder="验证码" type="password">
                            <span class="error-span"></span>
                            <span class="forget-password">获取验证码</span>
                        </div>
                        <div class="error-msg" id="phoneLoginErrorMsg"></div>
                        <button class="login-submit-btn submit-btn btn dark" id="phoneLoginButton"
                                onclick="phoneLogin()">登录
                        </button>
                        <div class="change-block">
                            没有帐号？<a href="${pageContext.request.contextPath}/pages/author/register.jsp" class="to-register">注册</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="${pageContext.request.contextPath}/external/layui/layui.all.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/internal/js/common.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/internal/js/login.js" charset="utf-8"></script>

<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('element', function () {
        var $ = layui.jquery
            , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
    });
</script>


</body>
</html>
