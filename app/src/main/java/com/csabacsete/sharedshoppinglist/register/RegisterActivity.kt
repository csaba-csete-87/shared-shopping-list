package com.csabacsete.sharedshoppinglist.register

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.csabacsete.sharedshoppinglist.R
import com.csabacsete.sharedshoppinglist.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private val presenter: RegisterContract.Presenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setupViews()
    }

    private fun setupViews() {
        buttonLoginHere.setOnClickListener({ presenter.onLoginHereClicked() })
    }

    override fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
