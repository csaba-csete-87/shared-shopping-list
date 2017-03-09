package com.csabacsete.sharedshoppinglist.navigator;

import com.csabacsete.sharedshoppinglist.data.ShoppingList;

public interface Navigator {

    void goToLogin();

    void goToMain();

    void goToLists();

    void goToMyInfo();

    void goToShoppingList(ShoppingList shoppingList);

    void goToSettings();
}
