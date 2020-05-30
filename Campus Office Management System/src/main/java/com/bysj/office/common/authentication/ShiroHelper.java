package com.bysj.office.common.authentication;

import com.bysj.office.common.annotation.Helper;
import org.apache.shiro.authz.AuthorizationInfo;


@Helper
public class ShiroHelper extends ShiroRealm {


    public AuthorizationInfo getCurrentuserAuthorizationInfo() {
        return super.doGetAuthorizationInfo(null);
    }
}
