package com.rong.persist.model;

import com.rong.persist.model.base.BaseAuth;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Auth extends BaseAuth<Auth> {
	public static final Auth dao = new Auth().dao();
	public static final String TABLE = "ocs_auth";
	public static final String TABLE_USER_AUTH = "ocs_user_auth";
}
