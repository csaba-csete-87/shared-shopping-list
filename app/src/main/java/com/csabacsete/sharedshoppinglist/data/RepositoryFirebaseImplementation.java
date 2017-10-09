package com.csabacsete.sharedshoppinglist.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryFirebaseImplementation implements Repository {


    public RepositoryFirebaseImplementation() {
    }

    @Override
    public void getUserById(String userId, final GetUserCallback callback) {
        FirebaseDatabase.getInstance().getReference().child("users").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onGetUserSuccess(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onGetUserError(databaseError.toException());
            }
        });
    }

    @Override
    public void deleteShoppingList(ShoppingList shoppingList, DeleteShoppingListCallback callback) {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("shoppingLists").child(shoppingList.getId()).setValue(null);
            for (Map.Entry<String, Boolean> entry : shoppingList.getUsers().entrySet()) {
                String userId = entry.getKey();
                ref.child("users").child(userId).child("lists").child(shoppingList.getId()).setValue(null);
            }
        } catch (Exception e) {
            callback.onDeleteShoppingListError(e);
        }
        callback.onDeleteShoppingListSuccess();
    }

    @Override
    public void savePendingInvitations(List<Pending> pendingList, final SavePendingCallback callback) {
        try {
            for (Pending pending : pendingList) {
                FirebaseDatabase.getInstance().getReference().child("pending").push().setValue(pending);
            }
            callback.onSavePendingSuccess();
        } catch (Exception e) {
            callback.onSavePendingError(e);
        }
    }

    @Override
    public void saveUser(final User user, final CreateUserCallback callback) {
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getId()).setValue(user, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                callback.onSaveUserError(databaseError.toException());
            } else {
                callback.onSaveUserSuccess(user);
            }
        });
    }

    @Override
    public void getShoppingListsByUserId(String userId, final GetShoppingListsCallback callback) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userListsRef = ref.child("users").child(userId).child("lists");
        userListsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Boolean> userLists = (HashMap<String, Boolean>) dataSnapshot.getValue();
                final List<ShoppingList> shoppingLists = new ArrayList<>();
                if (userLists != null) {
                    final List<String> shoppingListIds = new ArrayList<>();
                    for (Map.Entry<String, Boolean> entry : userLists.entrySet()) {
                        final String shoppingListId = entry.getKey();
                        shoppingListIds.add(shoppingListId);
                    }

                    final int expected = shoppingListIds.size();
                    final int[] counter = {0};
                    for (String shoppingListId : shoppingListIds) {
                        ref.child("shoppingLists").child(shoppingListId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                shoppingLists.add(dataSnapshot.getValue(ShoppingList.class));
                                counter[0]++;
                                if (counter[0] >= expected) {
                                    callback.onGetShoppingListsSuccess(shoppingLists);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                counter[0]++;
                                if (counter[0] >= expected) {
                                    callback.onGetShoppingListsSuccess(shoppingLists);
                                }
                            }
                        });
                    }
                } else {
                    callback.onGetShoppingListsSuccess(shoppingLists);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onGetShoppingListsError(databaseError.toException());
            }
        });
    }

    @Override
    public void saveShoppingList(final ShoppingList shoppingList, final SaveShoppingListCallback callback) {
        // TODO: 3/10/17 investigate transactions
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("shoppingLists").child(shoppingList.getId()).setValue(shoppingList);
            for (Map.Entry<String, Boolean> entry : shoppingList.getUsers().entrySet()) {
                String userId = entry.getKey();
                boolean isOwner = entry.getValue();
                ref.child("users").child(userId).child("lists").child(shoppingList.getId()).setValue(isOwner);
            }
        } catch (Exception e) {
            callback.onSaveShoppingListError(e);
        }
        callback.onSaveShoppingListSuccess();
    }
}
