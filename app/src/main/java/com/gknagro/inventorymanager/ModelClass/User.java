package com.gknagro.inventorymanager.ModelClass;

public class User {
    public User(){};
    String name, mobile, address,pannum;

    public User(String name, String mobile, String address, String pannum){
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.pannum = pannum;
    }
    public User(String name, String mobile, String address){
        this.name = name;
        this.mobile = mobile;
        this.address = address;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPannum() {
        return pannum;
    }
    public void setPannum(String pannum) {
        this.pannum = pannum;
    }

}
