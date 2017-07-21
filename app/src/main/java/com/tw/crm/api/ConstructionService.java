package com.tw.crm.api;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.tw.crm.entity.ConstructionEntity;
import com.tw.crm.listener.ProductStatusListener;
import com.tw.crm.utils.ApiRole;
import com.tw.crm.utils.MessageConstant;
import com.tw.crm.utils.OkHttpTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;

/**
 * Created by hizi on 2017/7/19.
 */

public class ConstructionService extends AsyncTask<ConstructionEntity,Void,Integer>{
    private final   String TAG = "ConstructionService";
    private ProductStatusListener productStatusListener ;
    public ConstructionService(ProductStatusListener productStatusListener){
        this.productStatusListener=productStatusListener;
    }

    @Override
    protected Integer doInBackground(ConstructionEntity... params) {
        String result_json;
        try {
             result_json= OkHttpTool.post(ApiRole.API_CONSTRUCTION,  new FormBody.Builder().
                    add("userid",params[0].getUserid()).
                    add("rfidtid",params[0].getRfidtid()).
                    add("userid_yw",params[0].getUserid_yw()).
                    add("customName",params[0].getCustomname()).
                    add("tel",params[0].getTel()).
                    add("carnum",params[0].getCarnum()).
                    add("cartype",params[0].getCartype()).
                    add("technumber",params[0].getTechnumber())
                    .build());
            Log.d(TAG, "doInBackground: "+result_json);

        } catch (IOException e) {
            e.printStackTrace();
            return MessageConstant.PRODUCT_JSON_FORMAT_ERROR;
        }
        if (!TextUtils.isEmpty(result_json)) {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(result_json);
                int status = jsonObject.getInt("status");
                Log.d(TAG, "doInBackground: status" + status);
                if (status == MessageConstant.PRODUCT_STATUS_SUCCESS) {
                    return MessageConstant.PRODUCT_STATUS_SUCCESS;
                } else if (status == MessageConstant.PRODUCT_STATUS_FAIL) {
                    return MessageConstant.PRODUCT_STATUS_FAIL;
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
        if (status != null) {
            if (MessageConstant.PRODUCT_STATUS_SUCCESS == status) {
                productStatusListener.serverSuccess(status);
            } else {
                productStatusListener.serverFail(status);
            }
        }
    }
}
