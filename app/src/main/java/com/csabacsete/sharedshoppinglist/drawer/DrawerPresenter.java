package com.csabacsete.sharedshoppinglist.drawer;

import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.data.User;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;
import com.csabacsete.sharedshoppinglist.utils.StringUtils;

public class DrawerPresenter implements DrawerContract.Presenter, Repository.GetUserCallback {

    private final DrawerContract.View view;
    private final Repository repository;
    private final Navigator navigator;
    private final Authenticator authenticator;

    public DrawerPresenter(DrawerContract.View view, Repository repository, Authenticator authenticator, Navigator navigator) {
        this.view = view;
        this.repository = repository;
        this.authenticator = authenticator;
        this.navigator = navigator;
    }

    @Override
    public void onListsMenuItemClicked() {
        navigator.goToLists();
    }

    @Override
    public void onMyInfoMenuItemClicked() {
        navigator.goToMyInfo();
    }

    @Override
    public void onSettingsMenuItemClicked() {
        navigator.goToSettings();
    }

    @Override
    public void onLogoutMenuItemClicked() {
        authenticator.logout();
        navigator.goToLogin();
    }

    @Override
    public void onPageLoaded() {
        repository.getUserById(authenticator.getCurrentUser().getId(), this);
    }

    @Override
    public void onGetUserSuccess(User user) {
        view.setUserName(user.getDisplayName());
        view.setUserEmail(user.getEmail());
        if(!StringUtils.isEmpty(user.getPhotoUrl())) {
            view.setUserImage(user.getPhotoUrl());
        }
    }

    @Override
    public void onGetUserError(Throwable t) {

    }
}
