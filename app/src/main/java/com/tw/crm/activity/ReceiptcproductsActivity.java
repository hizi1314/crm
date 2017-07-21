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

public class ReceiptcproductsActivity extends AppCompatActivity implements ProductStatusListener {

    private final String TAG = "ReceiptcproductsActivity";
    @BindView(R.id.receiptcproducts_rfid)
    TextView text_receiptcproducts_rfid;
    @BindView(R.id.receiptcproducts_button_submit)
    Button receiptcproducts_button_submit;
    @BindView(R.id.receiptcproducts_button_back)
    Button receiptcproducts_button_back;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiptcproducts_layout);
        ButterKnife.bind(this);
        receiptcproducts_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiptcproductsActivity.this.finish();
            }
        });
        receiptcproducts_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReceiptcproducts();
            }
        });
    }

    public void doReceiptcproducts() {
        if (TextUtils.isEmpty(text_receiptcproducts_rfid.getText())) {
            text_receiptcproducts_rfid.setError("请扫描产品条码");
            return;
        }
        //(String ifid,String sboxids,String userid,String userid_p,String userid_j,Integer status ,String operation){
        new ProductOperaterService(this).execute(new ProductEntity(text_receiptcproducts_rfid.getText().toString(),null , getIntent().getStringExtra("userid"), null, null, null, ApiRole.MENU_RECEIPTC_PRODUCTS));
    }

    @Override
    public void serverSuccess(Integer status) {
        //清空 扫描框，继续扫描
        ///Log.d(TAG, "serverSuccess: status:" + status);
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
