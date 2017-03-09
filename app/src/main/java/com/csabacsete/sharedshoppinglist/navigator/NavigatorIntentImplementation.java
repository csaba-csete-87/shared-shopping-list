package com.csabacsete.sharedshoppinglist.navigator;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.login.LoginActivity;
import com.csabacsete.sharedshoppinglist.main.MainActivity;
import com.csabacsete.sharedshoppinglist.my_info.MyInfoFragment;
import com.csabacsete.sharedshoppinglist.settings.SettingsActivity;
import com.csabacsete.sharedshoppinglist.shopping_list.ShoppingListFragment;
import com.csabacsete.sharedshoppinglist.shopping_lists.ShoppingListsFragment;

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

    @Override
    public void goToLists() {
        FragmentManager fm = ((Activity) context).getFragmentManager();

        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.content_main, ShoppingListsFragment.newInstance(), ShoppingListsFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void goToMyInfo() {
        FragmentManager fm = ((Activity) context).getFragmentManager();

        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.content_main, MyInfoFragment.newInstance(), MyInfoFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void goToShoppingList(ShoppingList shoppingList) {
        FragmentManager fm = ((Activity) context).getFragmentManager();

        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.content_main, ShoppingListFragment.newInstance(shoppingList), ShoppingListFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void goToSettings() {
        Intent i = new Intent(context, SettingsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
