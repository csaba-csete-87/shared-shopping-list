package com.csabacsete.sharedshoppinglist.splash;

import android.os.Bundle;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseActivity;

public class SplashActivity extends BaseActivity implements SplashContract.View {

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
                getAuthenticator(),
                getNavigator()
        );
        presenter.onPageLoaded();
    }

    @Override
    protected void onDestroy() {
        presenter = null;

        super.onDestroy();
    }
}
