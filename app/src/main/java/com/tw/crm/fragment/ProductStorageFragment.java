package com.tw.crm.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tw.crm.R;

/**
 * Created by yindezhi on 17/7/9.

 * 产品入库功能模块
 */
public class ProductStorageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.product_storage_layout,container,false);

        return rootView;
    }




}
