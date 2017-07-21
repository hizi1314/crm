package com.tw.crm.entity;

/**
 * Created by hizi on 2017/7/19.
 */

public class ConstructionEntity {
    public ConstructionEntity(String userid ,String rfidtid,String userid_yw,String customname,String tel,String carnum,String cartype,String technumber){
        this.userid = userid;
        this.rfidtid=rfidtid;
        this.userid_yw=userid_yw;
        this.customname=customname;
        this.tel=tel;
        this.carnum=carnum;
        this.cartype=cartype;
        this.technumber=technumber;
    }
    private String userid ;
    private String rfidtid  ;
    private String userid_yw;
    private String customname;
    private String tel;
    private String carnum;
    private String cartype;
    private String technumber;
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRfidtid() {
        return rfidtid;
    }

    public void setRfidtid(String rfidtid) {
        this.rfidtid = rfidtid;
    }

    public String getUserid_yw() {
        return userid_yw;
    }

    public void setUserid_yw(String userid_yw) {
        this.userid_yw = userid_yw;
    }

    public String getCustomname() {
        return customname;
    }

    public void setCustomname(String customname) {
        this.customname = customname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getTechnumber() {
        return technumber;
    }

    public void setTechnumber(String technumber) {
        this.technumber = technumber;
    }


}
