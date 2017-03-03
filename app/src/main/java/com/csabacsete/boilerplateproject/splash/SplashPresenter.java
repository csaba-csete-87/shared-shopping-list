package com.csabacsete.boilerplateproject.splash;

import com.csabacsete.boilerplateproject.base.BaseContract;
import com.csabacsete.boilerplateproject.data.Authenticator;
import com.csabacsete.boilerplateproject.navigator.Navigator;

public class SplashPresenter implements BaseContract.Presenter, Authenticator.WaitCallback {

    private Authenticator authenticator;
    private BaseContract.View view;
    private final Navigator navigator;

    public SplashPresenter(BaseContract.View view, Authenticator authenticator, Navigator navigator) {
        this.view = view;
        this.authenticator = authenticator;
        this.navigator = navigator;
    }

    @Override
    public void onPageLoaded() {
        authenticator.wait(2000, this);
    }

    @Override
    public void teardown() {
        this.authenticator = null;
        this.view = null;
    }

    @Override
    public void onWaitFinished() {
        if (authenticator.isUserLoggedIn()) {
            navigator.goToMain();
        } else {
            navigator.goToLogin();
        }
    }
}
