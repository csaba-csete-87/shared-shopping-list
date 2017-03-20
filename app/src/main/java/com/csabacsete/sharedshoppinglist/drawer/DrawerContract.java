package com.csabacsete.sharedshoppinglist.drawer;

public interface DrawerContract {

    interface View {

        void setUserName(String displayName);

        void setUserEmail(String email);

        void setUserImage(String photoUrl);
    }

    interface Presenter {

        void onListsMenuItemClicked();

        void onMyInfoMenuItemClicked();

        void onSettingsMenuItemClicked();

        void onLogoutMenuItemClicked();

        void onPageLoaded();

        void onAboutMenuItemClicked();
    }
}
