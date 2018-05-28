var maxArticleTitleLen = 40;
var maxArticleSummaryLen = 200;

function releaseArticle(editMode, funAfterReleaseArticleSuccess) {

    layer.prompt({
        title: '文章标题',
        formType: 2,
        value: oldTitle,
        area: ['300px', '36px'],
        maxlength:40,
        btn: ['下一步', '取消']
    }, function (newTitle, index) {
        if (newTitle.length > maxArticleTitleLen) {
            layer.msg('标题长度不能超过40,请重新输入');
        } else {
            layer.close(index);
            layer.prompt({
                title: '文章摘要',
                formType: 2,
                value:oldSummary,
                btn: ['发布', '取消']
            }, function (newSummary, index) {
                if (newSummary.length > maxArticleSummaryLen) {
                    layer.msg('摘要长度不能超过200,请重新输入');
                } else {
                    layer.close(index);
                    addArticle(newTitle, newSummary);

                }

            });
        }

    })
}


function addArticle(newTitle, newSummary) {
    var contentMd = $('#editormd').val();
    var contentHTML = $('#editorHtml').val();
    var cidsArray = [];


    if (editMode === 1) {
        var data = '';


        if (!isStrEmpty(newTitle)) {
            data += 'title=' + newTitle + '&';
        }

        if (!isStrEmpty(contentMd)) {
            data += 'contentHTML=' + stringToUnicode(contentHTML) + '&' + 'contentMd=' + stringToUnicode(contentMd) + '&';
        }

        if (!isStrEmpty(newSummary)) {
            data += 'summary=' + newSummary + '&';
        }

        data = data.substr(0, data.length - 1);
        $.ajax({
            url: '/author/' + authorId + '/article/' + articleId,
            data: data,
            type: 'put',
            success: function (result) {
                if(result.code===0){
                    layer.msg('文章发布成功');
                    funAfterReleaseArticleSuccess(result.data);
                }else if(result.code ===18){
                    layer.msg('存在同名文章，请重新修改标题');
                }
            }
        });

    } else if (editMode === 2) {
        // 创作模式
        $.post(
            '/author/' + authorId + '/article',
            {
                categoryIds: '',
                title: newTitle,
                contentHTML: stringToUnicode(contentHTML),
                contentMd: stringToUnicode(contentMd),
                summary: newSummary,
                keyWords: ''
            },
            function (result) {
                if(result.code===0){
                    layer.msg('文章发布成功');
                    funAfterReleaseArticleSuccess(result.data);
                }else if(result.code ===18){
                    layer.msg('存在同名文章，请重新修改标题');
                }
            }
        );
    }

}