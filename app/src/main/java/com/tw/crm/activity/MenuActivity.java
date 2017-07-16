package com.tw.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.tw.crm.R;
import com.tw.crm.adapter.MenuAdapter;
import com.tw.crm.entity.MenuItemsEntity;
import com.tw.crm.utils.ApiRole;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 菜单Activity
 * <p>
 * 根据权限显示菜单
 */
public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MenuActivity";

    @BindView(R.id.menu_main)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);
        ButterKnife.bind(this);
        initMenu();
    }

    private void initMenu() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            String roleId = bundle.getString("roleId");
            if (ApiRole.ROLE_ADMIN.equals(roleId)) {
                Log.d(TAG, "管理员登录..");
                gridView.setAdapter(new MenuAdapter(create_admin_menu(), MenuActivity.this, this));
            } else if (ApiRole.ROLE_PROVINCE_PROXY.equals(roleId)) {
                Log.d(TAG, "省代理登录...");
                gridView.setAdapter(new MenuAdapter(create_province_proxy_menu(), MenuActivity.this, this));
            }
        }
    }

    /**
     * 生成管理员目录菜单
     *
     * @return
     */
    private List<MenuItemsEntity> create_admin_menu() {
        List<MenuItemsEntity> menuItemsEntities = new ArrayList<>();
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_PRODUCT_STORAGE, "", getResources().getString(R.string.menu_product_storage)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_AGENT_MANAGEMENT, "", getResources().getString(R.string.menu_agent_management)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_PACKING, "", getResources().getString(R.string.menu_packing)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_SEND_AGENT, "", getResources().getString(R.string.menu_send_agent)));
        return menuItemsEntities;
    }

    /**
     * 生成省代理目录菜单【PROVINCE_PROXY】
     *
     * @return
     */
    private List<MenuItemsEntity> create_province_proxy_menu() {
        List<MenuItemsEntity> menuItemsEntities = new ArrayList<>();
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_SEND_AGENT, "", getResources().getString(R.string.menu_send_agent)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_RECEIPC_BOXS, "", getResources().getString(R.string.menu_receiptc_boxs)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_SEND_DISTRIBUTOR, "", getResources().getString(R.string.menu_send_distributor)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_RECEIPTC_PRODUCTS, "", getResources().getString(R.string.menu_receiptc_products)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_CONSTRUCTION, "", getResources().getString(R.string.menu_receiptc_products)));
        return menuItemsEntities;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "v tag:" + v.getTag());
        String tag = String.valueOf(v.getTag());
        Intent intent = new Intent();
        switch (tag) {
            case ApiRole.MENU_PRODUCT_STORAGE:
                intent.setClass(this, ProductStorageActivity.class);
                break;
            case ApiRole.MENU_AGENT_MANAGEMENT:
                intent.setClass(this, ProductStorageActivity.class);
                break;
            case ApiRole.MENU_PACKING:
                intent.setClass(this, ProductStorageActivity.class);
                break;
            case ApiRole.MENU_SEND_AGENT:
                intent.setClass(this, ProductStorageActivity.class);
                break;
        }

        startActivity(intent);

    }
}
