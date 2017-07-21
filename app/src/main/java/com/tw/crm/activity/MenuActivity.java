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

    private String userid ;
    private void initMenu() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            String roleId = bundle.getString("roleId");
            userid = bundle.getString("userid");
            Log.d(TAG, "initMenu: roleId:"+roleId);
            Log.d(TAG, "initMenu: userid:"+userid);
            if (ApiRole.ROLE_ADMIN.equals(roleId)) {
                Log.d(TAG, "管理员登录..");
                gridView.setAdapter(new MenuAdapter(create_admin_menu(), MenuActivity.this, this));
            } else if (ApiRole.ROLE_PROVINCE_PROXY.equals(roleId)) {
                Log.d(TAG, "省代理登录...");
                gridView.setAdapter(new MenuAdapter(create_province_proxy_menu(), MenuActivity.this, this));
            } else if (ApiRole.ROLE_JINGXIAO.equals(roleId)) {
                Log.d(TAG, "经销商登录...");
                gridView.setAdapter(new MenuAdapter(create_jingxiao_menu(), MenuActivity.this, this));
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
       // menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_AGENT_MANAGEMENT, "", getResources().getString(R.string.menu_agent_management)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_PACKING, "", getResources().getString(R.string.menu_packing)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_SEND_AGENT, "", getResources().getString(R.string.menu_send_agent)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_CONSTRUCTION,"",getResources().getString(R.string.menu_construction)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_SECURITY,"",getResources().getString(R.string.menu_security)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_TRACK,"",getResources().getString(R.string.menu_track)));


        return menuItemsEntities;
    }

    /**
     * 生成省代理目录菜单【PROVINCE_PROXY】
     *
     * @return
     */
    private List<MenuItemsEntity> create_province_proxy_menu() {
        List<MenuItemsEntity> menuItemsEntities = new ArrayList<>();
       // menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_SEND_AGENT, "", getResources().getString(R.string.menu_send_agent)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_RECEIPC_BOXS, "", getResources().getString(R.string.menu_receiptc_boxs)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_SEND_DISTRIBUTOR, "", getResources().getString(R.string.menu_send_distributor)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_CONSTRUCTION, "", getResources().getString(R.string.menu_construction)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_SECURITY,"",getResources().getString(R.string.menu_security)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_TRACK,"",getResources().getString(R.string.menu_track)));

        return menuItemsEntities;
    }


    /**
     * 生成经销商目录菜单【PROVINCE_PROXY】
     *
     * @return
     */
    private List<MenuItemsEntity> create_jingxiao_menu() {
        List<MenuItemsEntity> menuItemsEntities = new ArrayList<>();

        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_RECEIPTC_PRODUCTS, "", getResources().getString(R.string.menu_receiptc_products)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_CONSTRUCTION, "", getResources().getString(R.string.menu_construction)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_SECURITY,"",getResources().getString(R.string.menu_security)));
        menuItemsEntities.add(new MenuItemsEntity(ApiRole.MENU_TRACK,"",getResources().getString(R.string.menu_track)));
        return menuItemsEntities;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "v tag:" + v.getTag());
        String tag = String.valueOf(v.getTag());
        Intent intent = new Intent();
        Log.d(TAG, "onClick: userid:"+userid);
        intent.putExtra("userid",userid);
        Log.d(TAG, "onClick: "+tag);
        switch (tag) {

            case ApiRole.MENU_PRODUCT_STORAGE:
                intent.setClass(this, ProductStorageActivity.class);
                break;
            case ApiRole.MENU_PACKING:
                intent.setClass(this, PackingActivity.class);
                break;
            case ApiRole.MENU_SEND_AGENT:
                intent.setClass(this, SendAgentActivity.class);
                break;
            case ApiRole.MENU_RECEIPC_BOXS:
                intent.setClass(this, ReceiptcboxsActivity.class);
                break;
            case ApiRole.MENU_SEND_DISTRIBUTOR:
                intent.setClass(this, SenddistributorActivity.class);
                break;
            case ApiRole.MENU_RECEIPTC_PRODUCTS:
                intent.setClass(this, ReceiptcproductsActivity.class);
                break;
            case ApiRole.MENU_CONSTRUCTION:
                intent.setClass(this, ConstructionActivity.class);
                break;
            case ApiRole.MENU_SECURITY:
                intent.setClass(this, SecurityActivity.class);
                break;
            case ApiRole.MENU_TRACK:
                intent.setClass(this, TrackActivity.class);
                break;

        }
        startActivity(intent);

    }
}
