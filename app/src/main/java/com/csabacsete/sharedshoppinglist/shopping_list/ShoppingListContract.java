package com.csabacsete.sharedshoppinglist.shopping_list;

import com.csabacsete.sharedshoppinglist.data.ShoppingList;

public interface ShoppingListContract {

    interface View {

        String getShoppingListTitle();

        void showProgress();

        void hideProgress();

        void showSaveSuccess();

        void showSaveError();

        ShoppingList getShoppingList();

        void setTitle(String title);

        void showGetListError();
    }

    interface Presenter {

        void onPageLoaded();

        void onDoneClicked();
    }
}
