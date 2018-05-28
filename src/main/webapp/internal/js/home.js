$(document).ready(function () {
    // 加载初始文章列表
    init();


    //
    // // 加载高级检索的排序规则部分
    // loadSortRule();
    //

});

function init() {
    setArticleList();
}

function setArticleList() {

    $.ajax({
        type: "get",
        url: "/home/articles",
        data: "authorId=" + pageAuthorId,
        async: false,
        success: function (result) {
            addHomeArticleList(result.data);
        }
    });
    // $.get(
    //     '/home/articles',
    //     {
    //         authorId: pageAuthorId
    //     }, function (result) {
    //
    //     }, 'json'
    // );

}

function addHomeArticleList(articleArray) {

    if (articleArray == null || articleArray.length === 0) {
        $('#homeArticleList').html('<li class="layui-card my-home-list-card" onclick="window.open(\'/read/'+ articleArray[index].articleId +'\',\'_blank\')">\n' +
            '                    <div class="layui-card-header  my-home-card-header">\n' +
            '                        <h2><a onclick="window.open(\'pages/author/edit.jsp\')">' + articleArray[index].title + '</a></h2>\n' +
            '                    </div>\n' +
            '                    <div class="layui-card-body  my-home-card-header layui-row">\n' +
            '                        <div class="layui-col-md9">\n' +
            '                            <h4><img class="home-item-head-image"\n' +
            '                                     src="${pageContext.request.contextPath}/internal/image/head-image.jpg"><span>'+ articleArray[index].nickName +'</span>'+ dateFormat(articleArray[index].releaseDate) +'\n' +
            '                            </h4>\n' +
            '                            <div class="home-card-content">\n' +
            articleArray[index].summary +
            '                            </div>\n' +
            '                            <h4><i class="iconfont">&#xe605</i>&nbsp;'+ articleArray[index].viewCount + '&nbsp;&nbsp;<i class="iconfont">&#xe86f</i>&nbsp;'+articleArray[index].likeCount+'&nbsp;&nbsp;<i\n' +
            '                                    class="iconfont">&#xe684</i>&nbsp;'+articleArray[index].commentCount +'</h4>\n' +
            '                        </div>\n' +
            '                        <div class="layui-col-md3 home-card-image">\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                    <hr style="margin: 0 16px">\n' +
            '                </li>');
    } else {
        var pageCount = articleArray.length / 10;
        var index = 0;
        layui.use('flow', function () {
            var flow = layui.flow;
            flow.load({
                elem: '#homeArticleList',//流加载容器
                done: function (page, next) { //执行下一页的回调
                    //模拟数据插入
                    setTimeout(function () {
                        var lis = [];
                        var flag = articleArray.length;
                        if ((10 * page) < articleArray.length) flag = 10 * page;

                        for (index; index < flag; index++) {
                            // if (index === 0) {
                            //     lis.push('<li>\n' +
                            //         '                <div class="layui-row " style="height: 80px;margin-top:24px;margin-bottom: 16px">\n' +
                            //         '                    <div class="layui-col-md2 layui-col-md-offset1">\n' +
                            //         '                        <image style="width: 80px;border-radius: 100px"\n' +
                            //         '                               src="${pageContext.request.contextPath}/internal/image/head-image.jpg">\n' +
                            //         '                        </image>\n' +
                            //         '                    </div>\n' +
                            //         '                    <div class="layui-col-md6" style="height: 80px">\n' +
                            //         '                        <div style="height: 40px ;font-size: 24px;padding-top: 8px;font-family: 华文中宋, 方正姚体, serif;">作者\n' +
                            //         '                        </div>\n' +
                            //         '                        <span class="layui-breadcrumb" style="height: 40px;width: auto;" lay-separator="|">\n' +
                            //         '                            <a href=""><span style="padding-right: 8px;font-size: 15px;">118</span>文章</a>\n' +
                            //         '                            <a href=""><span style="padding-right: 8px;font-size: 15px;">132121</span>字数</a>\n' +
                            //         '                            <a href=""><span style="padding-right: 8px;font-size: 15px;">456456</span>关注</a>\n' +
                            //         '                        </span>\n' +
                            //         '                    </div>\n' +
                            //         '                    <div class="layui-col-md2" style="height: 80px">\n' +
                            //         '                        <button class=" layui-btn layui-btn-radius"\n' +
                            //         '                                style="width: 80px;margin-top:28px;background-color: #5FB878;">\n' +
                            //         '                            关注\n' +
                            //         '                        </button>\n' +
                            //         '                    </div>\n' +
                            //         '\n' +
                            //         '                </div>\n' +
                            //         '                <hr style="margin: 0 16px">\n' +
                            //         '            </li>')
                            // }
                            lis.push('<li class="layui-card my-home-list-card" onclick="window.open(\'/read/'+ articleArray[index].articleId +'\',\'_blank\')">\n' +
                                '                    <div class="layui-card-header  my-home-card-header">\n' +
                                '                        <h2><a onclick="window.open(\'pages/author/edit.jsp\')">' + articleArray[index].title + '</a></h2>\n' +
                                '                    </div>\n' +
                                '                    <div class="layui-card-body  my-home-card-header layui-row">\n' +
                                '                        <div class="layui-col-md9">\n' +
                                '                            <h4><img class="home-item-head-image"\n' +
                                '                                     src="${pageContext.request.contextPath}/internal/image/head-image.jpg"><span>'+ articleArray[index].nickName +'</span>'+ dateFormat(articleArray[index].releaseDate) +'\n' +
                                '                            </h4>\n' +
                                '                            <div class="home-card-content">\n' +
                                                                    articleArray[index].summary +
                                '                            </div>\n' +
                                '                            <h4><i class="iconfont">&#xe605</i>&nbsp;'+ articleArray[index].viewCount + '&nbsp;&nbsp;<i class="iconfont">&#xe86f</i>&nbsp;'+articleArray[index].likeCount+'&nbsp;&nbsp;<i\n' +
                                '                                    class="iconfont">&#xe684</i>&nbsp;'+articleArray[index].commentCount +'</h4>\n' +
                                '                        </div>\n' +
                                '                        <div class="layui-col-md3 home-card-image">\n' +
                                '                        </div>\n' +
                                '                    </div>\n' +
                                '                    <hr style="margin: 0 16px">\n' +
                                '                </li>')
                        }

                        index = 10 * page;
                        //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                        //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                        next(lis.join(''), page < pageCount); //假设总页数为 10
                    }, 500);
                }
            });
        })
    }
}

function setAuthorBasic() {

}