package com.csabacsete.sharedshoppinglist.main;

import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.User;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;
    private Navigator navigator;
    private Authenticator authenticator;

    public MainPresenter(MainContract.View view, Authenticator authenticator, Navigator navigator) {
        this.view = view;
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
    public void onDestroy() {
        this.authenticator = null;
        this.navigator = null;
    }

    @Override
    public void onPageLoaded() {
        User u = authenticator.getCurrentUser();
        view.setUserName(u.getDisplayName());
        view.setUserEmail(u.getEmail());
        view.setUserImage(u.getPhotoUrl());

        navigator.goToLists();
    }
}
