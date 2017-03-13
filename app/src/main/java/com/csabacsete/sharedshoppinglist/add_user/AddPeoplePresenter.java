package com.csabacsete.sharedshoppinglist.add_user;

import com.csabacsete.sharedshoppinglist.data.Pending;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.navigator.Navigator;

import java.util.ArrayList;
import java.util.List;

public class AddPeoplePresenter implements AddPeopleContract.Presenter, Repository.SavePendingCallback {
    private final AddPeopleContract.View view;
    private final Repository repository;
    private final Navigator navigator;

    public AddPeoplePresenter(AddPeopleContract.View view, Repository repository, Navigator navigator) {
        this.view = view;
        this.repository = repository;
        this.navigator = navigator;
    }

    @Override
    public void onSendClicked() {
        String listId = view.getListId();
        List<String> emails = view.getEmails();
        List<Pending> pendingList = new ArrayList<>();
        for (String email : emails) {
            pendingList.add(new Pending(email, listId));
        }
        view.showProgress();
        repository.savePendingInvitations(pendingList, this);
    }

    @Override
    public void onSavePendingSuccess() {
        view.hideProgress();
        view.showInvitationSuccess();
        navigator.goBack();
    }

    @Override
    public void onSavePendingError(Throwable t) {
        view.hideProgress();
        view.showInvitationError();
    }
}
