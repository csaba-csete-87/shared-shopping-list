package com.csabacsete.sharedshoppinglist.login;

public interface LoginContract {

    interface View {

        String getEmail();

        String getPassword();

        void clearEmailErrors();

        void clearPasswordErrors();

        void showPasswordRequired();

        void showPasswordTooShort();

        void showEmailRequired();

        void showEmailInvalid();

        void showInvalidCredentials();

        void showNetworkError();

        void showCreateAccountError();

        void requestGoogleAccount();

        void showProgress();

        void hideProgress();
    }

    interface Presenter {

        void login();

        void onGooglePlusButtonClicked();

        void onFacebookButtonClicked();

        void onGoogleAccountSignedIn(String idToken);
    }
}
