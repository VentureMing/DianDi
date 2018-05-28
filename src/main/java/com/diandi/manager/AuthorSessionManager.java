package com.diandi.manager;


import com.diandi.manager.properties.AuthorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created on 2018/3/13.
 *
 * @author DuanJiaNing
 */
@Component
public class AuthorSessionManager {

    @Autowired
    private AuthorProperties authorProperties;

    /**
     * 获得登录博主id，未登录返回-1
     *
     * @param request
     * @return
     */
    public int getLoginAuthorId(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object obj = session.getAttribute(authorProperties.getSessionNameOfAuthorId());

        return obj == null ? -1 : (Integer) obj;
    }

    /**
     * 获得登录博主用户名，未登录返回null
     *
     * @param request
     * @return
     */
    public String getLoginAuthorName(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Object obj = session.getAttribute(authorProperties.getSessionNameOfAuthorNickName());

        return obj == null ? null : obj.toString();
    }


}
