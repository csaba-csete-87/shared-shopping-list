package com.csabacsete.sharedshoppinglist.register

/**
 * Created by ccsete on 10/8/17.
 */
class RegisterPresenter(private val view: RegisterContract.View) : RegisterContract.Presenter {

    override fun onLoginHereClicked() {
        view.goToLogin()
    }
}