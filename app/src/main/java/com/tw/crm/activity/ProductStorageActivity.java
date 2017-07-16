package com.tw.crm.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.BRMicro.Tools;
import com.handheld.uhfr.UHFRManager;
import com.tw.crm.R;
import com.tw.crm.utils.Util;
import com.uhf.api.cls.Reader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yindezhi on 17/7/16.
 */

public class ProductStorageActivity extends AppCompatActivity {
    private final String TAG = "ProductStorageActivity";

    @BindView(R.id.btn_product_submit)
    Button btn_product_submit;

    UHFRManager mUhfrManager;

    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_storage_layout);

        ButterKnife.bind(this);

        Util.initSoundPool(this);//Init sound pool
        mSharedPreferences = getSharedPreferences("UHF", MODE_PRIVATE);

        btn_product_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.play(1, 0);
//                List<Reader.TAGINFO> list1;
//                list1 = mUhfrManager.tagEpcTidInventoryByTimer((short) 300);
//                //Log.d(TAG, "onClick: list.size:" + list1);
//                if (list1 != null && list1.size() > 0) {
//                    int i = 0;
//                    for (Reader.TAGINFO tfs : list1) {
////                        Log.d(TAG, "onClick: tfs:"+tfs.toString());
//                        byte[] tids = tfs.EmbededData;
//                        Log.d(TAG, "tid"+i +">>:"+Tools.Bytes2HexString(tids, tids.length));
//                        i++;
//                    }
//                }else {
//                    Log.d(TAG, "onClick: ----空");
//                }

                new Thread(new Task()).start();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUhfrManager = UHFRManager.getIntance();// Init Uhf module
        if (mUhfrManager != null) {
            mUhfrManager.setFastMode();
            mUhfrManager.asyncStartReading();
            mUhfrManager.setPower(mSharedPreferences.getInt("readPower", 30), mSharedPreferences.getInt("writePower", 30));//set uhf module power
            mUhfrManager.setRegion(Reader.Region_Conf.valueOf(mSharedPreferences.getInt("freRegion", 1)));
            Toast.makeText(getApplicationContext(), "FreRegion:" + Reader.Region_Conf.valueOf(mSharedPreferences.getInt("freRegion", 1)) +
                    "\n" + "Read Power:" + mSharedPreferences.getInt("readPower", 30) +
                    "\n" + "Write Power:" + mSharedPreferences.getInt("writePower", 30), Toast.LENGTH_LONG).show();
            //showToast(getString(R.string.inituhfsuccess));
            Log.d(TAG, "inituhfsuccess");
        } else {
            //showToast(getString(R.string.inituhffail));
            Log.d(TAG, "onResume: inituhffail");
        }
    }

    // when
    @Override
    protected void onPause() {
        super.onPause();
//        Log.e("main","pause");
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

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        Log.e("main","destroy");
        if (mUhfrManager != null) {//close uhf module
            mUhfrManager.close();
            mUhfrManager = null;
        }
    }

    private class Task implements Runnable{
        @Override
        public void run() {
            List<Reader.TAGINFO> list1;
            list1 = mUhfrManager.tagInventoryRealTime();
            Log.d(TAG, "onClick: list.size:" + list1);
            if (list1 != null && list1.size() > 0) {

                for (Reader.TAGINFO tfs : list1) {

                    byte[] epcdata = tfs.EpcId;

                    String epc = Tools.Bytes2HexString(epcdata, epcdata.length);

                    int rssi = tfs.RSSI;

                    Log.d(TAG, "onClick: RSSI:" + rssi);

                    Log.d(TAG, "onClick: EPC:" + epc);
                }
            }else{
                Log.d(TAG, "onClick: 空");
            }

            for(int i = 0;i<100;i++){

                Util.play(1,0);

                //EPC 值获取到了
                byte[] epcBytes = Tools.HexString2Bytes("300833B2DDD9014000000000");

                byte[] accessBytes = Tools.HexString2Bytes("00000000");

                byte[] readBytes = new byte[1 * 2];

                readBytes = mUhfrManager.getTagDataByFilter(2, 0, 1, accessBytes, (short) 1000, epcBytes, 2, 1, true);

                //readBytes 为空
                Log.d(TAG, "onClick: readBytes:" + readBytes);
            }

        }
    }

}
