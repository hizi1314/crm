package com.tw.crm.entity;

/**
 * Created by hizi on 2017/7/18.
 */

public class ProductEntity {
    private String userid ;
    private String ifid;
    private String sboxids ;
    private Integer status ;

    private String name ;
    private String batch;
    private String color;
    private String specifications;

    //操作  装箱 发箱 发货 收货
    private String operation;

    private String userid_p;

    private String userid_j;
    public ProductEntity(String userid ,String rfid ,String name,String batch ,String color,String specifications,Integer status ,String operation){
        this.userid = userid ;
        this.ifid = rfid ;
        this.name=name ;
        this.batch=batch ;
        this.color=color ;
        this.specifications=specifications;
        this.status=status ;
        this.operation = operation;
    }
//装箱
    public ProductEntity(String ifid,String sboxids,String userid ,String operation){
        this.ifid=ifid ;
        this.sboxids=sboxids;
        this.userid =userid ;
        this.operation=operation;
    }

//入库
        public  ProductEntity(String ifid,String sboxids,String userid,String userid_p,String userid_j,Integer status ,String operation){

            this.ifid=ifid ;
            this.sboxids=sboxids;
            this.userid =userid ;
            this.userid_p=userid_p;
            this.userid_j=userid_j;
            this.status=status;
            this.operation=operation;
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


    public String getIfid() {
        return ifid;
    }

    public void setIfid(String ifid) {
        this.ifid = ifid;
    }
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getSboxids() {
        return sboxids;
    }

    public void setSboxids(String sboxids) {
        this.sboxids = sboxids;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

}
