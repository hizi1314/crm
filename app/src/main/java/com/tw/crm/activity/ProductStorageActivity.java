package com.tw.crm.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.BRMicro.Tools;
import com.handheld.uhfr.UHFRManager;
import com.tw.crm.R;
import com.tw.crm.api.ProductOperaterService;
import com.tw.crm.entity.ProductEntity;
import com.tw.crm.listener.ProductStatusListener;
import com.tw.crm.utils.ApiRole;
import com.tw.crm.utils.HandlUtil;
import com.tw.crm.utils.MessageConstant;
import com.tw.crm.utils.Util;
import com.uhf.api.cls.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yindezhi on 17/7/16.
 */

public class ProductStorageActivity extends AppCompatActivity  implements ProductStatusListener,View.OnClickListener {
    private final String TAG = "ProductStorageActivity";

    @BindView(R.id.btn_product_submit)
    Button btn_product_submit;
    @BindView(R.id.btn_product_scan)
    Button btn_product_scan;
    @BindView(R.id.btn_product_back)
    Button btn_product_back;
    @BindView(R.id.product_rfid)
    TextView product_rfid ;
    @BindView(R.id.product_countrfid)
    TextView product_countrfid ;

    @BindView(R.id.product_name)
    TextView product_name;
    @BindView(R.id.product_batch)
    TextView product_batch;
    @BindView(R.id.product_color)
    TextView product_color;
    @BindView(R.id.product_specifications)
    TextView product_specifications;

    UHFRManager mUhfrManager;

    private SharedPreferences mSharedPreferences;

    private StringBuilder rfids_string = new StringBuilder();
//扫描Rfid的个数
    private int countRfid ;

    HashSet<Reader.TAGINFO> setlist  ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_storage_layout);
        ButterKnife.bind(this);
        IntentFilter filter = new IntentFilter() ;
        filter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver, filter) ;
        Util.initSoundPool(this);//Init sound pool
        mSharedPreferences = getSharedPreferences("UHF", MODE_PRIVATE);
        btn_product_back.setOnClickListener(this);
        btn_product_submit.setOnClickListener(this);
        btn_product_scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_product_scan:
                runInventory() ;
                break ;
            case R.id.btn_product_submit:
                doProductStorage();
                break ;
            case R.id.btn_product_back:
                ProductStorageActivity.this.finish();
                break ;
        }
    }

    public void doProductStorage(){
        if(TextUtils.isEmpty(product_rfid.getText())){
            product_rfid.setError("请扫描产品");
            return ;
        }
        if(TextUtils.isEmpty(product_name.getText())){
            product_name.setError("请输入产品名称");
            return ;
        }
        //                 (String userid ,String rfid ,String name,String batch ,String color,String specifications,Integer status ,String operation){
        new ProductOperaterService(this).execute(new ProductEntity(getIntent().getStringExtra("userid"),String.valueOf(product_rfid.getText()),String.valueOf(product_name.getText()) , String.valueOf(product_batch.getText()), String.valueOf(product_color.getText()),String.valueOf(product_specifications.getText()),-1, ApiRole.MENU_PRODUCT_STORAGE));
    }

    private boolean keyControl = true;
    private void runInventory() {
        if (keyControl) {
            keyControl = false;
            if (!isStart) {
                isRunning = true;
                new Thread(inventoryTask).start();
                btn_product_scan.setText(getResources().getString(R.string.product_scan_stop));
                isStart = true;
            } else {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isRunning = false;
                btn_product_scan.setText(getResources().getString(R.string.product_scan_start));
                isStart = false;
            }
            keyControl = true;
        }
    }

    private int allCount = 0 ;// inventory count
    private Set<String> tidSet = null ; //store different EPC

    StringBuffer tids_final = new StringBuffer();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    product_countrfid.setText("" +  allCount);
                    product_rfid.setText(tids_final);
                    break ;
            }
        }
    } ;


    private boolean isRunning = false ;
    private boolean isStart = false ;
    String tid;
     static  StringBuffer tidbuffer = new StringBuffer();
    //inventory epc
    private Runnable inventoryTask = new Runnable() {
        @Override
        public void run() {
            while(isRunning){
                if (isStart) {
                  //  List<Reader.TAGINFO> list1 ;
                    List<Reader.TAGINFO>  list1 = mUhfrManager.tagEpcTidInventoryByTimer((short) 300); //读所有tid
                    Log.d(TAG, "run: setlist"+setlist);
                    Util.play(1, 0);
                    if (list1 != null&&list1.size()>0) {//
                        for (Reader.TAGINFO tfs : list1) {
                            byte[] tids = tfs.EmbededData;
                            Log.d(TAG, "tid" +">>:"+Tools.Bytes2HexString(tids, tids.length));
                            tid = Tools.Bytes2HexString(tids, tids.length);
                            if(tid!=null && !"".equals(tid)){
                                if (tidSet == null) {//first add
                                    tidSet = new HashSet<String>();
                                    tidSet.add(tid);
                                    tids_final.append(tid).append("*");
                                    allCount++ ;
                                }else{
                                    if (!tidSet.contains(tid)) {//set already exit
                                        tids_final.append(tid).append("*");
                                        tidSet.add(tid);
                                        allCount++ ;
                                    }
                                }
                                Message msg = new Message() ;
                                msg.what = 1 ;
                                handler.sendMessage(msg);
                            }
                        }
                    }
                }
            }
        }
    } ;



    //key receiver
    private  long startTime = 0 ;
    private boolean keyUpFalg= true;

    private BroadcastReceiver keyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int keyCode = intent.getIntExtra("keyCode", 0) ;
            if(keyCode == 0){//H941
                keyCode = intent.getIntExtra("keycode", 0) ;
            }
