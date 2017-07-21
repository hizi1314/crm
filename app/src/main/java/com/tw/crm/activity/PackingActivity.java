package com.tw.crm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tw.crm.R;


import com.tw.crm.api.ProductOperaterService;
import com.tw.crm.entity.ProductEntity;
import com.tw.crm.listener.ProductStatusListener;
import com.tw.crm.utils.ApiRole;
import com.tw.crm.utils.MessageConstant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hizi on 2017/7/18.
 */

public class PackingActivity extends AppCompatActivity implements ProductStatusListener {
    private final String TAG = "PackingActivity";
    @BindView(R.id.packing_ifid)
    EditText  text_packing_ifid ;
    @BindView(R.id.packing_sboxids)
    EditText text_packing_sboxids;
    @BindView(R.id.packing_button_submit)
    Button packing_button_submit ;
    @BindView(R.id.packing_button_back)
    Button packing_button_back ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packing_layout);
        ButterKnife.bind(this);
        packing_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackingActivity.this.finish();
            }
        });
        packing_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPacking();
            }
        });
    }
        public void doPacking(){
        if (TextUtils.isEmpty(text_packing_ifid.getText())){
            text_packing_ifid.setError("请扫描产品条码");
            return ;
        }
        if (TextUtils.isEmpty(text_packing_sboxids.getText())){
            text_packing_sboxids.setError("请扫描箱子条码");
            return;
        }
            Log.d(TAG, "doPacking: userid:"+getIntent().getStringExtra("userid"));

            Log.d(TAG, "doPacking: "+text_packing_sboxids+"  text_packing_ifid:"+text_packing_ifid);
            new ProductOperaterService(this).execute(new ProductEntity(String.valueOf(text_packing_ifid.getText()),String.valueOf(text_packing_sboxids.getText()),getIntent().getStringExtra("userid"), ApiRole.MENU_PACKING));
    }
    @Override
    public void serverSuccess(Integer status) {
            //清空 扫描框，继续扫描
        Log.d(TAG, "productSuccess: status:"+status);
        Toast.makeText(this, "装箱成功...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void serverFail(Integer status) {
        switch (status) {
            case MessageConstant.NET_WORK_ERROR:
                Toast.makeText(this, "网络异常", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_STATUS_FAIL:
                Toast.makeText(this, "装箱失败...", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_JSON_FORMAT_ERROR:
                Toast.makeText(this, "服务器回传数据异常...", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "未知异常", Toast.LENGTH_LONG).show();
        }
    }
}
