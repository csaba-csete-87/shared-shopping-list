package com.csabacsete.sharedshoppinglist.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RepositoryFirebaseImplementation implements Repository {

    private final DatabaseReference databaseReference;
    private final DatabaseReference accessReference;
    private final DatabaseReference listsReference;

    public RepositoryFirebaseImplementation() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        accessReference = FirebaseDatabase.getInstance().getReference(Access.class.getSimpleName());
        listsReference = FirebaseDatabase.getInstance().getReference(ShoppingList.class.getSimpleName());
    }

    @Override
    public void createShoppingList(final ShoppingList shoppingList, final Access access, final SaveShoppingListCallback callback) {
        databaseReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                try {
                    mutableData.child(ShoppingList.class.getSimpleName()).child(shoppingList.getId()).setValue(shoppingList);
                    mutableData.child(Access.class.getSimpleName()).child(access.getId()).setValue(access);

                    return Transaction.success(mutableData);
                } catch (Exception e) {
                    return Transaction.abort();
                }
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                if (databaseError != null) {
                    callback.onSaveShoppingListError(databaseError.toException());
                } else {
                    callback.onSaveShoppingListSuccess();
                }
            }
        });
    }

    @Override
    public void getShoppingListById(final String listId, final GetShoppingListCallback callback) {
        listsReference
                .orderByChild("id")
                .startAt(listId).endAt(listId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        callback.onGetShoppingListSuccess(dataSnapshot.getValue(ShoppingList.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onGetShoppingListError(databaseError.toException());
                    }
                });

    }

    @Override
    public void getShoppingListsByUserId(String userId, final GetShoppingListsCallback callback) {
        // TODO: 3/9/17 maybe do these in a transaction
        accessReference
                .orderByChild("userId")
                .startAt(userId).endAt(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Access> accessList = new ArrayList<>();
                        if (dataSnapshot.getChildrenCount() < 1) {
                            callback.onGetShoppingListsSuccess(null);
                            return;
                        }
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            accessList.add(snapshot.getValue(Access.class));
                        }
                        final int expected = accessList.size();
                        final int[] current = {0};
                        final List<ShoppingList> shoppingLists = new ArrayList<>();

                        for (Access access : accessList) {
                            getShoppingListById(access.getListId(), new GetShoppingListCallback() {
                                @Override
                                public void onGetShoppingListSuccess(ShoppingList shoppingList) {
                                    current[0]++;
                                    shoppingLists.add(shoppingList);
                                    if (current[0] >= expected) {
                                        processList();
                                    }
                                }

                                @Override
                                public void onGetShoppingListError(Throwable t) {
                                    current[0]++;
                                    if (current[0] >= expected) {
                                        processList();
                                    }
                                }

                                private void processList() {
                                    Collections.sort(shoppingLists, new Comparator<ShoppingList>() {
                                        @Override
                                        public int compare(ShoppingList left, ShoppingList right) {
                                            if (left.getTimestamp() > right.getTimestamp()) {
                                                return -1;
                                            } else if (left.getTimestamp() < right.getTimestamp()) {
                                                return 1;
                                            } else {
                                                return 0;
                                            }
                                        }
                                    });
                                    callback.onGetShoppingListsSuccess(shoppingLists);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onGetShoppingListsError(databaseError.toException());
                    }
                });
    }

    @Override
    public void updateShoppingList(final ShoppingList shoppingList, final SaveShoppingListCallback callback) {
        databaseReference.child(ShoppingList.class.getSimpleName()).child(shoppingList.getId()).setValue(shoppingList, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    callback.onSaveShoppingListError(databaseError.toException());
                } else {
                    callback.onSaveShoppingListSuccess();
                }
            }
        });
    }
}
