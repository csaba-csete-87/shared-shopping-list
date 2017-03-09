package com.csabacsete.sharedshoppinglist.shopping_list;

import com.csabacsete.sharedshoppinglist.data.Access;
import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;

public class ShoppingListPresenter implements ShoppingListContract.Presenter, Repository.SaveShoppingListCallback, Repository.GetShoppingListCallback {

    private final ShoppingListContract.View view;
    private final Authenticator authenticator;
    private final Repository repository;
    private final Navigator navigator;

    public ShoppingListPresenter(ShoppingListContract.View view, Authenticator authenticator, Repository repository, Navigator navigator) {
        this.view = view;
        this.authenticator = authenticator;
        this.repository = repository;
        this.navigator = navigator;
    }

    @Override
    public void onPageLoaded() {
        ShoppingList shoppingList = view.getShoppingList();
        if (shoppingList != null) {
            view.setTitle(shoppingList.getTitle());
        }
    }

    @Override
    public void onDoneClicked() {
        ShoppingList shoppingList = view.getShoppingList();
        String title = view.getShoppingListTitle();

        if (shoppingList != null) {
            shoppingList.setTitle(title);
        } else {
            String userId = authenticator.getCurrentUser().getId();
            shoppingList = new ShoppingList(title);

            Access access = new Access("owner", shoppingList.getId(), userId);
            repository.createShoppingList(shoppingList, access, this);
        }

        view.showProgress();
        repository.updateShoppingList(shoppingList, this);
    }

    @Override
    public void onSaveShoppingListSuccess() {
        view.hideProgress();
        view.showSaveSuccess();
        navigator.goToLists();
    }

    @Override
    public void onSaveShoppingListError(Throwable t) {
        view.hideProgress();
        view.showSaveError();
    }

    @Override
    public void onGetShoppingListSuccess(ShoppingList shoppingList) {
        view.setTitle(shoppingList.getTitle());
    }

    @Override
    public void onGetShoppingListError(Throwable t) {
        view.showGetListError();
    }
}
