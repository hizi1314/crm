package com.tw.crm.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.tw.crm.R;
import com.tw.crm.entity.MenuItemsEntity;
import com.tw.crm.fragment.ProductStorageFragment;
import java.util.List;
import static com.tw.crm.utils.ApiRole.MENU_AGENT_MANAGEMENT;
import static com.tw.crm.utils.ApiRole.MENU_PRODUCT_STORAGE;


/**
 * Created by yindezhi on 17/7/5.
 */

public class MenuAdapter extends BaseAdapter {

    private List<MenuItemsEntity> list;

    private Activity activity;

    public MenuAdapter(List<MenuItemsEntity> list,Activity activity) {
        this.list = list;
        this.activity = activity;
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
        //暂时用按钮菜单
        Button button = new Button(activity);
        final MenuItemsEntity menuItemsEntity = list.get(position);
        button.setText(menuItemsEntity.getMenu_name());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //菜单打开选项
                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                String tag = menuItemsEntity.getTag();
                if(MENU_PRODUCT_STORAGE.equals(tag)){
                    //产品入库
                    transaction.replace(R.id.main_layout,new ProductStorageFragment());
                }else if(MENU_AGENT_MANAGEMENT.equals(tag)){

                }

                transaction.commit();
            }
        });

        return button;
    }
}
