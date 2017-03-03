package com.csabacsete.sharedshoppinglist.splash;

import android.app.Activity;
import android.os.Bundle;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseContract;
import com.csabacsete.sharedshoppinglist.data.AuthenticatorInMemoryImplementation;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;

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
