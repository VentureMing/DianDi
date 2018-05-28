package com.diandi.util.realm;

import com.diandi.entity.author.AuthorAccount;
import com.diandi.service.author.AuthorAccountService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shangmingyu
 * @Description: shiro realm
 * @date 2018/5/6 20:19
 */

public class DianDiAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    private AuthorAccountService accountService;

    /**
     * 权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 首先执行登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户账号
        String userEmail = token.getPrincipal().toString();
        AuthorAccount account = accountService.getAccountByEmail(userEmail);
        if (account != null) {

            //将查询到的邮箱和密码存放到 authenticationInfo用于后面的权限判断。第三个参数随便放一个就行了。
            return new SimpleAuthenticationInfo(account.getAuthorEmail(), account.getAuthorPassword(), "");

        } else {
            return null;
        }
    }
}
