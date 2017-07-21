package com.tw.crm.listener;

import com.tw.crm.entity.UserEntity;

/**
 * Created by yindezhi on 17/7/15.
 */

public interface ProductStatusListener {

    void serverSuccess(Integer status);

    void serverFail(Integer status);
}
