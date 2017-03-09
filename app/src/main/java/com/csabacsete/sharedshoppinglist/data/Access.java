package com.csabacsete.sharedshoppinglist.data;

import java.util.UUID;

/**
 * Created by ccsete on 3/9/17.
 */

public class Access {

    public String id;
    public String accessType;
    public String listId;
    public String userId;

    public Access() {
    }

    public Access(String accessType, String listId, String userId) {
        this.id = UUID.randomUUID().toString();
        this.accessType = accessType;
        this.listId = listId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
