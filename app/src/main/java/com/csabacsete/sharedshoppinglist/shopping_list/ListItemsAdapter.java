package com.csabacsete.sharedshoppinglist.shopping_list;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

public class ListItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_LIST_ITEM = 0;
    private static final int ITEM_VIEW_TYPE_ADD_NEW = 1;

    private final Context context;
    private final List<ShoppingListItem> listItems;
    private final ItemAddedListener listener;

    public ListItemsAdapter(Context context, List<ShoppingListItem> listItems, ItemAddedListener listener) {
        this.context = context;
        this.listener = listener;
        if (listItems == null) {
            this.listItems = new ArrayList<>();
        } else {
            this.listItems = listItems;
        }
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
            if (position == getItemCount() - 2) {
                viewHolder.itemName.requestFocus();
            }

            if (item.isBought()) {
                viewHolder.itemName.setPaintFlags(viewHolder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.itemName.setTextColor(ContextCompat.getColor(context, android.R.color.tertiary_text_light));
                viewHolder.checkBox.setButtonTintList(ContextCompat.getColorStateList(context, android.R.color.tertiary_text_light));
            } else {
                viewHolder.itemName.setPaintFlags(0);
                viewHolder.itemName.setTextColor(ContextCompat.getColor(context, android.R.color.secondary_text_light));
                viewHolder.checkBox.setButtonTintList(ContextCompat.getColorStateList(context, R.color.colorPrimary));
            }
        }
    }

    public void addListItem() {
        int position = listItems.size();
        listItems.add(position, new ShoppingListItem());
        notifyItemInserted(position);
        listener.onItemAdded(getItemCount() - 1);
        notifyItemChanged(getItemCount() - 2);
    }

    private void removeListItem(int position) {
        try {
            listItems.remove(position);
            notifyItemRemoved(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size() + 1;
    }

    private void setItemChecked(int position, boolean isChecked) {
        listItems.get(position).setBought(isChecked);
        notifyItemChanged(position);
    }

    public List<ShoppingListItem> getItems() {
        return listItems;
    }

    class AddNewRowViewHolder extends RecyclerView.ViewHolder {

        public AddNewRowViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.add_item).setOnClickListener(view -> addListItem());
        }
    }

    class ShoppingListItemViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        EditText itemName;

        public ShoppingListItemViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.item_bought).setOnClickListener(view -> setItemChecked(getLayoutPosition(), checkBox.isChecked()));
            itemView.findViewById(R.id.delete_item).setOnClickListener(view -> removeListItem(getAdapterPosition()));
            checkBox = itemView.findViewById(R.id.item_bought);
            itemName = itemView.findViewById(R.id.item_name);
            itemName.setOnEditorActionListener((textView, actionId, event) -> {
                        if (event == null && (actionId == context.getResources().getInteger(R.integer.action_enter) || actionId == EditorInfo.IME_ACTION_NEXT)) {
                            addListItem();
                            return true;
                        }
                        return false;
                    }
            );
            itemName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    listItems.get(getLayoutPosition()).setName(editable.toString());
                }
            });
        }
    }

    interface ItemAddedListener {

        void onItemAdded(int position);
    }
}