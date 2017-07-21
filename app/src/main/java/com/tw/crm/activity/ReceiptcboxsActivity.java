package com.tw.crm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
 * Created by hizi on 2017/7/19.
 */

public class ReceiptcboxsActivity extends AppCompatActivity implements ProductStatusListener{

    private final String TAG =  "receiptcboxsActivity";
    @BindView(R.id.receiptcboxs_sboxes)
    TextView  text_receiptcboxs_sboxes ;
    @BindView(R.id.receiptcboxs_button_submit)
    Button receiptcboxs_button_submit;
    @BindView(R.id.receiptcboxs_button_back)
    Button receiptcboxs_button_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiptcboxs_layout);
        ButterKnife.bind(this);
        receiptcboxs_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiptcboxsActivity.this.finish();
            }
        });
        receiptcboxs_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReceiptcboxs();
            }
        });
    }

    public void doReceiptcboxs(){
        if(TextUtils.isEmpty(text_receiptcboxs_sboxes.getText())){
            text_receiptcboxs_sboxes.setError("请扫描箱子条码");
            return ;
        }
        //(String ifid,String sboxids,String userid,String userid_p,String userid_j,Integer status ,String operation){
        new ProductOperaterService(this).execute(new ProductEntity(null,text_receiptcboxs_sboxes.getText().toString(),getIntent().getStringExtra("userid"),null,null,null, ApiRole.MENU_RECEIPC_BOXS));
    }

    @Override
    public void serverSuccess(Integer status) {
        //清空 扫描框，继续扫描
        Log.d(TAG, "serverSuccess: status:"+status);
        Toast.makeText(this, "收箱成功...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void serverFail(Integer status) {
        switch (status) {
            case MessageConstant.NET_WORK_ERROR:
                Toast.makeText(this, "网络异常", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_STATUS_FAIL:
                Toast.makeText(this, "收箱失败...", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_JSON_FORMAT_ERROR:
                Toast.makeText(this, "服务器回传数据异常...", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "未知异常", Toast.LENGTH_LONG).show();
        }
    }


}
