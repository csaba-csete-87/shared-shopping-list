package com.csabacsete.sharedshoppinglist.login;

import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;
import com.csabacsete.sharedshoppinglist.utils.Constants;
import com.csabacsete.sharedshoppinglist.utils.StringUtils;

public class LoginPresenter implements LoginContract.Presenter, Authenticator.LoginCallback, Authenticator.CreateAccountCallback {

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
    public void onGooglePlusButtonClicked() {
        view.requestGoogleAccount();
    }

    @Override
    public void onFacebookButtonClicked() {

    }

    @Override
    public void onGoogleAccountSignedIn(String idToken) {
        view.showProgress();
        authenticator.loginWithGoogle(idToken, this);
    }

    @Override
    public void onDestroy() {
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
    public void onEmailDoesNotExist(String email, String password) {
        authenticator.createAccount(email, password, this);
    }

    @Override
    public void onRequestError(Throwable t) {
        view.hideProgress();
        view.showNetworkError();
    }

    @Override
    public void onCreateAccountSuccess() {
        view.hideProgress();
        navigator.goToMain();
    }

    @Override
    public void onCreateAccountError(Throwable t) {
        view.hideProgress();
        view.showCreateAccountError();
    }
}
