package com.csabacsete.sharedshoppinglist.shopping_lists;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.drawer.DrawerActivity;

import java.util.List;

public class ShoppingListsActivity extends DrawerActivity implements ShoppingListsContract.View, ShoppingListsAdapter.ShoppingListSelectedListener {

    private ShoppingListsContract.Presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView shoppingListsRecycler;
    private FloatingActionButton createListFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);

        setTitle(getString(R.string.my_lists));

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        shoppingListsRecycler = findViewById(R.id.shopping_lists_recycler);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        createListFab = findViewById(R.id.create_list_fab);
        createListFab.setOnClickListener(view -> presenter.onAddShoppingListButtonClicked());
        shoppingListsRecycler.setLayoutManager(layoutManager);
        shoppingListsRecycler.addItemDecoration(new ShoppingListsActivity.ItemDecorationAlbumColumns(24, 2));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        presenter = new ShoppingListsPresenter(
                this,
                getRepository(),
                getNavigator(),
                getAuthenticator()
        );

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onPageLoaded());
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
        ShoppingListsAdapter adapter = new ShoppingListsAdapter(this, shoppingLists, this);
        shoppingListsRecycler.setAdapter(adapter);
    }

    @Override
    public void showGetShoppingListsError() {
        Toast.makeText(this, getString(R.string.error_getting_lists), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyView() {
        Toast.makeText(ShoppingListsActivity.this, "Show empty placeholder", Toast.LENGTH_SHORT).show();
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
