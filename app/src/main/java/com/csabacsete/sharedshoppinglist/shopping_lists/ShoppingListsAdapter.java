package com.csabacsete.sharedshoppinglist.shopping_lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ShoppingListViewHolder> {

    private final Context context;
    private final List<ShoppingList> shoppingLists;
    private final ShoppingListSelectedListener listener;

    public ShoppingListsAdapter(Context context, List<ShoppingList> shoppingLists, ShoppingListSelectedListener listener) {
        this.context = context;
        this.shoppingLists = shoppingLists;
        this.listener = listener;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShoppingListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shopping_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        holder.shoppingListTitle.setText(shoppingLists.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return shoppingLists != null ? shoppingLists.size() : 0;
    }

    class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shopping_list_title)
        TextView shoppingListTitle;

        @OnClick(R.id.root)
        void onItemSelected() {
            listener.onShoppingListSelected(shoppingLists.get(getLayoutPosition()));
        }

        public ShoppingListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    interface ShoppingListSelectedListener {

        void onShoppingListSelected(ShoppingList listId);
    }
}
