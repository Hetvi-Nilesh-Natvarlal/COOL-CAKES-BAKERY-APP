package com.example.coleenskitchenbakeryapp;

public class Products {

    private String pname,description,price,img,cat_id,pid,cat_name,weight,flavours,quantity,devprice;

    Products(){}

    public Products(String pname, String description, String price, String img, String cat_id, String pid, String cat_name, String weight, String flavours, String quantity, String devprice) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.img = img;
        this.cat_id = cat_id;
        this.pid = pid;
        this.cat_name = cat_name;
        this.weight = weight;
        this.flavours = flavours;
        this.quantity = quantity;
        this.devprice = devprice;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFlavours() {
        return flavours;
    }

    public void setFlavours(String flavours) {
        this.flavours = flavours;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDevprice() {
        return devprice;
    }

    public void setDevprice(String devprice) {
        this.devprice = devprice;
    }
}
