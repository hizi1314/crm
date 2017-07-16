package com.tw.crm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.tw.crm.entity.MenuItemsEntity;

import java.util.List;


/**
 * Created by yindezhi on 17/7/5.
 */

public class MenuAdapter extends BaseAdapter {
    private List<MenuItemsEntity> list;
    private Context context;
    private View.OnClickListener onClickListener;

    public MenuAdapter(List<MenuItemsEntity> list, Context context, View.OnClickListener onClickListener) {
        this.list = list;
        this.context = context;
        this.onClickListener = onClickListener;
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

        final MenuItemsEntity menuItemsEntity = list.get(position);
        //暂时用按钮菜单
        Button button = new Button(context);
        button.setText(menuItemsEntity.getMenu_name());
        button.setTag(menuItemsEntity.getTag());
        button.setOnClickListener(onClickListener);
        return button;
    }
}