//            Log.e("key ","keyCode = " + keyCode) ;
            boolean keyDown = intent.getBooleanExtra("keydown", false) ;
//			Log.e("key ", "down = " + keyDown);
            if(keyUpFalg&&keyDown && System.currentTimeMillis() - startTime > 500){
                keyUpFalg = false;
                startTime = System.currentTimeMillis() ;
                if ( (keyCode == KeyEvent.KEYCODE_F1 || keyCode == KeyEvent.KEYCODE_F2
                        || keyCode == KeyEvent.KEYCODE_F3 || keyCode == KeyEvent.KEYCODE_F4 ||
                        keyCode == KeyEvent.KEYCODE_F5)) {
//                Log.e("key ","inventory.... " ) ;
                    runInventory();
                }
                return ;
            }else if (keyDown){
                startTime = System.currentTimeMillis() ;
            }else {
                keyUpFalg = true;
            }

        }
    } ;


    @Override
    protected void onResume() {
        super.onResume();
        new HandlUtil(getApplicationContext(),mUhfrManager,mSharedPreferences).onResume();
       mUhfrManager = UHFRManager.getIntance();// Init Uhf module
        if (mUhfrManager != null) {
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
       // new HandlUtil(mUhfrManager).onPause();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mUhfrManager != null) {//close uhf module
            mUhfrManager.close();
            mUhfrManager = null;
        }
        unregisterReceiver(keyReceiver);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        Log.e("main","destroy");
     //   new HandlUtil(mUhfrManager).onDestroy();
        if (mUhfrManager != null) {//close uhf module
            mUhfrManager.close();
            mUhfrManager = null;
        }
    }

    /**
     *  这里是先读epc  ，读出epc后，循环每个epc，再去读每个epc中的tid   暂时没有用到
     */
    private class Task implements Runnable{
        @Override
        public void run() {
            List<Reader.TAGINFO> list1;
            list1 = mUhfrManager.tagInventoryRealTime();
            Log.d(TAG, "onClick: list.size:" + list1);
            Util.play(1,0);
            mUhfrManager.setFastMode();
            mUhfrManager.asyncStartReading();
            if (list1 != null && list1.size() > 0) {
                for (Reader.TAGINFO tfs : list1) {
                    mUhfrManager.setFastMode();
                    mUhfrManager.asyncStartReading();
                    byte[] epcdata = tfs.EpcId;
                    String epc = Tools.Bytes2HexString(epcdata, epcdata.length);
                    int rssi = tfs.RSSI;
                    Log.d(TAG, "onClick: RSSI:" + rssi);
                    Log.d(TAG, "onClick: EPC:" + epc);

                    mUhfrManager.setCancleFastMode();
                    mUhfrManager.asyncStopReading();
                    //EPC 值获取到了
                    byte[] epcBytes = Tools.HexString2Bytes(epc);
                    byte[] accessBytes = Tools.HexString2Bytes("00000000");
                    byte[] readBytes = new byte[2 * 2];
                    readBytes = mUhfrManager.getTagDataByFilter(2, 0, 1, accessBytes, (short) 1000, epcBytes, 1, 2, true);
                    //readBytes 为空
                    Log.d(TAG, "onClick: readBytes:" + (readBytes!=null &&readBytes.length>0?Tools.Bytes2HexString(readBytes, readBytes.length):"空"));
                    //Log.d(TAG, "onClick: readBytes:" + Tools.Bytes2HexString(readBytes, readBytes.length));
                }
            }else{
                Log.d(TAG, "onClick: 空");
            }
        }
    }

    @Override
    public void serverSuccess(Integer status) {
        //
        Log.d(TAG, "serverSuccess: status:"+status);
        Toast.makeText(this, "入库成功...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void serverFail(Integer status) {
        switch (status) {
            case MessageConstant.NET_WORK_ERROR:
                Toast.makeText(this, "网络异常", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_STATUS_FAIL:
                Toast.makeText(this, "入库失败...", Toast.LENGTH_LONG).show();
                break;
            case MessageConstant.PRODUCT_JSON_FORMAT_ERROR:
                Toast.makeText(this, "服务器回传数据异常...", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "未知异常", Toast.LENGTH_LONG).show();
        }
    }

}
