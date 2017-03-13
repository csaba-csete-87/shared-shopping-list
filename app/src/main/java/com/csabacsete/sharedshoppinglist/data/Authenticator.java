package com.csabacsete.sharedshoppinglist.data;

public interface Authenticator {

    User getCurrentUser();

    interface CreateAccountCallback {

        void onCreateAccountSuccess(String email);

        void onCreateAccountError(Throwable t);
    }

    void createAccount(String email, String password, CreateAccountCallback callback);

    interface LoginCallback {

        void onLoginSuccess(); // password matches email

        void onInvalidCredentials(); // password does not match existing email

        void onEmailDoesNotExist(String email, String password); // email not found, create user

        void onRequestError(Throwable t); // network or server error
    }

    void loginWithCredentials(String email, String password, LoginCallback callback);

    void loginWithGoogle(String idToken, LoginCallback callback);

    boolean isUserLoggedIn();

    void logout();

    void onDestroy();
}
