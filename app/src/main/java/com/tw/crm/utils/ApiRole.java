package com.tw.crm.utils;

/**
 * Created by yindezhi on 17/7/5.
 */

public class ApiRole {

    public static final String ROLE_ADMIN = "001";

    public static final String ROLE_PROVINCE_PROXY = "002";


    /**
     * 产品入库TAG //管理员
     */
    public static final String MENU_PRODUCT_STORAGE = "menu_product_storage";

    /**
     * 省代理管理TAG //管理员
     */
    public static final String MENU_AGENT_MANAGEMENT = "menu_agent_management";

    /**
     * 产品装箱 TAG //管理员
     */
    public static final String MENU_PACKING = "menu_packing";

    /**
     * 发货省代理 TAG //管理员
     */
    public static final String MENU_SEND_AGENT = "menu_send_agent";

    /**
     * 省代理确认收货 TAG //省代理
     */
    public static final String MENU_RECEIPC_BOXS = "menu_receiptc_boxs";

    /**
     * 发货经销商 TAG //省代理
     */
    public static final String MENU_SEND_DISTRIBUTOR = "menu_send_distributor";

    /**
     * 经销商确认收货 TAG //经销商 receiptcproducts
     */
    public static final String MENU_RECEIPTC_PRODUCTS = "menu_receiptc_products";

    /**
     * 施工单  //省代理 、经销商
     */
    public static final String MENU_CONSTRUCTION = "menu_construction";



    public static final String API_LOGIN_URL = "http://120.77.255.8:8080/product/interactive/clientlogin";
}
