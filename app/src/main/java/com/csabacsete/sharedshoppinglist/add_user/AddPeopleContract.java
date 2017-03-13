package com.csabacsete.sharedshoppinglist.add_user;

import java.util.List;

public interface AddPeopleContract {

    interface View {

        String getListId();

        List<String> getEmails();

        void showProgress();

        void hideProgress();

        void showInvitationSuccess();

        void showInvitationError();
    }

    interface Presenter {

        void onSendClicked();
    }
}
