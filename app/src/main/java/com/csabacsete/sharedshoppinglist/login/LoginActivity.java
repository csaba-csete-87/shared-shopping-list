package com.csabacsete.sharedshoppinglist.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.AuthenticatorInMemoryImplementation;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class LoginActivity extends Activity implements LoginContract.View {

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;
    private LoginContract.Presenter presenter;

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

    @OnClick(R.id.email_sign_in_button)
    void onSignInButtonClicked() {
        presenter.login();
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
                new AuthenticatorInMemoryImplementation(),
                new NavigatorIntentImplementation(this)
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.onPageLoaded();
    }

    @Override
    protected void onDestroy() {
        presenter.teardown();

        super.onDestroy();
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
}
