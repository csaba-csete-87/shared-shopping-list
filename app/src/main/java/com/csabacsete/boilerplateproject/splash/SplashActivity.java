package com.csabacsete.boilerplateproject.splash;

import android.app.Activity;
import android.os.Bundle;

import com.csabacsete.boilerplateproject.R;
import com.csabacsete.boilerplateproject.base.BaseContract;
import com.csabacsete.boilerplateproject.data.AuthenticatorInMemoryImplementation;
import com.csabacsete.boilerplateproject.navigator.NavigatorIntentImplementation;

public class SplashActivity extends Activity implements BaseContract.View {

    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter = new SplashPresenter(
                this,
                new AuthenticatorInMemoryImplementation(),
                new NavigatorIntentImplementation(this)
        );
        presenter.onPageLoaded();
    }

    @Override
    protected void onDestroy() {
        presenter = null;

        super.onDestroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
