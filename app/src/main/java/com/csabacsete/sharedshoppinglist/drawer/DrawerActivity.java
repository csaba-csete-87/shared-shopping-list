package com.csabacsete.sharedshoppinglist.drawer;

import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csabacsete.sharedshoppinglist.BaseActivity;
import com.csabacsete.sharedshoppinglist.R;

public class DrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerContract.View {

    private static final int NAVDRAWER_LAUNCH_DELAY = 250;
    private static final int MAIN_CONTENT_FADEOUT_DURATION = 250;
    private DrawerContract.Presenter presenter;
    private TextView userAccountName;
    private TextView userAccountEmail;
    private ImageView userAccountImage;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Handler handler = new Handler();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.setContentView(layoutResID);

        setupToolbar();
        initDrawerFields();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.onPageLoaded();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // TODO: 3/14/17 refactor this ugly logic into presenter
        final int itemId = item.getItemId();
        if (itemId == getCheckedItemId()) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        if (isSpecialItem(itemId)) {
            goToNavDrawerItem(itemId);
        } else {
            // launch the target Activity after a short delay, to allow the close animation to play
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToNavDrawerItem(itemId);
                }
            }, NAVDRAWER_LAUNCH_DELAY);

            // change the active item on the list so the user can see the item changed
            setSelectedNavDrawerItem(itemId);
            // fade out the main content
            View mainContent = findViewById(R.id.content_main);
            if (mainContent != null) {
                mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
            }
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void setSelectedNavDrawerItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (itemId == item.getItemId()) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
        }
    }

    private void goToNavDrawerItem(int itemId) {
        switch (itemId) {
            case R.id.nav_lists:
                presenter.onListsMenuItemClicked();
                break;
            case R.id.nav_account:
                presenter.onMyInfoMenuItemClicked();
                break;
            case R.id.nav_settings:
                presenter.onSettingsMenuItemClicked();
                break;
            case R.id.nav_about:
                presenter.onAboutMenuItemClicked();
                break;
            case R.id.nav_logout:
                presenter.onLogoutMenuItemClicked();
                break;
            default:
                break;
        }
    }

    private boolean isSpecialItem(int itemId) {
        return itemId == R.id.nav_logout;
    }

    private int getCheckedItemId() {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.isChecked()) {
                return item.getItemId();
            }
        }

        return -1;
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
        getImageLoader().loadInCircle(this, photoUrl, userAccountImage);
    }

    private void initDrawerFields() {
        userAccountName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_account_name);
        userAccountEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_account_email);
        userAccountImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_account_image);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter = new DrawerPresenter(
                this,
                getRepository(),
                getAuthenticator(),
                getNavigator()
        );
    }
}
