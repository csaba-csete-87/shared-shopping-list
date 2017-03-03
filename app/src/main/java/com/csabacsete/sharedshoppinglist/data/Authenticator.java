package com.csabacsete.sharedshoppinglist.data;

public interface Authenticator {

    interface LoginCallback {

        void onLoginSuccess();

        void onInvalidCredentials();

        void onRequestError();
    }

    void loginWithCredentials(String email, String password, LoginCallback callback);

    interface WaitCallback {

        void onWaitFinished();
    }

    void wait(int ms, WaitCallback callback);

    boolean isUserLoggedIn();
}
