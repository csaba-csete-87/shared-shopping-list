package com.csabacsete.sharedshoppinglist.main;

public interface MainContract {

    interface View {

        void setUserName(String displayName);

        void setUserEmail(String email);

        void setUserImage(String photoUrl);

        int getContainerId();
    }

    interface Presenter {

        void onListsMenuItemClicked();

        void onMyInfoMenuItemClicked();

        void onSettingsMenuItemClicked();

        void onLogoutMenuItemClicked();

        void onDestroy();

        void onPageLoaded();
    }
}
