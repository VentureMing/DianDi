$(document).ready(function () {
    // 加载初始文章列表
    initArticle();


    //
    // // 加载高级检索的排序规则部分
    // loadSortRule();
    //

});

// var defaultArticleCount = 10;
// 文章检索条件
var filterData = {
    kword: null,
    sort: null,
    order: null
};

// 加载初始文章列表
function initArticle() {
    // 将会加载两次
    setFilterData(null, "release_date", "desc");
    setArticles();
}

// 填充检索条件
function setFilterData(kword, sort, order) {
    filterData.kword = kword;
    filterData.sort = sort;
    filterData.order = order;
}


function setArticles() {
    $.get(
        '/article',
        {
            authorId: loginAuthorId,
            kword: filterData.kword,
            sort: filterData.sort,
            order: filterData.order
        }, function (result) {
            addArticleList(result.data)
        }, 'json'
    );
}

function addArticleList(articleArray) {
    if (articleArray == null || articleArray.length === 0) {
        $('#articleList').html('');
    } else {
        var pageCount = articleArray.length / 10;
        var index = 0;
        layui.use('flow', function () {
            var flow = layui.flow;
            flow.load({
                elem: '#articleList',//流加载容器
                done: function (page, next) { //执行下一页的回调
                    //模拟数据插入
                    setTimeout(function () {
                        var lis = [];
                        var flag = articleArray.length;
                        if ((10 * page) < articleArray.length) flag = 10 * page;

                        for (index; index < flag; index++) {
                            lis.push('<li class="layui-card my-list-card" onclick="window.open(\'/read/' + articleArray[index].articleId + '\',\'_blank\')">\n' +
                                '                <div class="layui-card-header my-layui-card-header">\n' +
                                '                    <h2><a onclick="window.open(\'pages/author/edit.jsp\')">' + articleArray[index].title + '</a></h2>\n' +
                                '                </div>\n' +
                                '                <div class="layui-card-body my-layui-card-header layui-row">\n' +
                                '                    <div class="layui-col-md9">\n' +
                                '                        <h4><img class="item-head-image"\n' +
                                '                                 src="${pageContext.request.contextPath}/internal/image/head-image.jpg"><span>' + articleArray[index].nickName + '</span>'+ dateFormat(articleArray[index].releaseDate) +'\n' +
                                '                        </h4>\n' +
                                '                        <div class="card-content">\n' +
                                                                articleArray[index].summary +
                                '                        </div>\n' +
                                '                        <h4><i class="iconfont">&#xe605</i>&nbsp;'+ articleArray[index].viewCount + '&nbsp;&nbsp;<i class="iconfont">&#xe86f</i>&nbsp;'+articleArray[index].likeCount+'&nbsp;&nbsp;<i\n' +
                                '                                class="iconfont">&#xe684</i>&nbsp;'+articleArray[index].commentCount +'</h4>\n' +
                                '                    </div>\n' +
                                '                    <div class="layui-col-md3 card-image">\n' +
                                '                    </div>\n' +
                                '                </div>\n' +
                                '            </li>')
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
