package com.tw.crm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tw.crm.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.main_layout,new LoginFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }else{
            getFragmentManager().popBackStack();
        }
    }
}
