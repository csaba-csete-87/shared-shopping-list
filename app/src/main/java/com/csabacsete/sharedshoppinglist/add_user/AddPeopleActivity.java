package com.csabacsete.sharedshoppinglist.add_user;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPeopleActivity extends BaseActivity implements AddPeopleContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
    }

    private static final String ARGUMENT_LIST_ID = "listId";
    @BindView(R.id.contacts_autocomplete)
    ContactsCompletionTextView contactsCompletionView;

    private Person[] people;
    private String shoppingListId;
    private ArrayAdapter<Person> adapter;
    private AddPeopleContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        ButterKnife.bind(this);

        people = new Person[]{
                new Person("Marshall Weir", "marshall@example.com"),
                new Person("Margaret Smith", "margaret@example.com"),
                new Person("Max Jordan", "max@example.com"),
                new Person("Meg Peterson", "meg@example.com"),
                new Person("Amanda Johnson", "amanda@example.com"),
                new Person("Terry Anderson", "terry@example.com")
        };

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, people);
        contactsCompletionView.setAdapter(adapter);
        contactsCompletionView.setThreshold(1);

        shoppingListId = getIntent().getStringExtra(ARGUMENT_LIST_ID);

        presenter = new AddPeoplePresenter(
                this,
                getRepository(),
                getNavigator()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_add_people, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                presenter.onSendClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getListId() {
        return shoppingListId;
    }

    @Override
    public List<String> getEmails() {
        List<String> emailList = new ArrayList<>();
        for (int i = 0; i < contactsCompletionView.getObjects().size(); i++) {
            Person person = contactsCompletionView.getObjects().get(i);
            emailList.add(person.getEmail());
        }
        return emailList;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showInvitationSuccess() {
        Toast.makeText(this, getString(R.string.invitations_sent), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvitationError() {
        Toast.makeText(this, getString(R.string.failed_to_send_invitations), Toast.LENGTH_SHORT).show();
    }
}
