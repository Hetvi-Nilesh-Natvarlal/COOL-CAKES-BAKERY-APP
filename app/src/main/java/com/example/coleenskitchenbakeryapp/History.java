package com.example.coleenskitchenbakeryapp;

public class History {

    String date,deliprice,desc,flavour,img_url,pid,name,price,quantity,uid,uuid,wordings,ddate;

    public History() {
    }

    public History(String date, String deliprice, String desc, String flavour, String img_url, String pid, String name, String price, String quantity, String uid, String uuid, String wordings, String ddate) {
        this.date = date;
        this.deliprice = deliprice;
        this.desc = desc;
        this.flavour = flavour;
        this.img_url = img_url;
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uid = uid;
        this.uuid = uuid;
        this.wordings = wordings;
        this.ddate = ddate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeliprice() {
        return deliprice;
    }

    public void setDeliprice(String deliprice) {
        this.deliprice = deliprice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWordings() {
        return wordings;
    }

    public void setWordings(String wordings) {
        this.wordings = wordings;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }
}
