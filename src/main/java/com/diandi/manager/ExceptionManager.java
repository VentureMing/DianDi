package com.diandi.manager;


import com.diandi.exception.BaseRuntimeException;
import com.diandi.exception.api.article.*;
import com.diandi.exception.api.author.*;
import com.diandi.exception.api.common.*;
import com.diandi.exception.api.parameter.ParameterFormatIllegalException;
import com.diandi.exception.api.parameter.ParameterIllegalException;
import com.diandi.exception.api.parameter.ParameterStringSplitException;
import com.diandi.exception.api.parameter.ParameterTypeMismatchException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContext;

/**
 * Created on 2017/12/26.
 * 将异常与说明关联，同时实现国际化
 *
 * @author DuanJiaNing
 */
@Component
public class ExceptionManager {

    public BaseRuntimeException getEmptyResultException(RequestContext context) {
        return new EmptyResultException(context.getMessage("common.emptyResult"));
    }

    public BaseRuntimeException getUnknownArticleException(RequestContext context) {
        return new UnknownArticleException(context.getMessage("article.unknownArticle"));
    }

    public BaseRuntimeException getUnknownAuthorException(RequestContext context) {
        return new UnknownAuthorException(context.getMessage("author.unknownAuthor"));
    }

    public BaseRuntimeException getParameterStringSplitException(RequestContext context) {
        return new ParameterStringSplitException(context.getMessage("common.parameterStringSplitIllegal"));
    }

    public BaseRuntimeException getArticleSortRuleUndefinedException(RequestContext context) {
        return new ArticleSortRuleUndefinedException(context.getMessage("author.authorSortRuleUndefined"));
    }

    public BaseRuntimeException getArticleSortOrderUndefinedException(RequestContext context) {
        return new ArticleSortOrderUndefinedException(context.getMessage("author.authorSortOrderUndefined"));
    }

    public BaseRuntimeException getParameterIllegalException(RequestContext context) {
        return new ParameterIllegalException(context.getMessage("common.parameterIllegal"));
    }

    public BaseRuntimeException getOperateFailException(RequestContext context) {
        return new OperateFailException(context.getMessage("common.operateFail"));
    }

    public BaseRuntimeException getUnspecifiedOperationException(RequestContext context) {
        return new UnspecifiedOperationException(context.getMessage("common.unspecifiedOperation"));
    }

    public BaseRuntimeException getUnknownException(RequestContext context, Throwable e) {
        return new UnknownException(context.getMessage("common.UnknownError"), e);
    }

    public BaseRuntimeException getMissingRequestParameterException(RequestContext context, Throwable e) {
        return new MissingRequestParameterException(context.getMessage("common.missingRequestParameter"), e);
    }

    public BaseRuntimeException getUnknownImageException(RequestContext context) {
        return new UnknownImageException(context.getMessage("common.unknownImage"));
    }

    public BaseRuntimeException getUnknownLinkException(RequestContext context) {
        return new UnknownLinkException(context.getMessage("common.unknownLink"));
    }

    public BaseRuntimeException getOperateFailException(RequestContext context, Throwable e) {
        return new OperateFailException(context.getMessage("common.operateFail"), e);
    }

    public BaseRuntimeException getUnauthorizedException(RequestContext context) {
        return new UnauthorizedException(context.getMessage("common.unauthorized"));
    }

    public BaseRuntimeException getUnknownCategoryException(RequestContext context) {
        return new UnknownArticleCategoryException(context.getMessage("common.unknownCategory"));
    }

    public BaseRuntimeException getAuthorNotLoggedInException(RequestContext context) {
        return new AuthorNotLoggedInException(context.getMessage("author.notLoggedIn"));
    }

    public BaseRuntimeException getArticleIllegalException(RequestContext context) {
        return new ArticleContentIllegalException(context.getMessage("author.illegal"));
    }
    //重复数据
    public BaseRuntimeException getDuplicationDataException(RequestContext context) {
        return new DuplicationDataException(context.getMessage("common.duplicationData"));
    }

    public BaseRuntimeException getParameterFormatIllegalException(RequestContext context) {
        return new ParameterFormatIllegalException(context.getMessage("common.parameterFormatIllegal"));
    }

    public BaseRuntimeException getParameterTypeMismatchException(RequestContext context, Throwable e) {
        return new ParameterTypeMismatchException(context.getMessage("common.parameterTypeMismatch"), e);
    }

    public BaseRuntimeException getLoginFailException(RequestContext context, boolean passwordIncorrect) {
        return new LoginFailException(context.getMessage(passwordIncorrect ? "author.passwordIncorrect" : "author.unknownAccount"));
    }

    public BaseRuntimeException getImageFormatErrorException(RequestContext context) {
        return new ImageFormatErrorException(context.getMessage("common.imageFormatError"));
    }
}
