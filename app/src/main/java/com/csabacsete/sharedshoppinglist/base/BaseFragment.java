package com.csabacsete.sharedshoppinglist.base;


import android.app.Fragment;

import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.Repository;

public class BaseFragment extends Fragment {

    protected Authenticator getAuthenticator() {
        return ((BaseActivity) getActivity()).getAuthenticator();
    }

    protected Repository getRepository() {
        return ((BaseActivity) getActivity()).getRepository();
    }

}
