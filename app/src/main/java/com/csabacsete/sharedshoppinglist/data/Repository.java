package com.csabacsete.sharedshoppinglist.data;

import java.util.List;

public interface Repository {

    interface GetUserCallback {

        void onGetUserSuccess(User user);

        void onGetUserError(Throwable t);
    }

    void getUserById(String userId, GetUserCallback callback);

    interface DeleteShoppingListCallback {

        void onDeleteShoppingListSuccess();

        void onDeleteShoppingListError(Throwable t);
    }

    void deleteShoppingList(ShoppingList shoppingList, DeleteShoppingListCallback callback);

    interface SavePendingCallback {

        void onSavePendingSuccess();

        void onSavePendingError(Throwable t);
    }

    void savePendingInvitations(List<Pending> pendingList, SavePendingCallback callback);

    interface CreateUserCallback {

        void onSaveUserSuccess(User user);

        void onSaveUserError(Throwable t);
    }

    void saveUser(User user, CreateUserCallback callback);

    interface GetShoppingListsCallback {

        void onGetShoppingListsSuccess(List<ShoppingList> shoppingLists);

        void onGetShoppingListsError(Throwable t);
    }

    void getShoppingListsByUserId(String userId, GetShoppingListsCallback callback);

    interface SaveShoppingListCallback {

        void onSaveShoppingListSuccess();

        void onSaveShoppingListError(Throwable t);
    }

    void saveShoppingList(ShoppingList shoppingList, SaveShoppingListCallback callback);
}
