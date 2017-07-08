package com.tw.crm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.tw.crm.entity.MenuItemsEntity;
import java.util.List;

/**
 * Created by yindezhi on 17/7/5.
 */

public class MenuAdapter extends BaseAdapter {

    private List<MenuItemsEntity> list;

    public MenuAdapter(List<MenuItemsEntity> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        return null;
    }
}
