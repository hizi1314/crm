package com.tw.crm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tw.crm.R;
import com.tw.crm.api.ProductOperaterService;
import com.tw.crm.entity.ProductEntity;
import com.tw.crm.entity.UserEntity;
import com.tw.crm.listener.ProductStatusListener;
import com.tw.crm.utils.ApiRole;
import com.tw.crm.utils.MessageConstant;
import com.tw.crm.utils.OkHttpTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by hizi on 2017/7/19.
 */

public class SenddistributorActivity extends AppCompatActivity implements ProductStatusListener {
    private final String TAG="SenddistributorActivity";
    @BindView(R.id.senddistributor_rfid)
    TextView text_senddistributor_rfid;
    @BindView(R.id.senddistributor_useridj)
    Spinner spinner_senddistributor_useridj;
    @BindView(R.id.senddistributor_button_submit)
    Button senddistributor_button_submit ;
    @BindView(R.id.senddistributor_button_back)
    Button senddistributor_button_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senddistributor_layout);
        ButterKnife.bind(this);
        initSpinner();
        senddistributor_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SenddistributorActivity.this.finish();
            }
        });
        senddistributor_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSendDistrubutor();
            }
        });
    }

    public void doSendDistrubutor(){
        if(TextUtils.isEmpty(text_senddistributor_rfid.getText())){
            text_senddistributor_rfid.setError("请扫描产品条码");
            return ;
        }
        //怎么判断下拉款是否为空  问得志     这个地方先写死
        String userid_j = "0220010000000";
        //if(TextUtils.isEmpty(spinner_sendagent_userid.))
        Log.d(TAG, "doSendDistrubutor: "+text_senddistributor_rfid);
        //(String ifid,String sboxids,String userid,String userid_p,String userid_j,Integer status ,String operation){
        new ProductOperaterService(this).execute(new ProductEntity(text_senddistributor_rfid.getText().toString(),null,getIntent().getStringExtra("userid"),null,userid_j,null, ApiRole.MENU_SEND_DISTRIBUTOR));
    }

    public void initSpinner() {
        Log.d(TAG, "initSpinner: userid:"+getIntent().getStringExtra("userid"));
        OkHttpTool.sendOkHttpRequestBody(ApiRole.API_SELECTJINGXS,new FormBody.Builder().add("userid",getIntent().getStringExtra("userid")).build(),new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<UserEntity> list = paresJsonWithJsonObject(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(list!=null){
                            ArrayAdapter<UserEntity> adapter = new ArrayAdapter<UserEntity>(SenddistributorActivity.this,android.R.layout.simple_spinner_item, list);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_senddistributor_useridj.setAdapter(adapter);
                        }else {
                            Toast.makeText(SenddistributorActivity.this, "初始化经销商失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SenddistributorActivity.this, "初始化省代理失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private List<UserEntity> paresJsonWithJsonObject(String jsondata){
        List<UserEntity> list= new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsondata);
            for (int i=0 ;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String role_name=jsonObject.getString("role_name");
                String role_id=jsonObject.getString("role_id");
                String id=jsonObject.getString("id");
                String usercode=jsonObject.getString("usercode");
                String username=jsonObject.getString("username");
                UserEntity userEntity = new UserEntity(id,username);
        /*        userEntity.setRole_id(role_id);
                userEntity.setUserid(id);
                userEntity.setUsername(username);*/
                list.add(userEntity);
                Log.d(TAG, "paresJsonWithJsonObject: role_name:"+role_name);
                Log.d(TAG, "paresJsonWithJsonObject: usercode:"+usercode);
                Log.d(TAG, "paresJsonWithJsonObject: username:"+username);
                Log.d(TAG, "paresJsonWithJsonObject: id:"+id);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list ;
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
