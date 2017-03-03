package com.csabacsete.sharedshoppinglist.splash;

import com.csabacsete.sharedshoppinglist.base.BaseContract;
import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;

public class SplashPresenter implements BaseContract.Presenter {

    private Authenticator authenticator;
    private Navigator navigator;

    public SplashPresenter(Authenticator authenticator, Navigator navigator) {
        this.authenticator = authenticator;
        this.navigator = navigator;
    }

    @Override
    public void onPageLoaded() {
        if (authenticator.isUserLoggedIn()) {
            navigator.goToMain();
        } else {
            navigator.goToLogin();
        }
    }

    @Override
    public void teardown() {
        this.authenticator = null;
        this.navigator = null;
    }
}
