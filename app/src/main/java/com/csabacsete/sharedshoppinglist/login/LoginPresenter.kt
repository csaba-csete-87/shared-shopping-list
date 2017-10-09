package com.csabacsete.sharedshoppinglist.login

import com.csabacsete.sharedshoppinglist.data.Authenticator
import com.csabacsete.sharedshoppinglist.navigator.Navigator
import com.csabacsete.sharedshoppinglist.utils.Constants
import com.csabacsete.sharedshoppinglist.utils.StringUtils

/**
 * Created by ccsete on 10/8/17.
 */
class LoginPresenter(private val view: LoginContract.View,
                     private val authenticator: Authenticator,
                     private val navigator: Navigator) : LoginContract.Presenter, Authenticator.LoginCallback {

    override fun onRegisterButtonClicked() {
        view.goToRegister()
    }

    override fun login() {
        val email = view.getEmail()
        val password = view.getPassword()

        view.clearEmailErrors()
        view.clearPasswordErrors()

        if (StringUtils.isEmpty(password)) {
            view.showPasswordRequired()
            return
        }
        if (!StringUtils.hasLength(password, Constants.MIN_PASSWORD_LENGTH)) {
            view.showPasswordTooShort()
            return
        }
        if (StringUtils.isEmpty(email)) {
            view.showEmailRequired()
            return
        }
        if (!StringUtils.isValidEmail(email)) {
            view.showEmailInvalid()
            return
        }

        view.showProgress()
        authenticator.loginWithCredentials(email, password, this)
    }

    override fun onGooglePlusButtonClicked() {
        view.requestGoogleAccount()
    }

    override fun onFacebookButtonClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGoogleAccountSignedIn(idToken: String) {
        view.showProgress()
        authenticator.loginWithGoogle(idToken, this)
    }

    override fun onLoginSuccess() {
        view.hideProgress()
        navigator.goToLists()
    }

    override fun onInvalidCredentials() {
        view.hideProgress()
        view.showInvalidCredentials()
    }

    override fun onEmailDoesNotExist(email: String?, password: String?) {
        view.hideProgress()
        TODO("show error, move functionality to register screen")
    }

    override fun onRequestError(t: Throwable?) {
        view.hideProgress()
        view.showNetworkError()
    }
}