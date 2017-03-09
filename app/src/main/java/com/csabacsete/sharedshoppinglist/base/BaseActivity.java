package com.csabacsete.sharedshoppinglist.base;

import android.support.v7.app.AppCompatActivity;

import com.csabacsete.sharedshoppinglist.App;
import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.Repository;

public class BaseActivity extends AppCompatActivity {

    protected Authenticator getAuthenticator() {
        return App.getInstance().getAuthenticator();
    }

    public Repository getRepository() {
        return App.getInstance().getRepository();
    }
}
