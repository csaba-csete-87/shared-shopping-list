package com.csabacsete.sharedshoppinglist.about;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.csabacsete.sharedshoppinglist.BuildConfig;
import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.drawer.DrawerActivity;
import com.csabacsete.sharedshoppinglist.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends DrawerActivity {

    @BindView(R.id.content_main)
    ViewGroup content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
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

        content.addView(aboutPage);
    }
}
