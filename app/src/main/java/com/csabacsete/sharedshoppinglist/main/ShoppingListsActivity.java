package com.csabacsete.sharedshoppinglist.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseActivity;
import com.csabacsete.sharedshoppinglist.navigator.DrawerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingListsActivity extends DrawerActivity {


    @OnClick(R.id.fab)
    void onNewListClicked(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        ButterKnife.bind(this);
    }
}
