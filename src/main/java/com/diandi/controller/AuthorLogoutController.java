package com.diandi.controller;

import com.diandi.util.restful.ResultBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/author/{authorId}/logout")
public class AuthorLogoutController extends BaseCheckController {


    @RequestMapping(method = RequestMethod.POST)
    public ResultBean logout(HttpServletRequest request,
                             @PathVariable Integer authorId) {
        handleAuthorSignInCheck(request, authorId);
        request.getSession().invalidate();

        return new ResultBean<>("");
    }

}
