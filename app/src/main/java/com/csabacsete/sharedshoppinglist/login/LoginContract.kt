package com.csabacsete.sharedshoppinglist.login

/**
 * Created by ccsete on 10/8/17.
 */
class LoginContract {

    interface View {

        fun getEmail(): String

        fun getPassword(): String

        fun clearEmailErrors()

        fun clearPasswordErrors()

        fun showPasswordRequired()

        fun showPasswordTooShort()

        fun showEmailRequired()

        fun showEmailInvalid()

        fun showInvalidCredentials()

        fun showNetworkError()

        fun requestGoogleAccount()

        fun showProgress()

        fun hideProgress()

        fun goToRegister()
    }

    interface Presenter {

        fun login()

        fun onGooglePlusButtonClicked()

        fun onFacebookButtonClicked()

        fun onGoogleAccountSignedIn(idToken: String)

        fun onRegisterButtonClicked()
    }
}