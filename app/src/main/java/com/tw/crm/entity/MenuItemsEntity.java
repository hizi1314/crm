package com.tw.crm.entity;


/**
 * 菜单实体类
 */
public class MenuItemsEntity {

    private String icon;

    private String menu_name;

    private String tag;

    public MenuItemsEntity(String tag, String icon, String menu_name) {
        this.icon = icon;
        this.menu_name = menu_name;
        this.tag = tag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
