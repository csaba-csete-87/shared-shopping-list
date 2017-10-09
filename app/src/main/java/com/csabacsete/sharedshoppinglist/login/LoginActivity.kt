package com.csabacsete.sharedshoppinglist.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.csabacsete.sharedshoppinglist.BaseActivity
import com.csabacsete.sharedshoppinglist.R
import com.csabacsete.sharedshoppinglist.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by ccsete on 10/7/17.
 */
class LoginActivity : BaseActivity(), LoginContract.View {

    private val presenter: LoginContract.Presenter = LoginPresenter(
            this,
            authenticator,
            navigator
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
    }

    override fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun setupViews() {
        registerButton.setOnClickListener({ presenter.onRegisterButtonClicked() })
    }

    override fun getEmail(): String {
        return emailEditText.text.toString()
    }

    override fun getPassword(): String {
        return passwordEditText.text.toString()
    }

    override fun clearEmailErrors() {
        emailEditTextWrapper.error = null
    }

    override fun clearPasswordErrors() {
        passwordEditTextWrapper.error = null
    }

    override fun showPasswordRequired() {
        passwordEditTextWrapper.error = getString(R.string.error_field_required)
    }

    override fun showPasswordTooShort() {
        passwordEditTextWrapper.error = getString(R.string.error_password_too_short)
    }

    override fun showEmailRequired() {
        emailEditTextWrapper.error = getString(R.string.error_field_required)
    }

    override fun showEmailInvalid() {
        emailEditTextWrapper.error = getString(R.string.error_invalid_email)
    }

    override fun showInvalidCredentials() {
        emailEditTextWrapper.error = getString(R.string.error_invalid_credentials)
        passwordEditTextWrapper.error = getString(R.string.error_invalid_credentials)
    }

    override fun showNetworkError() {
        Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
    }

    override fun requestGoogleAccount() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}