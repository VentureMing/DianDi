package com.diandi.controller;


import com.diandi.entity.author.AuthorAccount;
import com.diandi.manager.MessageManager;
import com.diandi.service.author.AuthorAccountService;
import com.diandi.util.common.StringUtils;
import com.diandi.util.restful.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 2018/1/11.
 * 博主登录
 * <p>
 * 1 邮箱登录
 * 2 电话号码登录
 *
 * @author DuanJiaNing
 */
@RestController
@RequestMapping("/author/login")
public class AuthorLoginController extends BaseAuthorController {

    @Autowired
    private AuthorAccountService accountService;

    @Autowired
    private MessageManager messageManager;

    @RequestMapping(value =  "/way=email", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean loginWithEmail(HttpServletRequest request,
                                       @RequestParam("email") String email,
                                       @RequestParam("password") String password) throws NoSuchAlgorithmException {
        // update 使用shiro

        AuthorAccount account = accountService.getAccountByEmail(email);

        // 用户不存在
        if (account == null) {
            throw exceptionManager.getUnknownAuthorException(new RequestContext(request));
        }


        // 密码错误
        if (!account.getAuthorPassword().equals(new BigInteger(StringUtils.toSha(password)).toString())) {
            throw exceptionManager.getLoginFailException(new RequestContext(request), true);
        }
        HttpSession session = request.getSession();
        session.setAttribute(authorProperties.getSessionNameOfAuthorId(), account.getAuthorId());
//        session.setAttribute(authorProperties.getSessionNameOfAuthorNickName(), account.getAuthorEmail());
        session.setAttribute(authorProperties.getSessionAuthorLoginSignal(), "login");


        // 成功登录
        return new ResultBean<>(account);
    }

    @RequestMapping(value = "/way=phone", method = RequestMethod.POST)
    public ResultBean loginWithPhoneNumber(HttpServletRequest request,
                                           @RequestParam("phone") String phone) {

        handlePhoneCheck(phone, request);

        AuthorAccount account = accountService.getAccountByPhone(phone);
        if (account == null) return new ResultBean<>("", ResultBean.FAIL);

        HttpSession session = request.getSession();
        session.setAttribute(authorProperties.getSessionNameOfAuthorId(), account.getAuthorId());
        session.setAttribute(authorProperties.getSessionNameOfAuthorNickName(), account.getAuthorEmail());
        session.setAttribute(authorProperties.getSessionAuthorLoginSignal(), "login");

        // 成功登录
        return new ResultBean<>(account.getAuthorEmail());
    }

    private void handlePhoneCheck(String phone, HttpServletRequest request) {
        RequestContext context = new RequestContext(request);
        if (phone != null && !StringUtils.isPhone(phone))
            throw exceptionManager.getParameterFormatIllegalException(context);

    }
}
