package com.tw.crm.api;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.tw.crm.entity.UserEntity;
import com.tw.crm.fragment.LoginFragment;
import com.tw.crm.utils.ApiRole;
import com.tw.crm.utils.MessageConstant;
import com.tw.crm.utils.OkHttpTool;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.FormBody;

/**
 * Created by yindezhi on 17/7/5.
 */

public class LoginService extends AsyncTask<UserEntity, Void, UserEntity> {

    private LoginFragment loginFragment;

    private final String TAG = "LoginService";

    public LoginService(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    @Override
    protected UserEntity doInBackground(UserEntity... params) {
        //{status:1,username:admin,password:11,role_id:1}
        String user_json = null;
        try {
            user_json = OkHttpTool.post(ApiRole.API_LOGIN_URL, new FormBody.Builder().
                    add("username", params[0].getUsername()).
                    add("password", params[0].getPassword())
                    .build());

            Log.d(TAG, "user_json: "+user_json);
            
        } catch (IOException e) {
            return new UserEntity(null, null, -1, MessageConstant.NET_WORK_ERROR);
        }

        if (!TextUtils.isEmpty(user_json)) {
            try {
                JSONObject jsonObject = new JSONObject(user_json);
                int status = jsonObject.getInt("userStatus");
                if (status == MessageConstant.USER_LOGIN_STATUS_FAIL) {
                    return new UserEntity(null, null, -1, MessageConstant.USER_LOGIN_STATUS_FAIL);
                } else if (status == MessageConstant.USER_LOGIN_STATUS_SUCCESS) {
                    return new UserEntity(null, null, jsonObject.getInt("roleid"), MessageConstant.USER_LOGIN_STATUS_SUCCESS);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return new UserEntity(null, null, -1, MessageConstant.USER_LOGIN_JSON_FORMAT_ERROR);
            }
        }
        return new UserEntity(null, null, -1, MessageConstant.USER_LOGIN_STATUS_FAIL);
    }

    @Override
    protected void onPostExecute(UserEntity userEntity) {
        super.onPostExecute(userEntity);
        //回调
        if (loginFragment != null) {
            loginFragment.loginCallBack(userEntity);
        }
    }
}