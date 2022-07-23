package com.example.coleenskitchenbakeryapp;

public class Order {
    String name;
    String phoneno;
        String state;
        String city;
        String locality;
        String pincode;
        String ddate;
        String TotalPrice;
        String uid;
        String orderstate;
        String delistate;

    public Order() {
    }

    public Order(String name, String phoneno, String state, String city, String locality, String pincode, String ddate, String totalPrice, String uid, String orderstate, String delistate) {
        this.name = name;
        this.phoneno = phoneno;
        this.state = state;
        this.city = city;
        this.locality = locality;
        this.pincode = pincode;
        this.ddate = ddate;
        TotalPrice = totalPrice;
        this.uid = uid;
        this.orderstate = orderstate;
        this.delistate = delistate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getDelistate() {
        return delistate;
    }

    public void setDelistate(String delistate) {
        this.delistate = delistate;
    }
}



