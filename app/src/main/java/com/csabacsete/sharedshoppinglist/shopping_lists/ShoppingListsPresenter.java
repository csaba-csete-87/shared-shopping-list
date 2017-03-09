package com.csabacsete.sharedshoppinglist.shopping_lists;

import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;

import java.util.List;

public class ShoppingListsPresenter implements ShoppingListsContract.Presenter, Repository.GetShoppingListsCallback {

    private final ShoppingListsContract.View view;
    private final Navigator navigator;
    private final Repository repository;
    private final Authenticator authenticator;

    public ShoppingListsPresenter(ShoppingListsContract.View view, Repository repository, Navigator navigator, Authenticator authenticator) {
        this.view = view;
        this.repository = repository;
        this.navigator = navigator;
        this.authenticator = authenticator;
    }

    @Override
    public void onAddShoppingListButtonClicked() {
        navigator.goToShoppingList(null);
    }

    @Override
    public void onPageLoaded() {
        view.showProgress();
        String userId = authenticator.getCurrentUser().getId();
        repository.getShoppingListsByUserId(userId, this);
    }

    @Override
    public void onShoppingListSelected(ShoppingList shoppingList) {
        navigator.goToShoppingList(shoppingList);
    }

    @Override
    public void onGetShoppingListsSuccess(List<ShoppingList> shoppingLists) {
        view.hideProgress();
        if (shoppingLists != null && shoppingLists.size() > 0) {
            view.showShoppingLists(shoppingLists);
        } else {
            view.showEmptyView();
        }
    }

    @Override
    public void onGetShoppingListsError(Throwable t) {
        view.hideProgress();
        view.showGetShoppingListsError();
    }
}
