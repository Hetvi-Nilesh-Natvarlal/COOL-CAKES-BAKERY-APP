package com.example.coleenskitchenbakeryapp;

public class Cart {

    String date,desc_enter,devprice,flavours,img_url,pid,pname,price,quantity,time,uid,wordings,Image;

    public Cart() {
    }

    public Cart(String date, String desc_enter, String devprice, String flavours, String img_url, String pid, String pname, String price, String quantity, String time, String uid, String wordings) {
        this.date = date;
        this.desc_enter = desc_enter;
        this.devprice = devprice;
        this.flavours = flavours;
        this.img_url = img_url;
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
        this.uid = uid;
        this.wordings = wordings;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc_enter() {
        return desc_enter;
    }

    public void setDesc_enter(String desc_enter) {
        this.desc_enter = desc_enter;
    }

    public String getDevprice() {
        return devprice;
    }

    public void setDevprice(String devprice) {
        this.devprice = devprice;
    }

    public String getFlavours() {
        return flavours;
    }

    public void setFlavours(String flavours) {
        this.flavours = flavours;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWordings() {
        return wordings;
    }

    public void setWordings(String wordings) {
        this.wordings = wordings;
    }
}
