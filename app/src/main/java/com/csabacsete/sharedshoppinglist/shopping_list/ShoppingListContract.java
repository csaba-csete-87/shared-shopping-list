package com.csabacsete.sharedshoppinglist.shopping_list;

import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.data.ShoppingListItem;

import java.util.List;

public interface ShoppingListContract {

    interface View {

        String getShoppingListTitle();

        void showProgress();

        void hideProgress();

        void showSaveSuccess();

        void showSaveError();

        ShoppingList getShoppingList();

        void setTitle(String title);

        void setItems(List<ShoppingListItem> listItems);

        void setShoppingList(ShoppingList shoppingList);

        List<ShoppingListItem> getShoppingListItems();

        void showConfirmDeleteListDialog();

        void showDeleteListError();
    }

    interface Presenter {

        void onPageLoaded();

        void onFinishedEditing();

        void onDeleteListClicked();

        void onAddPeopleClicked();

        void onConfirmDeleteShoppingList();

        void onDoneClicked();
    }
}
