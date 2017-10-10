package com.csabacsete.sharedshoppinglist.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.csabacsete.sharedshoppinglist.BaseActivity
import com.csabacsete.sharedshoppinglist.R
import com.csabacsete.sharedshoppinglist.register.RegisterActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber


/**
 * Created by ccsete on 10/7/17.
 */
class LoginActivity : BaseActivity(), LoginContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private val rcSignIn = 100
    private lateinit var presenter: LoginContract.Presenter
    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()

        presenter = LoginPresenter(this, authenticator, navigator)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
        signIn()
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == rcSignIn) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                Timber.i("" + account)
                firebaseAuthWithGoogle(result.signInAccount)
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                    } else {
                        Timber.e("error")
                    }
                })
    }

    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, rcSignIn)
    }

    override fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun setupViews() {
        registerButton.setOnClickListener({ presenter.onRegisterButtonClicked() })
        socialButtonGoogle.setOnClickListener({ presenter.onGoogleButtonClicked() })
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
        Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show()
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

    override fun onConnected(p0: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}