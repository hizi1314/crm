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
import okhttp3.Response;

/**
 * Created by hizi on 2017/7/18.
 */

public class SendAgentActivity extends AppCompatActivity  implements ProductStatusListener {
    private final String TAG="SendAgentActivity";
    @BindView(R.id.sendagent_sboxes)
    TextView text_sendagent_sboxes;
    @BindView(R.id.sendagent_userid)
    Spinner spinner_sendagent_userid;
    @BindView(R.id.sendagent_button_submit)
    Button button_sendagent_submit ;
    @BindView(R.id.sendagent_button_back)
    Button button_sendagent_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendagent_layout);
        ButterKnife.bind(this);
        initSpinner();
        button_sendagent_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendAgentActivity.this.finish();
            }
        });
        button_sendagent_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSendAgent();
            }
        });
    }

    public void doSendAgent(){
        if(TextUtils.isEmpty(text_sendagent_sboxes.getText())){
            text_sendagent_sboxes.setError("请扫描箱子条码");
            return ;
        }
        //怎么判断下拉款是否为空  问得志     这个地方先写死
        String userid_p = "0110000000000";
        //if(TextUtils.isEmpty(spinner_sendagent_userid.))
        Log.d(TAG, "doSendAgent: "+text_sendagent_sboxes.getText());
        Log.d(TAG, "doSendAgent: "+spinner_sendagent_userid);
        new ProductOperaterService(this).execute(new ProductEntity(null,text_sendagent_sboxes.getText().toString(),getIntent().getStringExtra("userid"),userid_p,null,null,ApiRole.MENU_SEND_AGENT));
    }


    public void initSpinner() {
        OkHttpTool.sendOkHttpRequest(ApiRole.API_SELECTAGENT, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<UserEntity> list = paresJsonWithJsonObject(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(list!=null){
                           // ArrayAdapter<UserEntity> adapter = new ArrayAdapter<UserEntity>(SendAgentActivity.this,android.R.layout.simple_list_item, list);
                            ArrayAdapter<UserEntity> adapter = new ArrayAdapter<UserEntity>(SendAgentActivity.this,android.R.layout.simple_spinner_item, list);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_sendagent_userid.setAdapter(adapter);
                        }else {
                            Toast.makeText(SendAgentActivity.this, "初始化省代理失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SendAgentActivity.this, "初始化省代理失败", Toast.LENGTH_SHORT).show();
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
        Log.d(TAG, "SendAgentActivity: status:"+status);
        Toast.makeText(this, "发箱省代理成功...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void serverFail(Integer status) {
        switch (status) {
            case MessageConstant.NET_WORK_ERROR:
                Toast.makeText(this, "网络异常", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_STATUS_FAIL:
                Toast.makeText(this, "发箱省代理失败...", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_JSON_FORMAT_ERROR:
                Toast.makeText(this, "服务器回传数据异常...", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "未知异常", Toast.LENGTH_LONG).show();
        }
    }




}
