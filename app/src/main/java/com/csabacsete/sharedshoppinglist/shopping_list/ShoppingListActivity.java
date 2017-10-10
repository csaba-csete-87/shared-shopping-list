package com.csabacsete.sharedshoppinglist.shopping_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.data.ShoppingListItem;
import com.csabacsete.sharedshoppinglist.drawer.DrawerActivity;
import com.csabacsete.sharedshoppinglist.utils.Constants;

import java.util.List;

public class ShoppingListActivity extends DrawerActivity implements ShoppingListContract.View, ListItemsAdapter.ItemAddedListener {

    private ShoppingListContract.Presenter presenter;

    TextInputEditText editText;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView listItemsRecycler;

    private ShoppingList shoppingList;
    private ListItemsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        editText = findViewById(R.id.list_title);
        findViewById(R.id.done_fab).setOnClickListener(view -> presenter.onDoneClicked());

        shoppingList = getIntent().getParcelableExtra(Constants.INTENT_EXTRA_SHOPPING_LIST);

        this.setTitle(getString(R.string.create_edit_list));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listItemsRecycler.setLayoutManager(layoutManager);
        listItemsRecycler = findViewById(R.id.list_items_recycler);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onPageLoaded());

        presenter = new ShoppingListPresenter(
                this,
                getAuthenticator(),
                getRepository(),
                getNavigator()
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onPageLoaded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_list:
                presenter.onDeleteListClicked();
                break;
            case R.id.action_add_people:
                presenter.onAddPeopleClicked();
        }
        return super.onOptionsItemSelected(item);
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
        Toast.makeText(this, getString(R.string.create_list_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSaveError() {
        Toast.makeText(this, getString(R.string.create_list_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    @Override
    public void setTitle(String title) {
        editText.setText(title);
    }

    @Override
    public void setItems(List<ShoppingListItem> listItems) {
        adapter = new ListItemsAdapter(this, listItems, this);
        listItemsRecycler.setAdapter(adapter);
    }

    @Override
    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public List<ShoppingListItem> getShoppingListItems() {
        return adapter.getItems();
    }

    @Override
    public void showConfirmDeleteListDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.AlertDialogCustom)
                .setTitle(R.string.confirm_delete)
                .setMessage(R.string.are_you_sure_you_want_to_delete_this_list)
                .setNegativeButton(R.string.not_really, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.yes_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onConfirmDeleteShoppingList();
                    }
                })
                .show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, android.R.color.primary_text_light));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorPossibleAccent));
    }

    @Override
    public void showDeleteListError() {
        Toast.makeText(this, getString(R.string.could_not_delete_list), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemAdded(int position) {
        listItemsRecycler.scrollToPosition(position);
    }
}
