package com.diandi.controller;

import com.diandi.manager.StringConstructorManager;
import com.diandi.manager.properties.AuthorProperties;
import com.diandi.manager.properties.WebSiteProperties;
import com.diandi.util.common.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2017/12/28.
 * 该家族的大多数操作都需要博主登录
 *
 * @author DuanJiaNing
 */
public class BaseAuthorController extends BaseCheckController {

    @Autowired
    protected StringConstructorManager stringConstructorManager;

    @Autowired
    protected WebSiteProperties websiteProperties;

    @Autowired
    protected AuthorProperties authorProperties;

    /**
     * 检查所有参数是否都为null，在更新时这种情况下更新操作将取消
     *
     * @param objs 当且仅当这些参数全为null时抛出异常
     */
    protected void handleParamAllNullForUpdate(HttpServletRequest request, Object... objs) {
        if (CollectionUtils.isEmpty(objs))
            throw exceptionManager.getParameterIllegalException(new RequestContext(request));

        for (Object obj : objs) {
            if (obj != null) return;
        }

        throw exceptionManager.getParameterIllegalException(new RequestContext(request));
    }

    /**
     * 检查图片是否存在
     *
     * @param imageId 图片id
     */
    protected void handleImageExistCheck(HttpServletRequest request,  Integer imageId) {
        if (imageId != null && !authorValidateManager.checkAuthorImageExist( imageId))
            throw exceptionManager.getUnknownImageException(new RequestContext(request));
    }
}
