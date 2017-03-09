package com.csabacsete.sharedshoppinglist.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.UUID;

public class ShoppingList implements Parcelable {

    public String id;
    public String title;
    public long timestamp;

    public ShoppingList() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ShoppingList(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.timestamp = Calendar.getInstance().getTimeInMillis();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimestamp() {
        return timestamp;
    }

    protected ShoppingList(Parcel in) {
        id = in.readString();
        title = in.readString();
        timestamp = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeLong(timestamp);
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