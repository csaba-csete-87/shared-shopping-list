package com.csabacsete.sharedshoppinglist.shopping_lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.ShoppingList;
import com.csabacsete.sharedshoppinglist.data.ShoppingListItem;

import java.util.List;

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
        ShoppingList shoppingList = shoppingLists.get(position);
        holder.shoppingListTitle.setText(shoppingList.getTitle());
        StringBuilder builder = new StringBuilder();
        for (ShoppingListItem shoppingListItem : shoppingList.getListItems()) {
            builder.append(shoppingListItem.getName()).append("\n");
        }
        holder.shoppingListPreview.setText(builder.toString());
    }

    @Override
    public int getItemCount() {
        return shoppingLists != null ? shoppingLists.size() : 0;
    }

    class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        private final View root;
        private final TextView shoppingListTitle;
        private final TextView shoppingListPreview;

        public ShoppingListViewHolder(View itemView) {
            super(itemView);
            shoppingListTitle = itemView.findViewById(R.id.shopping_list_title);
            shoppingListPreview = itemView.findViewById(R.id.shopping_list_preview);
            root = itemView.findViewById(R.id.root);
            root.setOnClickListener(v -> listener.onShoppingListSelected(shoppingLists.get(getLayoutPosition())));
        }
    }

    interface ShoppingListSelectedListener {

        void onShoppingListSelected(ShoppingList listId);
    }
}
