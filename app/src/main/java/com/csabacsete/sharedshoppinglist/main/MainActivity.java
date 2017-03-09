package com.csabacsete.sharedshoppinglist.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseActivity;
import com.csabacsete.sharedshoppinglist.data.AuthenticatorFirebaseImplementation;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    TextView userAccountName;
    TextView userAccountEmail;
    ImageView userAccountImage;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initDrawerFields();
        setSupportActionBar(toolbar);
        setupDrawerToggle();
        navigationView.setNavigationItemSelectedListener(this);

        presenter = new MainPresenter(
                this,
                new AuthenticatorFirebaseImplementation(),
                new NavigatorIntentImplementation(this)
        );
    }

    private void initDrawerFields() {
        userAccountName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_account_name);
        userAccountEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_account_email);
        userAccountImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_account_image);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.onPageLoaded();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_lists:
                presenter.onListsMenuItemClicked();
                break;
            case R.id.nav_account:
                presenter.onMyInfoMenuItemClicked();
                break;
            case R.id.nav_settings:
                presenter.onSettingsMenuItemClicked();
                break;
            case R.id.nav_logout:
                presenter.onLogoutMenuItemClicked();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        presenter = null;

        super.onDestroy();
    }

    private void setupDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void setUserName(String displayName) {
        userAccountName.setText(displayName);
    }

    @Override
    public void setUserEmail(String email) {
        userAccountEmail.setText(email);
    }

    @Override
    public void setUserImage(String photoUrl) {
    }

    @Override
    public int getContainerId() {
        return R.id.content_main;
    }
}
