package com.csabacsete.sharedshoppinglist.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.csabacsete.sharedshoppinglist.App;
import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.data.Storage;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;

public class BaseActivity extends AppCompatActivity {

    private Navigator navigator;

    protected Authenticator getAuthenticator() {
        return App.getInstance().getAuthenticator();
    }

    protected Repository getRepository() {
        return App.getInstance().getRepository();
    }

    protected Storage getStorage() {
        return App.getInstance().getStorage();
    }

    protected Navigator getNavigator() {
        if (navigator == null) {
            navigator = new NavigatorIntentImplementation(this);
        }
        return navigator;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        if (!getAuthenticator().isUserLoggedIn()) {
            getAuthenticator().logout();
            navigator.goToLogin();
        }
    }
}
