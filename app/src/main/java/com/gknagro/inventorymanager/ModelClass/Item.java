package com.gknagro.inventorymanager.ModelClass;

public class Item {
    String name,id;
    int price;
    int pieces;


    int quantity;
    int subtotal;
    public Item(){    }
    public Item(String id,String name,int price, int pieces){
        this.id = id;
        this.name =name;
        this.price = price;
        this.pieces = pieces;

    }
    public Item(String id,String name, int price,int pieces,int subtotal){
        this.id = id;
        this.name =name;
        this.price = price;
        this.pieces =pieces;
        this.subtotal= subtotal;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pices) {
        this.pieces = pices;
    }
    public int getSubtotal(){ return price*pieces;}

}
