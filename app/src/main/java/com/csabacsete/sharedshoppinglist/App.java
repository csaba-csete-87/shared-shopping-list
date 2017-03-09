package com.csabacsete.sharedshoppinglist;

import android.app.Application;

import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.AuthenticatorFirebaseImplementation;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.data.RepositoryFirebaseImplementation;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;

import timber.log.Timber;

public class App extends Application {

    private static App app;
    private Repository repository;
    private Authenticator authenticator;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public Authenticator getAuthenticator() {
        if (authenticator == null) {
            authenticator = new AuthenticatorFirebaseImplementation();
        }
        return authenticator;
    }

    public Navigator getNavigator() {
        return new NavigatorIntentImplementation(this);
    }

    public Repository getRepository() {
        if (repository == null) {
            repository = new RepositoryFirebaseImplementation();
        }
        return repository;
    }
}
