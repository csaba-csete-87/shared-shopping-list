package com.csabacsete.sharedshoppinglist.shopping_list;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseFragment;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.data.ShoppingListItem;

import java.util.List;

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

    @BindView(R.id.list_items_recycler)
    RecyclerView listItemsRecycler;

    private ShoppingList shoppingList;
    private ListItemsAdapter adapter;

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
        setHasOptionsMenu(true);
        shoppingList = getArguments().getParcelable(ARGUMENT_LIST);

        getActivity().setTitle(getString(R.string.create_edit_list));

        presenter = new ShoppingListPresenter(
                this,
                getAuthenticator(),
                getRepository(),
                getNavigator()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.bind(this, v);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listItemsRecycler.setLayoutManager(layoutManager);
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
    public void onPause() {
        presenter.onFinishedEditing();

        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
        Toast.makeText(getActivity(), getString(R.string.create_list_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSaveError() {
        Toast.makeText(getActivity(), getString(R.string.create_list_error), Toast.LENGTH_SHORT).show();
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
        adapter = new ListItemsAdapter(getActivity(), listItems);
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
        AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom)
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
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getActivity(), android.R.color.primary_text_light));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPossibleAccent));
    }

    @Override
    public void showDeleteListError() {
        Toast.makeText(getActivity(), getString(R.string.could_not_delete_list), Toast.LENGTH_SHORT).show();
    }
}
