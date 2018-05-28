$(function () {
    $('.content').flexText();
});
function keyUP(t) {
    var len = $(t).val().length;
    if (len > 139) {
        $(t).val($(t).val().substring(0, 140));
    }
}
$('.commentAll').on('click', '.plBtn', function () {
    var myDate = new Date();
    //获取当前年
    var year = myDate.getFullYear();
    //获取当前月
    var month = myDate.getMonth() + 1;
    //获取当前日
    var date = myDate.getDate();
    var h = myDate.getHours();       //获取当前小时数(0-23)
    var m = myDate.getMinutes();     //获取当前分钟数(0-59)
    if (m < 10) m = '0' + m;
    var s = myDate.getSeconds();
    if (s < 10) s = '0' + s;
    var now = year + '-' + month + "-" + date + " " + h + ':' + m + ":" + s;
    //获取输入内容
    var oSize = $(this).siblings('.flex-text-wrap').find('.comment-input').val();
    console.log(oSize);
    //动态创建评论模块
    oHtml = '<div class="comment-show-con">\n' +
        '            <div class=" pl-info layui-row ">\n' +
        '\n' +
        '                <div class="layui-col-md1">\n' +
        '                    <img class="comment-show-con-img" src="images/header-img-comment_03.png">\n' +
        '                </div>\n' +
        '                <div class="layui-col-md6" style="margin-left: 8px">\n' +
        '                    <div class="comment-size-name">张三</div>\n' +
        '                    <div class="comment-size-time">'+now+'</div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="comment-content" style=" margin: 8px 0 10px 0;font-size: 16px;">\n'
        + oSize +
        '            </div>\n' +
        '            <div class="layui-row date-dz">\n' +
        '                <div class="layui-col-md2" style="width: auto">\n' +
        '                    <i class="layui-icon" style="margin-right:8px">&#xe6c6</i><span>588人赞</span>\n' +
        '                </div>\n' +
        '                <div class="layui-col-md2 date-dz-hf">\n' +
        '                    <i class="layui-icon" style="margin:0 8px 0 14px">&#xe66c</i> <span\n' +
        '                        class=" pl-hf hf-con-inline">回复</span>\n' +
        '                </div>\n' +
        '                <div class="layui-col-md-offset11 removeInline">\n' +
        '                    删除\n' +
        '                </div>\n' +
        '            </div>\n' +
        '\n' +
        '            <div class="hf-list-con">\n' +
        '\n' +
        '            </div>\n' +
        '\n' +
        '        </div>';

    if (oSize.replace(/(^\s*)|(\s*$)/g, "") != '') {
        $(this).parents('.reviewArea ').siblings('.comment-show').prepend(oHtml);
        $(this).siblings('.flex-text-wrap').find('.comment-input').prop('value', '').siblings('pre').find('span').text('');
    }
});

$('.comment-show').on('click', '.pl-hf', function () {
    //获取回复人的名字
    var fhName = $(this).parents('.date-dz-hf').parents('.date-dz').siblings('.pl-info').find('.comment-size-name').html();
    //回复@
    var fhN = '@' + fhName;
    //var oInput = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.hf-con');
    var fhHtml = '<div class="hf-con layui-col-md-offset1 layui-col-md11"> <textarea class="content comment-input hf-input" placeholder="" onkeyup="keyUP(this)"></textarea> <button href="javascript:;" class="hf-pl">评论</button></div>';


    // //显示回复
    if ($(this).is('.hf-con-inline')) {
        $(this).parents('.date-dz-hf').parents('.date-dz').append(fhHtml);
        $(this).removeClass('hf-con-inline');
        $('.content').flexText();
        // $(this).parents('.date-dz-hf').siblings('.hf-con').find('.pre').css('padding', '6px 15px');
        //console.log($(this).parents('.date-dz-right').siblings('.hf-con').find('.pre'))
        //input框自动聚焦
        $(this).parents('.date-dz-hf').siblings('.hf-con').find('.hf-input').val('').focus().val(fhN);
    } else {
        $(this).addClass('hf-con-inline');
        $(this).parents('.date-dz-hf').siblings('.hf-con').remove();
    }
});
$('.comment-show').on('click', '.hf-pl', function () {
    var oThis = $(this);
    var myDate = new Date();
    //获取当前年
    var year = myDate.getFullYear();
    //获取当前月
    var month = myDate.getMonth() + 1;
    //获取当前日
    var date = myDate.getDate();
    var h = myDate.getHours();       //获取当前小时数(0-23)
    var m = myDate.getMinutes();     //获取当前分钟数(0-59)
    if (m < 10) m = '0' + m;
    var s = myDate.getSeconds();
    if (s < 10) s = '0' + s;
    var now = year + '-' + month + "-" + date + " " + h + ':' + m + ":" + s;
    //获取输入内容
    var oHfVal = $(this).siblings('.flex-text-wrap').find('.hf-input').val();
    console.log(oHfVal)
    var oHfName = $(this).parents('.hf-con').parents('.date-dz').siblings('.pl-info').find('.comment-size-name').html();
    var oAllVal = '@' + oHfName;
    oHfVal  =oHfVal.substring(oAllVal.length);
    if (oHfVal.replace(/^ +| +$/g, '') == '' ) {


    } else {
        var oHtml = ' <div class="hf-list-con-item">\n' +
            '                    <div class="pl-info" style="height: auto;">\n' +
            '                        <span class="comment-size-name" >我的名字</span><span style="padding-right: 8px">：'+oAllVal +'</span>'+oHfVal+'\n' +
            '                    </div>\n' +
            '                    <div class="layui-row  date-dz hf-list-data-dz">\n' +
            '                        <div class="layui-col-md2 hf-list-time" style="width: auto">\n'
            + now +
            '                        </div>\n' +
            '                        <div class="layui-col-md2 date-dz-hf hf-list-date-dz-hf">\n' +
            '                            <i class="layui-icon" style="margin:0 2px 0 14px">&#xe66c</i> <a\n' +
            '                                class=" pl-hf hf-con-inline">回复</a>\n' +
            '                        </div>\n' +
            '                        <div class="layui-col-md-offset11 removeInline">\n' +
            '                            删除\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>';
        oThis.parents('.hf-con').parents('.comment-show-con').find('.hf-list-con').css('display', 'block').prepend(oHtml) && oThis.parents('.hf-con').siblings('.date-dz').find('.pl-hf').addClass('hf-con-inline') && oThis.parents('.hf-con').remove();

    }
});

$('.commentAll').on('click', '.removeInline', function () {
    var oT = $(this).parents('.date-dz').parents('.hf-list-con-item');
    if (oT.siblings('.hf-list-con-item').length >= 1) {
        oT.remove();
    } else {
        $(this).parents('.date-dz').parents('.hf-list-con-item').parents('.hf-list-con').css('display', 'none')
        oT.remove();
    }
    $(this).parents('.date-dz').parents('.comment-show-con').remove();

})
//点赞
$('.comment-show').on('click', '.date-dz-z', function () {
    var zNum = $(this).find('.z-num').html();
    if ($(this).is('.date-dz-z-click')) {
        zNum--;
        $(this).removeClass('date-dz-z-click red');
        $(this).find('.z-num').html(zNum);
        $(this).find('.date-dz-z-click-red').removeClass('red');
    } else {
        zNum++;
        $(this).addClass('date-dz-z-click');
        $(this).find('.z-num').html(zNum);
        $(this).find('.date-dz-z-click-red').addClass('red');
    }
})