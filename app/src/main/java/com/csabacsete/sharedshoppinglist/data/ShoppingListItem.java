package com.csabacsete.sharedshoppinglist.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ShoppingListItem implements Parcelable {

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

    protected ShoppingListItem(Parcel in) {
        name = in.readString();
        bought = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (bought ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ShoppingListItem> CREATOR = new Parcelable.Creator<ShoppingListItem>() {
        @Override
        public ShoppingListItem createFromParcel(Parcel in) {
            return new ShoppingListItem(in);
        }

        @Override
        public ShoppingListItem[] newArray(int size) {
            return new ShoppingListItem[size];
        }
    };
}