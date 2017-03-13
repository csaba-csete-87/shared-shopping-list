package com.csabacsete.sharedshoppinglist.data;

public class Pending {

    public String userEmail;
    public String listId;

    public Pending() {
    }

    public Pending(String userEmail, String listId) {
        this.userEmail = userEmail;
        this.listId = listId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
}
