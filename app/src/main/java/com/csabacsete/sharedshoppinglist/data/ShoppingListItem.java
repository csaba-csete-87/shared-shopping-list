package com.csabacsete.sharedshoppinglist.data;

public class ShoppingListItem {

    private String name;

    private boolean bought;

    public ShoppingListItem() {
        this.name = "";
        this.bought = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
