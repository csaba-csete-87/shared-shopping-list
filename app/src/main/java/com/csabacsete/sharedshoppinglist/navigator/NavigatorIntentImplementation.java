package com.csabacsete.sharedshoppinglist.navigator;

import android.content.Context;
import android.content.Intent;

import com.csabacsete.sharedshoppinglist.login.LoginActivity;
import com.csabacsete.sharedshoppinglist.main.MainActivity;

public class NavigatorIntentImplementation implements Navigator {

    private final Context context;

    public NavigatorIntentImplementation(Context c) {
        this.context = c;
    }

    public void goToLogin() {
        Intent i = new Intent(context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void goToMain() {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
