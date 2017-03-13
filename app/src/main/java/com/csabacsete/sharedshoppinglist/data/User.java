package com.csabacsete.sharedshoppinglist.data;

import java.util.HashMap;

public class User {

    private String id;

    private String displayName;

    private String email;

    private String photoUrl;

    private HashMap<String, Boolean> shoppingLists;

    public User() {
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
        String[] emailArray = email.split("@");
        if (emailArray.length > 0) {
            this.displayName = emailArray[0];
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public HashMap<String, Boolean> getShoppingLists() {
        return shoppingLists;
    }
}
