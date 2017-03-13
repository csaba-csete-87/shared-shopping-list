package com.csabacsete.sharedshoppinglist.shopping_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.ShoppingListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ListItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_LIST_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_ADD_NEW = 1;

    private final Context context;
    private final List<ShoppingListItem> listItems;

    public ListItemsAdapter(Context context, List<ShoppingListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_ADD_NEW:
                return new ListItemsAdapter.AddNewRowViewHolder(LayoutInflater.from(context).inflate(R.layout.item_add_new_item, parent, false));
            case ITEM_VIEW_TYPE_LIST_ITEM:
            default:
                return new ListItemsAdapter.ShoppingListItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shopping_list_item, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < listItems.size()) {
            return ITEM_VIEW_TYPE_LIST_ITEM;
        } else {
            return ITEM_VIEW_TYPE_ADD_NEW;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_LIST_ITEM) {
            ShoppingListItemViewHolder viewHolder = (ShoppingListItemViewHolder) holder;
            ShoppingListItem item = listItems.get(position);
            viewHolder.checkBox.setChecked(item.isBought());
            viewHolder.itemName.setText(item.getName());
            if (position == listItems.size() - 1) {
                viewHolder.itemName.requestFocus();
            }
        }
    }

    public void addListItem() {
        listItems.add(listItems.size(), new ShoppingListItem());
        notifyDataSetChanged();
    }

    private void removeListItem(int position) {
        listItems.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listItems != null ? listItems.size() + 1 : 1;
    }

    public List<ShoppingListItem> getItems() {
        return listItems;
    }

    class AddNewRowViewHolder extends RecyclerView.ViewHolder {

        @OnClick(R.id.add_item)
        void onAddItemClicked() {
            addListItem();
        }

        public AddNewRowViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    class ShoppingListItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_bought)
        CheckBox checkBox;

        @BindView(R.id.item_name)
        EditText itemName;

        @OnClick(R.id.delete_item)
        void onDeleteRowButtonClicked() {
            removeListItem(getAdapterPosition());
        }

        @OnCheckedChanged(R.id.item_bought)
        void onCheckChanged(boolean isChecked) {
            listItems.get(getLayoutPosition()).setBought(isChecked);
        }

        @OnTextChanged(R.id.item_name)
        void onItemTextChanged(Editable editable) {
            listItems.get(getLayoutPosition()).setName(editable.toString());
        }

        public ShoppingListItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }
}