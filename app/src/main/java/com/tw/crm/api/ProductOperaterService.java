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
 * Created by hizi on 2017/7/19.
 */

public class ProductOperaterService extends AsyncTask<ProductEntity, Void, Integer> {
    private final String TAG = "PackingService";
    private ProductStatusListener productStatusListener;

    public ProductOperaterService(ProductStatusListener productStatusListener) {
        this.productStatusListener = productStatusListener;
    }

    @Override
    protected Integer doInBackground(ProductEntity... params) {
        String result_json = "";
        Integer result_status = null;
        try {
            if (ApiRole.MENU_PRODUCT_STORAGE.equals(params[0].getOperation())) {        //入库
                result_json = OkHttpTool.post(ApiRole.API_STORAGE, new FormBody.Builder().
                        add("userid", params[0].getUserid()).
                        add("rfidtid", params[0].getIfid()).
                        add("name", params[0].getName()).
                        add("batch", params[0].getBatch()).
                        add("color", params[0].getColor()).
                        add("specifications", params[0].getSpecifications())
                        .build());
                Log.d(TAG, "result_json: " + result_json);
            }else if (ApiRole.MENU_PACKING.equals(params[0].getOperation())) {        //装箱
                result_json = OkHttpTool.post(ApiRole.API_PACKING, new FormBody.Builder().
                        add("userid", params[0].getUserid()).
                        add("rfidtid", params[0].getIfid()).
                        add("sboxids", params[0].getSboxids())
                        .build());
                Log.d(TAG, "result_json: " + result_json);
            } else if (ApiRole.MENU_SEND_AGENT.equals(params[0].getOperation())) {     //发箱
                result_json = OkHttpTool.post(ApiRole.API_SENDAGENT, new FormBody.Builder().
                        add("userid", params[0].getUserid()).
                        add("sboxids", params[0].getSboxids()).
                        add("userid_p", params[0].getUserid_p())
                        .build());
            } else if (ApiRole.MENU_RECEIPC_BOXS.equals(params[0].getOperation())) {    //收箱
                result_json = OkHttpTool.post(ApiRole.API_RECEIPTCBOXS, new FormBody.Builder().
                        add("userid", params[0].getUserid()).
                        add("sboxids", params[0].getSboxids())
                        .build());
            }else if (ApiRole.MENU_SEND_DISTRIBUTOR.equals(params[0].getOperation())) {    //收箱
                result_json = OkHttpTool.post(ApiRole.API_SENDDISTRIBUTOR, new FormBody.Builder().
                        add("userid", params[0].getUserid()).
                        add("rfidtid", params[0].getIfid()).
                        add("userid_j", params[0].getUserid_j())
                        .build());
            }else if (ApiRole.MENU_RECEIPTC_PRODUCTS.equals(params[0].getOperation())) {    //收货
                result_json = OkHttpTool.post(ApiRole.API_RECEIPTCPRODUCTS, new FormBody.Builder().
                        add("userid", params[0].getUserid()).
                        add("rfidtid", params[0].getIfid())
                        .build());
            }

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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
