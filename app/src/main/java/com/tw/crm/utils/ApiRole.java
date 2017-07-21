package com.tw.crm.utils;

/**
 * Created by yindezhi on 17/7/5.
 */

public class ApiRole {

    public static final String ROLE_ADMIN = "001";

    public static final String ROLE_PROVINCE_PROXY = "100";

    public static final String ROLE_JINGXIAO = "101";
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


//防伪
    public static final String MENU_SECURITY = "menu_security";

//跟踪
    public static final String MENU_TRACK = "menu_track";






    //public static final String API_LOGIN_URL = "http://120.77.255.8:8080/product/interactive/clientlogin";
    //public static final String API_PACKING = "http://120.77.255.8:8080/product/interactive/packing";

    private  static final String API_IP="http://192.168.0.191:8080";
//登陆
    public static final String API_LOGIN_URL = API_IP+"/product/interactive/clientlogin";

    //入库
    public static final String API_STORAGE = API_IP+"/product/interactive/storage";
    //装箱
    public static final String API_PACKING = API_IP+"/product/interactive/packing";

    //发箱
    public static final String API_SENDAGENT = API_IP+"/product/interactive/sendagent";

    //省代理收箱子
    public static final String API_RECEIPTCBOXS = API_IP+"/product/interactive/receiptcboxs";

    //发货经销商
    public static final String API_SENDDISTRIBUTOR = API_IP+"/product/interactive/senddistributor";

    //经销商收货
    public static final String API_RECEIPTCPRODUCTS = API_IP+"/product/interactive/receiptcproducts";

    //施工
    public static final String API_CONSTRUCTION = API_IP+"/product/interactive/construction";

//防伪
        public static final String API_SECURITY = API_IP+"/product/interactive/security";
//跟踪
        public static final String API_TRACK= API_IP+"/product/interactive/track";



    //查询所有省代理数据：interactive/selectAgents
    public static final String API_SELECTAGENT = API_IP+"/product/interactive/selectAgents";

    //查询省代理下的所有经销商数据：interactive/selectJingxs
    public static final String API_SELECTJINGXS= API_IP+"/product/interactive/selectJingxs";

    //查询所有 分销和业务
    public static final String API_SELECTFENXIAOYEWU= API_IP+"/product/interactive/selectFenxiaoAndYewu";

}
