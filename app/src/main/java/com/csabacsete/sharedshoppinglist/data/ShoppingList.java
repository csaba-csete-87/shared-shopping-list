package com.csabacsete.sharedshoppinglist.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ShoppingList implements Parcelable {

    public String id;
    public String title;
    public long created;
    public long lastEdited;
    public HashMap<String, Boolean> users;
    public List<ShoppingListItem> listItems;

    public ShoppingList() {
        setDefaults();
    }

    public ShoppingList(String title) {
        this.title = title;
        setDefaults();
    }

    private void setDefaults() {
        this.id = UUID.randomUUID().toString();
        this.created = Calendar.getInstance().getTimeInMillis();
        this.lastEdited = created;
        listItems = new ArrayList<>();
        listItems.add(new ShoppingListItem());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(long lastEdited) {
        this.lastEdited = lastEdited;
    }

    public HashMap<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, Boolean> users) {
        this.users = users;
    }

    public List<ShoppingListItem> getListItems() {
        return listItems;
    }

    public void setListItems(List<ShoppingListItem> listItems) {
        this.listItems = listItems;
    }

    public void addUser(String userId, boolean isOwner) {
        if (users == null) {
            users = new HashMap<>();
        }
        users.put(userId, isOwner);
    }

    protected ShoppingList(Parcel in) {
        id = in.readString();
        title = in.readString();
        created = in.readLong();
        lastEdited = in.readLong();
        users = (HashMap) in.readValue(HashMap.class.getClassLoader());
        if (in.readByte() == 0x01) {
            listItems = new ArrayList<ShoppingListItem>();
            in.readList(listItems, ShoppingListItem.class.getClassLoader());
        } else {
            listItems = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeLong(created);
        dest.writeLong(lastEdited);
        dest.writeValue(users);
        if (listItems == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(listItems);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ShoppingList> CREATOR = new Parcelable.Creator<ShoppingList>() {
        @Override
        public ShoppingList createFromParcel(Parcel in) {
            return new ShoppingList(in);
        }

        @Override
        public ShoppingList[] newArray(int size) {
            return new ShoppingList[size];
        }
    };
}