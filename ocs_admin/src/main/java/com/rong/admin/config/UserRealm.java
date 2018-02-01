package com.rong.admin.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.rong.persist.model.SystemAdmin;
import com.rong.system.service.SystemAdminService;
import com.rong.system.service.SystemAdminServiceImpl;
import com.rong.system.service.SystemResourceService;
import com.rong.system.service.SystemResourceServiceImpl;

/**
 * shiro权限管理
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class UserRealm extends AuthorizingRealm {
    private SystemAdminService userService = new SystemAdminServiceImpl();
    private SystemResourceService resourceService = new SystemResourceServiceImpl();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if("admin".equalsIgnoreCase(username)){//admin用户拥有最高处理权限，开发测试时使用，生产环境不保留该用户
        	authorizationInfo.setStringPermissions(resourceService.getAllKey());
        	return authorizationInfo;
        }
        Set<String> set = new HashSet<>();
        SystemAdmin user = userService.getByUserName(username);
        if(user!=null){
        	set.add(user.getRole());
        }
        authorizationInfo.setRoles(set);
        authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();

        SystemAdmin user = userService.getByUserName(username);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(), //用户名
                user.getUserPassword(), //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
