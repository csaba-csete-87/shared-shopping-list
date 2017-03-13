package com.csabacsete.sharedshoppinglist.navigator;

import com.csabacsete.sharedshoppinglist.data.ShoppingList;

public interface Navigator {

    void goToLogin();

    void goToLists();

    void goToMyInfo();

    void goToShoppingList(ShoppingList shoppingList);

    void goToSettings();

    void goToAddPeople(String shoppingListId);

    void goBack();
}
