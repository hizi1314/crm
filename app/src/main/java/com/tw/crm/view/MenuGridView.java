package com.tw.crm.view;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.GridView;

import com.tw.crm.R;
import com.tw.crm.adapter.MenuAdapter;
import com.tw.crm.entity.MenuItemsEntity;
import com.tw.crm.utils.ApiRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yindezhi on 17/7/5.
 */

public class MenuGridView extends GridView {

    private int role;
    private Activity activity;

    public MenuGridView(Context context) {
        super(context);
        init();
    }

    public MenuGridView(Activity mainActivity, int role) {
        super(mainActivity);
        this.activity = mainActivity;
        this.role = role;
        init();
    }

    /**
     * 初始化视图
     */
    private void init() {
        //设置为4列
        setNumColumns(3);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        List<MenuItemsEntity> menuItemsEntities = new ArrayList<>();
        //根据权限动态生成菜单
        if (role == ApiRole.ROLE_ADMIN) {
            menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_PRODUCT_STORAGE, "", getResources().getString(R.string.menu_product_storage)));
            menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_AGENT_MANAGEMENT, "", getResources().getString(R.string.menu_agent_management)));
        } else if (role == ApiRole.ROLE_PROVINCE_PROXY) {
            menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_AGENT_MANAGEMENT, "", getResources().getString(R.string.menu_agent_management)));
        }
        setAdapter(new MenuAdapter(menuItemsEntities, activity));
    }
}
