package com.tw.crm.api;

import android.os.AsyncTask;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.tw.crm.entity.ProductEntity;
import com.tw.crm.listener.ProductStatusListener;
import com.tw.crm.utils.ApiRole;
import com.tw.crm.utils.MessageConstant;
import com.tw.crm.utils.OkHttpTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;

/**
 * Created by hizi on 2017/7/18.
 */

public class PackingService extends AsyncTask<ProductEntity,Void,Integer>{
    private final   String TAG = "PackingService";
    private ProductStatusListener productStatusListener ;
    public PackingService(ProductStatusListener productStatusListener){
        this.productStatusListener=productStatusListener;
    }
    @Override
    protected Integer doInBackground(ProductEntity... params) {
        String result_json="";
        Integer result_status =null;
        try {
            //装箱
            if(ApiRole.MENU_PACKING.equals(params[0].getOperation())){
                result_json= OkHttpTool.post(ApiRole.API_PACKING,  new FormBody.Builder().
                        add("userid",params[0].getUserid()).
                        add("rfidtid",params[0].getIfid()).
                        add("sboxids",params[0].getSboxids())
                        .build());
                Log.d(TAG, "result_json: "+result_json);
            }else if(ApiRole.MENU_SEND_AGENT.equals(params[0].getOperation())){
                //发箱
                result_json= OkHttpTool.post(ApiRole.API_SENDAGENT,  new FormBody.Builder().
                        add("userid",params[0].getUserid()).
                        add("sboxids",params[0].getSboxids()).
                        add("userid_p",params[0].getUserid_p())
                        .build());
            }


        } catch (IOException e) {
            e.printStackTrace();
            return MessageConstant.PRODUCT_JSON_FORMAT_ERROR;
        }
        if (!TextUtils.isEmpty(result_json)) {
            JSONObject jsonObject ;
            try {
                jsonObject = new JSONObject(result_json);
                int status = jsonObject.getInt("status");
                Log.d(TAG, "doInBackground: status"+status);
                if(status==MessageConstant.PRODUCT_STATUS_SUCCESS){
                    return  MessageConstant.PRODUCT_STATUS_SUCCESS ;
                }else if(status==MessageConstant.PRODUCT_STATUS_FAIL){
                    return MessageConstant.PRODUCT_STATUS_FAIL ;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return MessageConstant.PRODUCT_JSON_FORMAT_ERROR;
            }
        }
        return MessageConstant.PRODUCT_STATUS_FAIL;
    }

    @Override
    protected void onPostExecute(Integer status) {
        super.onPostExecute(status);
        if(status!=null){
            if(MessageConstant.PRODUCT_STATUS_SUCCESS==status){
                productStatusListener.serverSuccess(status);
            }else{
                productStatusListener.serverFail(status);
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
