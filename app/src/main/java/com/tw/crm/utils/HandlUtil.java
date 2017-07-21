package com.tw.crm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.handheld.uhfr.UHFRManager;
import com.uhf.api.cls.Reader;

/**
 * Created by hizi on 2017/7/21.
 */

public class HandlUtil {
    private final String TAG = "HandlUtil";
    UHFRManager mUhfrManager;
    private SharedPreferences mSharedPreferences;
    private Context  context;
    public HandlUtil(Context  context ,UHFRManager mUhfrManager, SharedPreferences mSharedPreferences){
        this.context=context ;
        this.mUhfrManager=mUhfrManager;
        this.mSharedPreferences=mSharedPreferences;
    }

    public HandlUtil(UHFRManager mUhfrManager){
        this.mUhfrManager=mUhfrManager;
    }

    public void onResume(){
        mUhfrManager = UHFRManager.getIntance();// Init Uhf module
        if (mUhfrManager != null) {
            mUhfrManager.setPower(mSharedPreferences.getInt("readPower", 30), mSharedPreferences.getInt("writePower", 30));//set uhf module power
            mUhfrManager.setRegion(Reader.Region_Conf.valueOf(mSharedPreferences.getInt("freRegion", 1)));
            Toast.makeText(context, "FreRegion:" + Reader.Region_Conf.valueOf(mSharedPreferences.getInt("freRegion", 1)) +
                    "\n" + "Read Power:" + mSharedPreferences.getInt("readPower", 30) +
                    "\n" + "Write Power:" + mSharedPreferences.getInt("writePower", 30), Toast.LENGTH_LONG).show();
            //showToast(getString(R.string.inituhfsuccess));
            Log.d(TAG, "inituhfsuccess");
        } else {
            //showToast(getString(R.string.inituhffail));
            Log.d(TAG, "onResume: inituhffail");
        }
    }

    public  void onPause(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mUhfrManager != null) {//close uhf module
            mUhfrManager.close();
            mUhfrManager = null;
        }
    }
    public  void onDestroy() {
        // TODO Auto-generated method stub
        if (mUhfrManager != null) {//close uhf module
            mUhfrManager.close();
            mUhfrManager = null;
        }
    }
}
