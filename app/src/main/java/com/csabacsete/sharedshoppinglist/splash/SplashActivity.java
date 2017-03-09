package com.csabacsete.sharedshoppinglist.splash;

import android.app.Activity;
import android.os.Bundle;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.AuthenticatorFirebaseImplementation;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;

public class SplashActivity extends Activity implements SplashContract.View {

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
                new AuthenticatorFirebaseImplementation(),
                new NavigatorIntentImplementation(this)
        );
        presenter.onPageLoaded();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        presenter = null;

        super.onDestroy();
    }
}
