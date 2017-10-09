package com.csabacsete.sharedshoppinglist.register

/**
 * Created by ccsete on 10/8/17.
 */
interface RegisterContract {

    interface View {
        fun goToLogin()
    }

    interface Presenter {
        fun onLoginHereClicked()
    }
}