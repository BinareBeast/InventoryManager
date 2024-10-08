package com.gknagro.inventorymanager.ModelClass;

public class Product {
    private String id;
    private String name;
    private int price,quantity;

    public Product(){}


    public Product(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;

    }
    public Product(String name,int price,int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() { return name;}

    public void setName(String name) {this.name = name;}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getTotalPrice(){return quantity * price; }

}
