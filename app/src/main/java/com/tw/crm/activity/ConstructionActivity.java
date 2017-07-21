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
import com.tw.crm.api.ConstructionService;
import com.tw.crm.entity.ConstructionEntity;
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
import okhttp3.Response;

/**
 * Created by hizi on 2017/7/19.
 */

public class ConstructionActivity extends AppCompatActivity implements ProductStatusListener {
    private final String TAG = "ConstructionActivity";
    @BindView(R.id.construction_rfid)
    TextView   construction_rfid;
    @BindView(R.id.construction_cartype)
    TextView   construction_cartype;
    @BindView(R.id.construction_customname)
    TextView   construction_customname;
    @BindView(R.id.construction_technumber)
    TextView   construction_technumber;
    @BindView(R.id.construction_tel)
    TextView   construction_tel;
    @BindView(R.id.construction_useridyw)
    Spinner spinner_construction_useridyw;
    @BindView(R.id.construction_carnum)
    TextView   construction_carnum;

    @BindView(R.id.construction_button_back)
    Button construction_button_back ;
    @BindView(R.id.construction_button_submit)
    Button construction_button_submit ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.construction_layout);
        ButterKnife.bind(this);
        initSpinner();
        construction_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstructionActivity.this.finish();
            }
        });
        construction_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doConstruction();
            }
        });
    }

    public void doConstruction(){
        if(TextUtils.isEmpty(construction_rfid.getText())){
            construction_rfid.setError("请扫描产品条码");
            return ;
        }
        if(TextUtils.isEmpty(construction_customname.getText())){
            construction_customname.setError("请输入客户姓名");
            return ;
        }
        if(TextUtils.isEmpty(construction_tel.getText())){
            construction_tel.setError("请输入客户手机号");
            return ;
        }
        if(TextUtils.isEmpty(construction_cartype.getText())){
            construction_cartype.setError("请输入车辆类别");
            return ;
        }
        if(TextUtils.isEmpty(construction_carnum.getText())){
            construction_carnum.setError("请输入车牌号");
            return ;
        }
        //问得志
        String userid_yw = "0220000000001";
      //  (String userid ,String rfidtid,String userid_yw,String customname,String tel,String carnum,String cartype,String technumber){
        new ConstructionService(this).execute(new ConstructionEntity(getIntent().getStringExtra("userid"),construction_rfid.getText().toString(),userid_yw,construction_customname.getText().toString(),
                construction_tel.getText().toString(),construction_carnum.getText().toString(),construction_cartype.getText().toString(),construction_technumber.getText().toString()));

    }

    public void initSpinner() {
        OkHttpTool.sendOkHttpRequest(ApiRole.API_SELECTFENXIAOYEWU, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<UserEntity> list = paresJsonWithJsonObject(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(list!=null){
                            ArrayAdapter<UserEntity> adapter = new ArrayAdapter<UserEntity>(ConstructionActivity.this,android.R.layout.simple_spinner_item, list);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_construction_useridyw.setAdapter(adapter);
                        }else {
                            Toast.makeText(ConstructionActivity.this, "初始化业务失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ConstructionActivity.this, "初始化业务失败", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "施工成功...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void serverFail(Integer status) {
        switch (status) {
            case MessageConstant.NET_WORK_ERROR:
                Toast.makeText(this, "网络异常", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_STATUS_FAIL:
                Toast.makeText(this, "施工失败...", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_JSON_FORMAT_ERROR:
                Toast.makeText(this, "服务器回传数据异常...", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "未知异常", Toast.LENGTH_LONG).show();
        }
    }

}
