package com.tw.crm.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tw.crm.R;
import com.tw.crm.api.LoginService;
import com.tw.crm.entity.UserEntity;
import com.tw.crm.utils.MessageConstant;

/**
 * Created by yindezhi on 17/7/5.
 */
public class LoginFragment extends Fragment {

    private final String TAG = "LoginFragment";
    private Button submitButton;
    private Button closeButton;
    private EditText text_login_username;
    private EditText text_login_password;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);
        submitButton = (Button) rootView.findViewById(R.id.login_btn_submit);
        closeButton = (Button) rootView.findViewById(R.id.login_btn_close);
        text_login_username = (EditText) rootView.findViewById(R.id.text_login_username);
        text_login_password = (EditText) rootView.findViewById(R.id.text_login_password);

        //关闭程序
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        //点击登录按钮
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(text_login_username.getText())) {
                    text_login_username.setError(getString(R.string.login_username_empty_message));
                    return;
                }
                if (TextUtils.isEmpty(text_login_password.getText())) {
                    text_login_password.setError(getString(R.string.login_password_empty_message));
                    return;
                }
                //临时写法
                Toast.makeText(getActivity(), "正在登录请稍后...", Toast.LENGTH_LONG).show();
                new LoginService(LoginFragment.this).execute(new UserEntity(text_login_username.getText().toString(), text_login_password.getText().toString(), -1, -1));
            }
        });
        return rootView;
    }

    /**
     * 登录结果回调
     *
     * @param userEntity
     */
    public void loginCallBack(UserEntity userEntity) {
        if (null != userEntity) {
            int status = userEntity.getUserStatus();
            Log.d(TAG, "登录状态为:" + status);

            switch (status) {
                case MessageConstant.NET_WORK_ERROR:
                    Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
                    break;
                case MessageConstant.USER_LOGIN_STATUS_FAIL:
                    Toast.makeText(getActivity(), "用户名或者密码错误，登录失败...", Toast.LENGTH_LONG).show();
                    break;
                case MessageConstant.USER_LOGIN_JSON_FORMAT_ERROR:
                    Toast.makeText(getActivity(), "服务器回传数据异常...", Toast.LENGTH_LONG).show();
                    break;
                case MessageConstant.USER_LOGIN_STATUS_SUCCESS:
                    Toast.makeText(getActivity(), "登录成功，开始跳转页面", Toast.LENGTH_LONG).show();
                    //判断权限,根据权限生成菜单结构
                    int role_id = userEntity.getRole_id();
                    break;
                default:
                    Toast.makeText(getActivity(), "未知异常", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "登录错误");
        }
    }
}
