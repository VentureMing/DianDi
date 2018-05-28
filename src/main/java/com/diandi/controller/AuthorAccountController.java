package com.diandi.controller;


import com.diandi.entity.author.AuthorAccount;
import com.diandi.entity.author.AuthorBasic;
import com.diandi.service.author.AuthorAccountService;
import com.diandi.service.author.AuthorBasicService;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created on 2018/1/17.
 * 博主账号api
 * <p>
 * 1 注册账号
 * 2 修改用户名
 * 3 修改密码
 * 4 注销账号
 *
 * @author DuanJiaNing
 */
@RestController
@RequestMapping("/author")
public class AuthorAccountController extends BaseAuthorController {

    @Autowired
    private AuthorAccountService accountService;
    @Autowired
    private AuthorBasicService basicService;

    /**
     * 注册
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean register(HttpServletRequest request,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {
//        handleEmailCheck(request, email);
//        handlePwdCheck(request, password);

        int authorId = accountService.insertAccount(email, password);
        if (authorId < 0) handlerOperateFail(request);

        return new ResultBean<>(authorId);
    }

    /**
     * 检查昵称是否存在
     */
    @RequestMapping(value = "/check=nickName", method = RequestMethod.GET)
    public ResultBean checkNickName(HttpServletRequest request,
                                        @RequestParam("nickName") String nickName) {
//        handleEmailCheck(request, checkNickName);

        AuthorBasic authorBasic = basicService.getAuthorBasicByNickName(nickName);
        if (authorBasic != null) {
            return new ResultBean(exceptionManager.getDuplicationDataException(new RequestContext(request)));
        } else {
            return new ResultBean<>("");
        }

    }

    /**
     * 检查邮箱是否已注册
     */
    @RequestMapping(value = "/check=email", method = RequestMethod.GET)
    public ResultBean checkEmailUsed(HttpServletRequest request,
                                     @RequestParam("email") String email) {
//        handleEmailCheck(request, email);

        AuthorAccount account = accountService.getAccountByEmail(email);
        if (account != null) {
            return new ResultBean(exceptionManager.getDuplicationDataException(new RequestContext(request)));
        } else {
            return new ResultBean<>("");
        }

    }

    /**
     * 检查电话号码是否存在
     */
    @RequestMapping(value = "/check=phone", method = RequestMethod.GET)
    public ResultBean checkProfileExist(HttpServletRequest request,
                                        @RequestParam("phone") String phone) {
        AuthorAccount account = accountService.getAccountByPhone(phone);

        if (account != null) {
            return new ResultBean(exceptionManager.getDuplicationDataException(new RequestContext(request)));
        } else {
            return new ResultBean<>("");
        }

    }

    /**
     * 修改用户名(昵称)
     */
    @RequestMapping(value = "/{authorId}/item=name", method = RequestMethod.PUT)
    public ResultBean modifyUsername(HttpServletRequest request,
                                     @PathVariable Integer authorId,
                                     @RequestParam(value = "email") String newUserName) {
        handleAuthorSignInCheck(request, authorId);
//        handleEmailCheck(request, newUserName);

        boolean result = accountService.updateAccountUserName(authorId, newUserName);
        if (!result) handlerOperateFail(request);

        // 更新session信息
        HttpSession session = request.getSession();
        session.setAttribute(authorProperties.getSessionNameOfAuthorNickName(), newUserName);

        return new ResultBean<>("");
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/{authorId}/item=pwd", method = RequestMethod.PUT)
    public ResultBean modifyPassword(HttpServletRequest request,
                                     @PathVariable Integer authorId,
                                     @RequestParam(value = "old") String oldPassword,
                                     @RequestParam(value = "new") String newPassword) {
        handleAuthorSignInCheck(request, authorId);
//        handlePwdCheck(request, newPassword);

        boolean result = accountService.updateAccountPassword(authorId, oldPassword, newPassword);
        if (!result) handlerOperateFail(request);

        // session 失效，重新登录
        HttpSession session = request.getSession();
        session.invalidate();

        return new ResultBean<>("");
    }


    /**
     * 注销账号
     */
    @RequestMapping(value = "/{authorId}", method = RequestMethod.DELETE)
    public ResultBean delete(HttpServletRequest request,
                             @PathVariable Integer authorId) {
        handleAuthorSignInCheck(request, authorId);

        boolean result = accountService.deleteAccount(authorId);
        if (!result) handlerOperateFail(request);

        // session 失效
        HttpSession session = request.getSession();
        session.invalidate();

        return new ResultBean<>("");
    }


//    // 检查昵称合法性
//    private void handleNickNameCheck(HttpServletRequest request, String nickName) {
//        if (StringUtils.isEmpty_(nickName) || !authorValidateManager.checkEmail(ni))
//            throw exceptionManager.getParameterIllegalException(new RequestContext(request));
//    }
//    // 检查邮箱合法性
//    private void handleEmailCheck(HttpServletRequest request, String email) {
//        if (StringUtils.isEmpty_(email) || !authorValidateManager.checkEmail(email))
//            throw exceptionManager.getParameterIllegalException(new RequestContext(request));
//    }
//
//    // 检查密码合法性
//    private void handlePwdCheck(HttpServletRequest request, String password) {
//        if (StringUtils.isEmpty_(password) || !authorValidateManager.checkPassword(password))
//            throw exceptionManager.getParameterIllegalException(new RequestContext(request));
//    }
}
