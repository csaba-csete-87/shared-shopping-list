package com.csabacsete.sharedshoppinglist.about;

import android.os.Bundle;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.drawer.DrawerActivity;

public class AboutActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
