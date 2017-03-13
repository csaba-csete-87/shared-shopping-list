package com.csabacsete.sharedshoppinglist.base;


import android.app.Fragment;

import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.data.Storage;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;

public class BaseFragment extends Fragment {

    private Navigator navigator;

    protected Authenticator getAuthenticator() {
        return ((BaseActivity) getActivity()).getAuthenticator();
    }

    protected Repository getRepository() {
        return ((BaseActivity) getActivity()).getRepository();
    }

    protected Navigator getNavigator() {
        if (navigator == null) {
            navigator = new NavigatorIntentImplementation(getActivity());
        }
        return navigator;
    }

    protected Storage getStorage() {
        return ((BaseActivity) getActivity()).getStorage();
    }

}
