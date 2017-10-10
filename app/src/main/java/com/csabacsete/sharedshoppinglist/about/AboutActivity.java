package com.csabacsete.sharedshoppinglist.about;

import android.os.Bundle;
import android.view.View;

import com.csabacsete.sharedshoppinglist.BuildConfig;
import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.drawer.DrawerActivity;
import com.csabacsete.sharedshoppinglist.utils.Constants;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.about));

        Element versionElement = new Element();
        versionElement.setTitle(String.format(Constants.VERSION_FORMAT, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, BuildConfig.FLAVOR));
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher)
                .addItem(versionElement)
                .addGroup(getString(R.string.connect_with_us))
                .addEmail(getString(R.string.developer_email))
                .create();

        setContentView(aboutPage);
    }
}
