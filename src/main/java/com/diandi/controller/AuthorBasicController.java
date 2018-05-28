package com.diandi.controller;


import com.diandi.entity.author.AuthorBasic;
import com.diandi.service.author.AuthorBasicService;
import com.diandi.service.author.AuthorImageService;
import com.diandi.util.common.StringUtils;
import com.diandi.util.enums.AuthorImageCategoryEnum;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * Created on 2017/12/29.
 * 博主个人资料api
 * <p>
 * 1 获取资料
 * 2 新增资料
 * 3 更新资料
 * 4 删除资料
 *
 * @author DuanJiaNing
 */
@RestController
@RequestMapping("/author/{authorId}/basicInfo")
public class AuthorBasicController extends BaseAuthorController {

    @Autowired
    private AuthorBasicService authorBasicService;

    @Autowired
    private AuthorImageService authorImageService;

    /**
     * 获取资料
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<AuthorBasic> get(HttpServletRequest request,
                                       @PathVariable Integer authorId) {
        handleAccountCheck(request, authorId);

        AuthorBasic authorBasic = authorBasicService.getAuthorBasic(authorId);
        if (authorBasic == null) handlerEmptyResult(request);

        return new ResultBean<>(authorBasic);
    }

    /**
     * 新增资料
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean add(HttpServletRequest request,
                          @PathVariable Integer authorId,
                          @RequestParam(value = "profileId", required = false ,defaultValue = "1") Integer profileId,
                          @RequestParam(value = "phone", required = false) String phone,
                          @RequestParam(value = "nickName", required = false) String nickName,
                          @RequestParam(value = "intro", required = false,defaultValue = "") String intro) {
        handleAuthorSignInCheck(request, authorId);
        handleImageExistCheck(request, profileId);
//        handleParamsCheck(phone, nickName, request);
        int basicId = authorBasicService.insertAuthorBasic(authorId,  profileId, phone, nickName,intro);
        if (basicId <= 0) handlerOperateFail(request);
        return new ResultBean<>(basicId);
    }

    /**
     * 更新资料
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean update(HttpServletRequest request,
                             @PathVariable Integer authorId,
                             @RequestParam(value = "profileId", required = false) Integer profileId,
                             @RequestParam(value = "phone", required = false) String phone,
                             @RequestParam(value = "nickName", required = false) String nickName,
                             @RequestParam(value = "intro", required = false) String intro) {

        if (phone == null && nickName == null && nickName == null && intro == null) {
            throw exceptionManager.getParameterIllegalException(new RequestContext(request));
        }

        handleAuthorSignInCheck(request, authorId);
        handleImageExistCheck(request,  profileId);

        handleParamsCheck(phone, nickName, request);
        int headImage = profileId == null || profileId <= 0 ? -1 : profileId;
        boolean result = authorBasicService.updateAuthorBasic(authorId, headImage, phone, nickName,  intro);
        if (!result) handlerOperateFail(request);

        return new ResultBean<>("");
    }


    /**
     * 删除资料
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResultBean delete(HttpServletRequest request,
                             @PathVariable Integer authorId) {
        handleAuthorSignInCheck(request, authorId);

        boolean result = authorBasicService.deleteAuthorBasic(authorId);
        if (!result) handlerOperateFail(request);

        return new ResultBean<>("");
    }

    /**
     * 更新头像---------------需要修改
     */
    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public ResultBean updateAvatar(HttpServletRequest request,
                                   @PathVariable Integer authorId,
                                   @RequestParam(value = "avatarBaseUrlData") String base64urlData) {
        handleImageBase64Check(request, base64urlData);
        handleAuthorSignInCheck(request, authorId);

        // 保存图片
        String base = base64urlData.replaceFirst("^data:image/(png|jpg);base64,", "");
        byte[] bs = Base64.getDecoder().decode(base);
        int id = authorImageService.insertImage(bs, authorId, "once-avatar-" + authorId + ".png", "", AuthorImageCategoryEnum.PUBLIC, "");
        if (id <= 0) handlerOperateFail(request);

        boolean res = authorBasicService.updateAuthorBasic(authorId, id, null, null, null);
        if (!res) handlerOperateFail(request);

        return new ResultBean<>(id);
    }

    private void handleImageBase64Check(HttpServletRequest request, String base64urlData) {

        if (!base64urlData.contains("data:image") || !base64urlData.contains("base64")) {
            throw exceptionManager.getParameterFormatIllegalException(new RequestContext(request));
        }

    }

    private void handleParamsCheck(String phone, String nickName, HttpServletRequest request) {
        RequestContext context = new RequestContext(request);
        if (phone != null && !StringUtils.isPhone(phone))
            throw exceptionManager.getParameterFormatIllegalException(context);

//        if (nickName != null && !StringUtils.isEmail(nickName))
//            throw exceptionManager.getParameterFormatIllegalException(context);
        if (nickName != null && !StringUtils.isNickName(nickName))
            throw exceptionManager.getParameterFormatIllegalException(context);
    }

}
