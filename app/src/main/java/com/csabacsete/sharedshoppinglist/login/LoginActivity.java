package com.csabacsete.sharedshoppinglist.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseActivity;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class LoginActivity extends BaseActivity implements LoginContract.View, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.login_form)
    View loginForm;

    @BindView(R.id.login_progress)
    View progress;

    @BindInt(android.R.integer.config_shortAnimTime)
    int shortAnimTime;

    @BindString(R.string.error_field_required)
    String errorFieldRequired;

    @BindString(R.string.error_password_too_short)
    String errorPasswordTooShort;

    @BindString(R.string.error_invalid_email)
    String errorEmailInvalid;

    @BindString(R.string.error_invalid_credentials)
    String errorInvalidCredentials;

    @BindString(R.string.error_network)
    String errorNetwork;

    @BindString(R.string.error_create_account)
    String errorCreateAccount;

    @BindString(R.string.error_google_sign_in)
    String errorGoogleSignIn;

    private LoginContract.Presenter presenter;
    private GoogleApiClient googleApiClient;

    @OnClick(R.id.email_sign_in_button)
    void onSignInButtonClicked() {
        presenter.login();
    }

    @OnClick(R.id.google_plus_button)
    void onGooglePlusButtonClicked() {
        presenter.onGooglePlusButtonClicked();
    }

    @OnClick(R.id.facebook_button)
    void onFacebookButtonClicked() {
        presenter.onFacebookButtonClicked();
    }

    @OnEditorAction(R.id.password)
    boolean onTextFieldAction(int id) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
            presenter.login();
            return false;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(
                this,
                getAuthenticator(),
                new NavigatorIntentImplementation(this)
        );

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                presenter.onGoogleAccountSignedIn(account.getIdToken());
            } else {
                Toast.makeText(LoginActivity.this, errorGoogleSignIn, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showProgress() {
        loginForm.setVisibility(View.GONE);
        loginForm.animate().setDuration(shortAnimTime).alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loginForm.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);
        progress.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progress.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgress() {
        loginForm.setVisibility(View.VISIBLE);
        loginForm.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loginForm.setVisibility(View.VISIBLE);
            }
        });

        progress.setVisibility(View.GONE);
        progress.animate().setDuration(shortAnimTime).alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public String getEmail() {
        return email.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void clearEmailErrors() {
        email.setError(null);
    }

    @Override
    public void clearPasswordErrors() {
        password.setError(null);
    }

    @Override
    public void showPasswordRequired() {
        password.setError(errorFieldRequired);
    }

    @Override
    public void showPasswordTooShort() {
        password.setError(errorPasswordTooShort);
    }

    @Override
    public void showEmailRequired() {
        email.setError(errorFieldRequired);
    }

    @Override
    public void showEmailInvalid() {
        email.setError(errorEmailInvalid);
    }

    @Override
    public void showInvalidCredentials() {
        Toast.makeText(LoginActivity.this, errorInvalidCredentials, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(LoginActivity.this, errorNetwork, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCreateAccountError() {
        Toast.makeText(LoginActivity.this, errorCreateAccount, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestGoogleAccount() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
}
