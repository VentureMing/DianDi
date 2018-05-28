<%--
  Created by IntelliJ IDEA.
  User: shang
  Date: 2018/5/24
  Time: 17:27
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
    <title>点滴专栏</title>
    <link id="favicon-link" rel="shortcut icon" href="${pageContext.request.contextPath}/internal/image/favicon.ico">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/external/layui/css/layui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/internal/css/special_column_list.css">
</head>
<body>
<div style="text-align: center">
    <div style="  height: 90px;line-height: 90px;font-size: 32px;font-family: 华文中宋, 方正姚体, serif;">点滴专栏
    </div>
    <div style="font-size: 18px">&nbsp;随&nbsp;心 &nbsp;写&nbsp;作&nbsp;·&nbsp;自&nbsp;由&nbsp;表&nbsp;达</div>
</div>

<div class=" special-column-container">
    <div class="layui-row ">

        <div class="layui-col-md3 ">
            <div class="special-column-item " style="text-align: center;">
                <img style="margin-top: 24px;width: 48px;height: 48px;border-radius: 100px"
                     src="${pageContext.request.contextPath}/internal/image/head-image.jpg">
                <div style="text-align: center;margin: 8px 0;font-size: 18px;">篮外空心</div>
                <div style=" height:48px;text-align: center;margin: 4px 16px;font-size: 14px;color:#8D8D8D">
                    关于篮球、NBA、马刺，以及其他。
                </div>
                <div style=" height:30px;text-align: center;margin: 4px 16px;font-size:15px;color:#8D8D8D">574人关注 |
                    11篇文章
                </div>
                <div>

                    <button class="layui-btn   layui-btn-primary"
                            style=" width:96px;color:#5FB878;border-color: #5FB878;border-radius: 4px ">
                        投稿
                    </button>

                </div>

            </div>
        </div>

        <div class="layui-col-md3 ">
            <div class="special-column-item " style="text-align: center;">
                <img style="margin-top: 24px;width: 48px;height: 48px;border-radius: 100px" src="image/demo5.jpg">
                <div style="text-align: center;margin: 8px 0;font-size: 18px;">篮外空心</div>
                <div style=" height:48px;text-align: center;margin: 4px 16px;font-size: 14px;color:#8D8D8D">
                    关于篮球、NBA、马刺，以及其他。
                </div>
                <div style=" height:30px;text-align: center;margin: 4px 16px;font-size:15px;color:#8D8D8D">574人关注 |
                    11篇文章
                </div>
                <div>

                    <button class="layui-btn   layui-btn-primary"
                            style=" width:96px;color:#5FB878;border-color: #5FB878;border-radius: 4px ">
                        投稿
                    </button>

                </div>

            </div>
        </div>
        <div class="layui-col-md3 ">
            <div class="special-column-item " style="text-align: center;">
                <img style="margin-top: 24px;width: 48px;height: 48px;border-radius: 100px" src="image/demo5.jpg">
                <div style="text-align: center;margin: 8px 0;font-size: 18px;">篮外空心</div>
                <div style=" height:48px;text-align: center;margin: 4px 16px;font-size: 14px;color:#8D8D8D">
                    关于篮球、NBA、马刺，以及其他。
                </div>
                <div style=" height:30px;text-align: center;margin: 4px 16px;font-size:15px;color:#8D8D8D">574人关注 |
                    11篇文章
                </div>
                <div>

                    <button class="layui-btn   layui-btn-primary"
                            style=" width:96px;color:#5FB878;border-color: #5FB878;border-radius: 4px ">
                        投稿
                    </button>

                </div>

            </div>
        </div>
        <div class="layui-col-md3 ">
            <div class="special-column-item " style="text-align: center;">
                <img style="margin-top: 24px;width: 48px;height: 48px;border-radius: 100px" src="image/demo5.jpg">
                <div style="text-align: center;margin: 8px 0;font-size: 18px;">篮外空心</div>
                <div style=" height:48px;text-align: center;margin: 4px 16px;font-size: 14px;color:#8D8D8D">
                    关于篮球、NBA、马刺，以及其他。
                </div>
                <div style=" height:30px;text-align: center;margin: 4px 16px;font-size:15px;color:#8D8D8D">574人关注 |
                    11篇文章
                </div>
                <div>

                    <button class="layui-btn   layui-btn-primary"
                            style=" width:96px;color:#5FB878;border-color: #5FB878;border-radius: 4px ">
                        投稿
                    </button>

                </div>

            </div>
        </div>
        <div class="layui-col-md3 ">
            <div class="special-column-item " style="text-align: center;">
                <img style="margin-top: 24px;width: 48px;height: 48px;border-radius: 100px" src="image/demo5.jpg">
                <div style="text-align: center;margin: 8px 0;font-size: 18px;">篮外空心</div>
                <div style=" height:48px;text-align: center;margin: 4px 16px;font-size: 14px;color:#8D8D8D">
                    关于篮球、NBA、马刺，以及其他。
                </div>
                <div style=" height:30px;text-align: center;margin: 4px 16px;font-size:15px;color:#8D8D8D">574人关注 |
                    11篇文章
                </div>
                <div>

                    <button class="layui-btn   layui-btn-primary"
                            style=" width:96px;color:#5FB878;border-color: #5FB878;border-radius: 4px ">
                        投稿
                    </button>

                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>
