package com.csabacsete.sharedshoppinglist.shopping_list;

import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.data.ShoppingListItem;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;

import java.util.List;

import timber.log.Timber;

public class ShoppingListPresenter implements ShoppingListContract.Presenter, Repository.SaveShoppingListCallback, Repository.DeleteShoppingListCallback {

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
        if (shoppingList == null) {
            shoppingList = new ShoppingList();
            String userId = authenticator.getCurrentUser().getId();
            shoppingList.addUser(userId, true);
            view.setShoppingList(shoppingList);
        }
        view.setTitle(shoppingList.getTitle());
        view.setItems(shoppingList.getListItems());
    }

    @Override
    public void onDeleteListClicked() {
        view.showConfirmDeleteListDialog();
    }

    @Override
    public void onAddPeopleClicked() {
        String shoppingListId = view.getShoppingList().getId();
        navigator.goToAddPeople(shoppingListId);
    }

    @Override
    public void onConfirmDeleteShoppingList() {
        view.showProgress();
        repository.deleteShoppingList(view.getShoppingList(), this);
    }

    @Override
    public void onDoneClicked() {
        ShoppingList shoppingList = view.getShoppingList();

        String title = view.getShoppingListTitle();
        shoppingList.setTitle(title);

        List<ShoppingListItem> shoppingListItems = view.getShoppingListItems();
        shoppingList.setListItems(shoppingListItems);

        view.showProgress();
        repository.saveShoppingList(shoppingList, this);

        navigator.goToLists();
    }

    @Override
    public void onSaveShoppingListSuccess() {
        view.hideProgress();
        view.showSaveSuccess();
        navigator.goToLists();
    }

    @Override
    public void onSaveShoppingListError(Throwable t) {
        Timber.e(t.getMessage());
        view.hideProgress();
        view.showSaveError();
    }

    @Override
    public void onDeleteShoppingListSuccess() {
        view.hideProgress();
        navigator.goToLists();
    }

    @Override
    public void onDeleteShoppingListError(Throwable t) {
        view.hideProgress();
        view.showDeleteListError();
    }
}
