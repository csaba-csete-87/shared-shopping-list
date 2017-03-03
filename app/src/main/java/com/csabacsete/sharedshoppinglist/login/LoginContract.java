package com.csabacsete.sharedshoppinglist.login;

import com.csabacsete.sharedshoppinglist.base.BaseContract;

public interface LoginContract extends BaseContract {

    interface View extends BaseContract.View {

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
    }

    interface Presenter extends BaseContract.Presenter {

        void login();

        void onGooglePlusButtonClicked();

        void onFacebookButtonClicked();

        void onGoogleAccountSignedIn(String idToken);
    }
}
