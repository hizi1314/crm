package com.tw.crm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tw.crm.activity.MenuActivity;
import com.tw.crm.api.LoginService;
import com.tw.crm.entity.UserEntity;
import com.tw.crm.listener.LoginStatusListener;
import com.tw.crm.utils.MessageConstant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoginStatusListener {

    @BindView(R.id.login_btn_submit)
    Button submitButton;

    @BindView(R.id.login_btn_close)
    Button closeButton;

    @BindView(R.id.text_login_username)
    EditText text_login_username;

    @BindView(R.id.text_login_password)
    EditText text_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    /**
     * 执行登录逻辑
     */
    private void doLogin() {
        if (TextUtils.isEmpty(text_login_username.getText())) {
            text_login_username.setError(getString(R.string.login_username_empty_message));
            return;
        }
        if (TextUtils.isEmpty(text_login_password.getText())) {
            text_login_password.setError(getString(R.string.login_password_empty_message));
            return;
        }
        //临时写法
        Toast.makeText(this, "正在登录请稍后...", Toast.LENGTH_LONG).show();
        new LoginService(this).execute(new UserEntity(text_login_username.getText().toString(), text_login_password.getText().toString(), "-1", -1));
    }


    @Override
    public void loginSuccess(UserEntity userEntity) {
        Intent intent = new Intent();
        intent.setClass(this, MenuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("roleId", userEntity.getRole_id());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void loginFail(int error_code) {

        switch (error_code) {
            case MessageConstant.NET_WORK_ERROR:
                Toast.makeText(this, "网络异常", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.USER_LOGIN_STATUS_FAIL:
                Toast.makeText(this, "用户名或者密码错误，登录失败...", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.USER_LOGIN_JSON_FORMAT_ERROR:
                Toast.makeText(this, "服务器回传数据异常...", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "未知异常", Toast.LENGTH_LONG).show();
        }
    }
}
