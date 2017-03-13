package com.csabacsete.sharedshoppinglist.drawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseActivity;

import butterknife.ButterKnife;

public class DrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerContract.View {

    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    private TextView userAccountName;
    private TextView userAccountEmail;
    private ImageView userAccountImage;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerContract.Presenter presenter;

    public DrawerActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        setupDrawerToggle();
        initDrawerFields();

        presenter = new DrawerPresenter(
                this,
                getRepository(),
                getAuthenticator(),
                getNavigator()
        );
    }

    private void initDrawerFields() {
        userAccountName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_account_name);
        userAccountEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_account_email);
        userAccountImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_account_image);
    }

    private void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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
        presenter = null;

        super.onDestroy();
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
        Glide.with(this)
                .load(photoUrl)
                .into(userAccountImage);
    }
}
