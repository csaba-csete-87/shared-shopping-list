package com.csabacsete.boilerplateproject.login;

import com.csabacsete.boilerplateproject.data.Authenticator;
import com.csabacsete.boilerplateproject.navigator.Navigator;
import com.csabacsete.boilerplateproject.utils.Constants;
import com.csabacsete.boilerplateproject.utils.StringUtils;

public class LoginPresenter implements LoginContract.Presenter, Authenticator.LoginCallback {

    private Navigator navigator;
    private LoginContract.View view;
    private Authenticator authenticator;

    public LoginPresenter(LoginContract.View view, Authenticator authenticator, Navigator navigator) {
        this.view = view;
        this.authenticator = authenticator;
        this.navigator = navigator;
    }

    @Override
    public void login() {
        String email = view.getEmail();
        String password = view.getPassword();

        view.clearEmailErrors();
        view.clearPasswordErrors();

        if (StringUtils.isEmpty(password)) {
            view.showPasswordRequired();
            return;
        }
        if (!StringUtils.hasLength(password, Constants.MIN_PASSWORD_LENGTH)) {
            view.showPasswordTooShort();
            return;
        }
        if (StringUtils.isEmpty(email)) {
            view.showEmailRequired();
            return;
        }
        if (!StringUtils.isValidEmail(email)) {
            view.showEmailInvalid();
            return;
        }

        view.showProgress();
        authenticator.loginWithCredentials(email, password, this);
    }

    @Override
    public void onPageLoaded() {
    }

    @Override
    public void teardown() {
        this.view = null;
        this.authenticator = null;
        this.navigator = null;
    }

    @Override
    public void onLoginSuccess() {
        view.hideProgress();
        navigator.goToMain();
    }

    @Override
    public void onInvalidCredentials() {
        view.hideProgress();
        view.showInvalidCredentials();
    }

    @Override
    public void onRequestError() {
        view.hideProgress();
        view.showNetworkError();
    }
}
