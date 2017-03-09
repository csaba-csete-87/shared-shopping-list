package com.csabacsete.sharedshoppinglist.shopping_lists;

import com.csabacsete.sharedshoppinglist.data.ShoppingList;

import java.util.List;

public interface ShoppingListsContract {

    interface View {

        void showProgress();

        void hideProgress();

        void showShoppingLists(List<ShoppingList> shoppingLists);

        void showGetShoppingListsError();

        void showEmptyView();
    }

    interface Presenter {

        void onAddShoppingListButtonClicked();

        void onPageLoaded();

        void onShoppingListSelected(ShoppingList shoppingList);
    }
}
