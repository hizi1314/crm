package com.tw.crm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tw.crm.R;
import com.tw.crm.listener.ProductStatusListener;
import com.tw.crm.utils.MessageConstant;

import butterknife.ButterKnife;

/**
 * Created by hizi on 2017/7/19.
 */

public class SecurityActivity extends AppCompatActivity implements ProductStatusListener {
    private final String TAG = "SecurityActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.construction_layout);
        ButterKnife.bind(this);
    }

    @Override
    public void serverSuccess(Integer status) {
        //清空 扫描框，继续扫描
        Log.d(TAG, ": status:"+status);
        Toast.makeText(this, "发货经销商成功...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void serverFail(Integer status) {
        switch (status) {
            case MessageConstant.NET_WORK_ERROR:
                Toast.makeText(this, "网络异常", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_STATUS_FAIL:
                Toast.makeText(this, "发货经销商失败...", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_JSON_FORMAT_ERROR:
                Toast.makeText(this, "服务器回传数据异常...", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "未知异常", Toast.LENGTH_LONG).show();
        }
    }

}
