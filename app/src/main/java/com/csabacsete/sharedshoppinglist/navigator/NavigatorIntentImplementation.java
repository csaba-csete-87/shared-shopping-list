package com.csabacsete.sharedshoppinglist.navigator;

import android.app.Activity;
import android.content.Intent;

import com.csabacsete.sharedshoppinglist.add_user.AddPeopleActivity;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.login.LoginActivity;
import com.csabacsete.sharedshoppinglist.my_info.MyInfoActivity;
import com.csabacsete.sharedshoppinglist.settings.SettingsActivity;
import com.csabacsete.sharedshoppinglist.shopping_list.ShoppingListActivity;
import com.csabacsete.sharedshoppinglist.shopping_lists.ShoppingListsActivity;
import com.csabacsete.sharedshoppinglist.utils.Constants;

public class NavigatorIntentImplementation implements Navigator {

    private final Activity activity;

    public NavigatorIntentImplementation(Activity a) {
        this.activity = a;
    }

    public void goToLogin() {
        Intent i = new Intent(activity, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
    }

    @Override
    public void goToLists() {
        Intent i = new Intent(activity, ShoppingListsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
//
//        Intent i = new Intent(activity, DrawerActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        activity.startActivity(i);
    }

    @Override
    public void goToMyInfo() {
        Intent i = new Intent(activity, MyInfoActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
    }

    @Override
    public void goToShoppingList(ShoppingList shoppingList) {
        Intent i = new Intent(activity, ShoppingListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(Constants.INTENT_EXTRA_SHOPPING_LIST, shoppingList);
        activity.startActivity(i);
    }

    @Override
    public void goToSettings() {
        Intent i = new Intent(activity, SettingsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
    }

    @Override
    public void goToAddPeople(String shoppingListId) {
        Intent i = new Intent(activity, AddPeopleActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(Constants.INTENT_EXTRA_LIST_ID, shoppingListId);
        activity.startActivity(i);
    }

    @Override
    public void goBack() {
        activity.onBackPressed();
    }
}
