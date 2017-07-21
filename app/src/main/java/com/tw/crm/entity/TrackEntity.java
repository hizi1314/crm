package com.tw.crm.entity;

/**
 * Created by hizi on 2017/7/20.
 */


public class TrackEntity {
    public  TrackEntity(String pname ,String rfidtid,String time0,String time1,String time2,String time3){
         this.pname =pname;
        this.rfidtid=rfidtid;
        this.time0=time0;
        this.time1=time1;
        this.time2=time2;
        this.time3=time3;
    }
    private String  pname ;
    private String rfidtid ;
    private String pstatus ;
    private  String time0;
    private String time1;
    private String time2;
    private String time3;
    private String time4;
    private String time5;
    private String time6;
    private String userid;
    private String userid_p;
    private String userid_j;
    private String userid_sg;
    private String userid_yw ;
    private String carnum ;
    private String cartype ;
    private String customname;
    private String customtel ;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRfidtid() {
        return rfidtid;
    }

    public void setRifidtid(String rfidtid) {
        this.rfidtid = rfidtid;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public String getTime0() {
        return time0;
    }

    public void setTime0(String time0) {
        this.time0 = time0;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public String getTime4() {
        return time4;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    public String getTime5() {
        return time5;
    }

    public void setTime5(String time5) {
        this.time5 = time5;
    }

    public String getTime6() {
        return time6;
    }

    public void setTime6(String time6) {
        this.time6 = time6;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid_p() {
        return userid_p;
    }

    public void setUserid_p(String userid_p) {
        this.userid_p = userid_p;
    }

    public String getUserid_j() {
        return userid_j;
    }

    public void setUserid_j(String userid_j) {
        this.userid_j = userid_j;
    }

    public String getUserid_sg() {
        return userid_sg;
    }

    public void setUserid_sg(String userid_sg) {
        this.userid_sg = userid_sg;
    }

    public String getUserid_yw() {
        return userid_yw;
    }

    public void setUserid_yw(String userid_yw) {
        this.userid_yw = userid_yw;
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

    public String getCustomname() {
        return customname;
    }

    public void setCustomname(String customname) {
        this.customname = customname;
    }

    public String getCustomtel() {
        return customtel;
    }

    public void setCustomtel(String customtel) {
        this.customtel = customtel;
    }




}
