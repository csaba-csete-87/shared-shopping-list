package com.csabacsete.sharedshoppinglist.shopping_list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseFragment;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingListFragment extends BaseFragment implements ShoppingListContract.View {

    private static final String ARGUMENT_LIST = "listId";
    private ShoppingListContract.Presenter presenter;

    @BindView(R.id.list_title)
    TextInputEditText editText;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @OnClick(R.id.done_fab)
    void onDoneFabButtonClicked() {
        presenter.onDoneClicked();
    }

    public ShoppingListFragment() {
    }

    public static Fragment newInstance(ShoppingList shoppingList) {
        Fragment f = new ShoppingListFragment();
        Bundle b = new Bundle();
        b.putParcelable(ARGUMENT_LIST, shoppingList);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ShoppingListPresenter(
                this,
                getAuthenticator(),
                getRepository(),
                new NavigatorIntentImplementation(getActivity())
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.bind(this, v);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onPageLoaded();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onPageLoaded();
    }

    @Override
    public String getShoppingListTitle() {
        return editText.getText().toString();
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSaveSuccess() {
        Toast.makeText(getActivity(), getString(R.string.create_list_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSaveError() {
        Toast.makeText(getActivity(), getString(R.string.create_list_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public ShoppingList getShoppingList() {
        return getArguments().getParcelable(ARGUMENT_LIST);
    }

    @Override
    public void setTitle(String title) {
        editText.setText(title);
    }

    @Override
    public void showGetListError() {
        Toast.makeText(getActivity(), getString(R.string.could_not_get_list), Toast.LENGTH_SHORT).show();
    }
}
