package com.csabacsete.sharedshoppinglist.my_info;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csabacsete.sharedshoppinglist.R;

public class MyInfoFragment extends Fragment {

    public MyInfoFragment() {
    }

    public static Fragment newInstance() {
        return new MyInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_info, container, false);
    }
}
