function checkLogin() {
    if (!authorLoginSignal) {
        window.location.href = "pages/author/login.jsp";
    } else return true;
}

function likeArticle(th) {
    if (!checkLogin()) return;

    var like = $(th);
    if (like.attr('data-tipso') === '添加喜欢') {
        $.post(
            '/author/' + loginAuthorId + '/' + articleId + '/operate=like',
            null,
            function (result) {
                if (result.code === 0) {
                    $('<button id="toLike" data-tipso="取消喜欢"  onclick="likeArticle(this)" class="layui-btn layui-btn-primary my-fixed-btn"\n' +
                        '                        style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;">\n' +
                        '                    <i class="iconfont" style="color: #5FB878">&#xe86f</i>\n' +
                        '                </button>').replaceAll("#toLike");
                    location.reload();
                }
            }
        )
    } else {
        $.ajax({
            url: '/author/' + loginAuthorId + '/' + articleId + '/operate=like',
            type: 'delete',
            success: function (result) {
                if (result.code === 0) {
                    $('<button id="toLike" data-tipso="添加喜欢" onclick="likeArticle(this)" class="layui-btn layui-btn-primary my-fixed-btn"\n' +
                        '                        style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;">\n' +
                        '                    <i class="iconfont">&#xe870</i>\n' +
                        '                </button>').replaceAll("#toLike");
                    location.reload();
                }
            }
        });
    }

}

function collectArticle(th) {
    if (!checkLogin()) return;

    var collect = $(th);
    if (collect.attr('data-tipso') === '点击收藏') {
        $.post(
            '/author/' + loginAuthorId + '/' + articleId + '/operate=collect',
            null,
            function (result) {
                if (result.code === 0) {
                    $('<button id="toCollect" data-tipso="取消收藏"  onclick="collectArticle(this)" class="layui-btn layui-btn-primary my-fixed-btn"\n' +
                        '                        style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;">\n' +
                        '                    <i class="iconfont" style="color: #5FB878">&#xe86d</i>\n' +
                        '                </button>').replaceAll("#toCollect");
                    location.reload();
                }
            }
        )
    } else {
        $.ajax({
            url: '/author/' + loginAuthorId + '/' + articleId + '/operate=collect',
            type: 'delete',
            success: function (result) {
                if (result.code === 0) {
                   $('<button id="toCollect" data-tipso="点击收藏" onclick="collectArticle(this)" class="layui-btn layui-btn-primary my-fixed-btn"\n' +
                       '                        style="width: 50px;height: 50px;padding: 0;margin:2px 0 ;">\n' +
                       '                    <i class="iconfont">&#xe86e</i>\n' +
                       '                </button>').replaceAll("#toCollect");
                    location.reload();
                }
            }
        });
    }

}


function shareArtcle() {

}

function complainArticle() {
    if (!checkLogin()) return;

}

function loadComment() {

    $.get(
        '/article/' + articleId + '/comment',
        {
            rows: 1000,
            offset: 0
        },
        function (result) {

            var container = $('#commentContainer');
            var html = '';

            if (result.code === 0) {

                var array = result.data;
                $('#commentCount').html(array.length);

                for (var index in array) {
                    var comment = array[index];
                    var del = '';

                    if (isLoginAuthorComment(comment.spokesman.id)) {
                        del = '<span class=" vertical-line">&nbsp;&nbsp;|&nbsp;&nbsp;</span>' +
                            '<span style="cursor: pointer" onclick="deleteComment(' + comment.id + ')">删除</span>';
                    }

                    var bgname = comment.spokesman.username;
                    html += '<div class="comment">' +

                        '<b style="cursor: pointer" data-toggle="tooltip" title="' + comment.spokesman.profile.aboutMe +
                        '"data-placement="top"' +
                        'onclick="window.open(\'/' + bgname + '/archives\',\'_blank\')">' + bgname +
                        '</b>' +
                        '&nbsp;&nbsp;<small style="color: gray">' + dateFormat_(comment.releaseDate) + del +
                        '</small>' +

                        '<hr class="default-line">' +
                        '<dl class="dl-horizontal">' +

                        '<dt><br>' +
                        '<img style="cursor: pointer" class="img-circle img64px" src="' + comment.spokesman.avatar.path +
                        '" onclick="window.open(\'/' + bgname + '/archives\',\'_blank\')">' +
                        '&nbsp;&nbsp;&nbsp;&nbsp;</dt>' +

                        '<dd style="word-wrap: break-word"><br>' +
                        comment.content + '</dd>' +
                        '</dl>' +
                        '</div>';
                }

            } else if (result.code === 14) {
                $('#commentCount').html('0');

                if (authorLoginSignal) {
                    html = '<h4>还没有留言，留下一个留言吧！</h4>';
                } else {
                    html = '<h4>还没有留言，<a onclick="$(\'#loginDialog\').modal(\'show\')">登录</a>&nbsp;' +
                        '或&nbsp;<a href="/register">注册</a>&nbsp;然后留下一个留言吧！</h4>';
                }
            } else {
                html += result.msg;
            }

            container.html(html);
            initToolTip();

        }
    )

}

// 登录博主的留言
function isLoginAuthorComment(bgid) {
    if (typeof(loginAuthorId) === "undefined") return false;
    else return loginAuthorId === bgid;
}

// 删除留言
function deleteComment(bgid) {
    $.ajax({
        url: '/author/' + loginAuthorId + '/comment/' + bgid + '?articleId=' + articleId,
        async: false,
        type: 'delete',
        success: function (result) {
            if (result.code === 0) {
                loadComment();
            }
        }
    });
}

// 留言字数上限
var commentMaxLimit = 400;

function leaveAComment() {
    var dom = $('#leaveAComment');
    var comment = dom.val();

    if (isStrEmpty(comment)) {
        error('留言不能为空', 'commentErrorMsg', true, 3000);
        return;
    }

    if (comment.length > commentMaxLimit) {
        error('留言字数最多为400字', 'commentErrorMsg', true, 3000);
        return;
    }

    disableButton(false, 'commentBtn', '正在留言...', "button-disable");
    $.post(
        '/author/' + loginAuthorId + '/comment',
        {
            articleId: articleId,
            content: comment,
            listenerId: articleOwnerAuthorId
        },
        function (result) {
            if (result.code === 0) {
                disableButton(false, 'commentBtn', '留言成功', "button-disable");

                setTimeout(function () {
                    disableButton(true, 'commentBtn', '提交', "button-disable");
                    loadComment();
                    dom.val('');
                }, 1000);

            } else {
                error('留言出错：' + result.code, 'commentErrorMsg', true, 3000);
                disableButton(true, 'loginBtn', '提交', "button-disable");
            }
        }
    )

}

$(document).ready(function () {
    loadComment();
});
