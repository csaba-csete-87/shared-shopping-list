package com.csabacsete.boilerplateproject.login;

import com.csabacsete.boilerplateproject.base.BaseContract;

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
    }

    interface Presenter extends BaseContract.Presenter {

        void login();
    }
}
