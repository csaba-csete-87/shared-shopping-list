package com.csabacsete.sharedshoppinglist.data;

import java.util.List;

public interface Repository {

    void createShoppingList(ShoppingList shoppingList, Access access, SaveShoppingListCallback callback);

    interface GetShoppingListCallback {

        void onGetShoppingListSuccess(ShoppingList shoppingList);

        void onGetShoppingListError(Throwable t);
    }

    void getShoppingListById(String listId, GetShoppingListCallback callback);

    interface GetShoppingListsCallback {

        void onGetShoppingListsSuccess(List<ShoppingList> shoppingLists);

        void onGetShoppingListsError(Throwable t);
    }

    void getShoppingListsByUserId(String userId, GetShoppingListsCallback callback);

    interface SaveShoppingListCallback {

        void onSaveShoppingListSuccess();

        void onSaveShoppingListError(Throwable t);
    }

    void updateShoppingList(ShoppingList shoppingList, SaveShoppingListCallback callback);
}
