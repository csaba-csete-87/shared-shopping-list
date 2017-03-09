package com.csabacsete.sharedshoppinglist.shopping_lists;


import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseFragment;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.navigator.NavigatorIntentImplementation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingListsFragment extends BaseFragment implements ShoppingListsContract.View, ShoppingListsAdapter.ShoppingListSelectedListener {

    private ShoppingListsContract.Presenter presenter;

    @BindView(R.id.shopping_lists_recycler)
    RecyclerView shoppingListsRecycler;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.empty_list_placeholder_container)
    View emptyPlaceholder;

    @OnClick(R.id.create_list_fab)
    void onNewListClicked(View view) {
        presenter.onAddShoppingListButtonClicked();
    }

    public ShoppingListsFragment() {
    }

    public static Fragment newInstance() {
        return new ShoppingListsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ShoppingListsPresenter(
                this,
                getRepository(),
                new NavigatorIntentImplementation(getActivity()),
                getAuthenticator()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        ButterKnife.bind(this, v);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        shoppingListsRecycler.setLayoutManager(layoutManager);
        shoppingListsRecycler.addItemDecoration(new ItemDecorationAlbumColumns(24, 2));
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
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showShoppingLists(List<ShoppingList> shoppingLists) {
        ShoppingListsAdapter adapter = new ShoppingListsAdapter(getActivity(), shoppingLists, this);
        shoppingListsRecycler.setAdapter(adapter);
    }

    @Override
    public void showGetShoppingListsError() {
        Toast.makeText(getActivity(), getString(R.string.error_getting_lists), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyView() {
        emptyPlaceholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShoppingListSelected(ShoppingList shoppingList) {
        presenter.onShoppingListSelected(shoppingList);
    }

    public class ItemDecorationAlbumColumns extends RecyclerView.ItemDecoration {

        private int mSizeGridSpacingPx;
        private int mGridSize;

        private boolean mNeedLeftSpacing = false;

        public ItemDecorationAlbumColumns(int gridSpacingPx, int gridSize) {
            mSizeGridSpacingPx = gridSpacingPx;
            mGridSize = gridSize;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int frameWidth = (int) ((parent.getWidth() - (float) mSizeGridSpacingPx * (mGridSize - 1)) / mGridSize);
            int padding = parent.getWidth() / mGridSize - frameWidth;
            int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
            if (itemPosition < mGridSize) {
                outRect.top = 0;
            } else {
                outRect.top = mSizeGridSpacingPx;
            }
            if (itemPosition % mGridSize == 0) {
                outRect.left = 0;
                outRect.right = padding;
                mNeedLeftSpacing = true;
            } else if ((itemPosition + 1) % mGridSize == 0) {
                mNeedLeftSpacing = false;
                outRect.right = 0;
                outRect.left = padding;
            } else if (mNeedLeftSpacing) {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx - padding;
                if ((itemPosition + 2) % mGridSize == 0) {
                    outRect.right = mSizeGridSpacingPx - padding;
                } else {
                    outRect.right = mSizeGridSpacingPx / 2;
                }
            } else if ((itemPosition + 2) % mGridSize == 0) {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx / 2;
                outRect.right = mSizeGridSpacingPx - padding;
            } else {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx / 2;
                outRect.right = mSizeGridSpacingPx / 2;
            }
            outRect.bottom = 0;
        }
    }
}
