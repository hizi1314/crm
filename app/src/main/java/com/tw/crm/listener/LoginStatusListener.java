package com.tw.crm.listener;

import com.tw.crm.entity.UserEntity;

/**
 * Created by yindezhi on 17/7/15.
 */

public interface LoginStatusListener {

    void loginSuccess(UserEntity userEntity);

    void loginFail(int error_code);
}
