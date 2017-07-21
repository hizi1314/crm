package com.tw.crm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tw.crm.R;
import com.tw.crm.adapter.TrackAdapter;
import com.tw.crm.entity.TrackEntity;
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

public class TrackActivity extends AppCompatActivity implements ProductStatusListener {
    private final String TAG = "TrackActivity";

    @BindView(R.id.track_listview)
    ListView listView ;
    @BindView(R.id.track_seach_button_back)
    Button  track_seach_button_back;
    @BindView(R.id.track_seach_button_submit)
    Button track_seach_button_submit;
    @BindView(R.id.track_seach_carnum)
    TextView track_seach_carnum;
    @BindView(R.id.track_seach_customname)
    TextView track_seach_customname;
    @BindView(R.id.track_seach_rfidtid)
    TextView track_seach_rfidtid;
    @BindView(R.id.track_seach_cpstatus)
    TextView track_seach_cpstatus;

    @BindView(R.id.track_seach_userid_p)
    Spinner track_seach_userid_p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_listview);
        ButterKnife.bind(this);
        track_seach_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackActivity.this.finish();
            }
        });
        track_seach_button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTrack();
            }
        });
    }


    public void doTrack() {
        String carnum= track_seach_carnum.getText().toString();
        String rfidtid= track_seach_rfidtid.getText().toString();
        String cpstatus= track_seach_cpstatus.getText().toString();
        //    String userid_p= track_seach_userid_p.getText().toString();
        String userid = getIntent().getStringExtra("userid");
        String userid_p = "";
        String customname= track_seach_customname.getText().toString();


        OkHttpTool.sendOkHttpRequestBody(ApiRole.API_TRACK,new FormBody.Builder().
                add("ca_carnum",carnum).add("cp_rfidtid",rfidtid).add("cu_customname",customname).
                add("cp_status",cpstatus).add("cp_userid",userid).add("cp_userid_p",userid_p)
                .build(),new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d(TAG, "onResponse: responseText"+responseText);
                final List<TrackEntity> trackList =paresJsonWithJsonObject(responseText);
                Log.d(TAG, "onResponse:trackList "+trackList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(trackList!=null){
                            Log.d(TAG, "onCreate: "+trackList.size());
                            TrackAdapter adapter = new TrackAdapter(TrackActivity.this,R.layout.track_item,trackList);
                            listView.setAdapter(adapter);
                        }else {
                            Toast.makeText(TrackActivity.this, "未查到数据", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(TrackActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    private List<TrackEntity> paresJsonWithJsonObject(String jsondata){
        try {
             List<TrackEntity> trackList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsondata);
            for (int i=0 ;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.d(TAG, "paresJsonWithJsonObject: "+jsonObject);
                String pname=jsonObject.has("name")==true?jsonObject.getString("name"):null;
                String rfidtid=jsonObject.has("rfidtid")==true?jsonObject.getString("rfidtid"):null;
                String time0=jsonObject.has("time0")==true?jsonObject.getString("time0"):null;
                String time1=jsonObject.has("time1")==true?jsonObject.getString("time1"):null;
                String time2=jsonObject.has("time2")==true?jsonObject.getString("time2"):null;
                String time3=jsonObject.has("time3")==true?jsonObject.getString("time3"):null;
               TrackEntity trackEntity = new TrackEntity(pname,rfidtid,null,null,null,null);
                trackList.add(trackEntity);
            }
            return trackList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
    @Override
    public void serverSuccess(Integer status) {

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
