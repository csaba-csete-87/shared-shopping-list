package com.csabacsete.sharedshoppinglist.data;

import android.support.annotation.NonNull;

import com.csabacsete.sharedshoppinglist.utils.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import timber.log.Timber;

public class AuthenticatorFirebaseImplementation implements Authenticator, FirebaseAuth.AuthStateListener {

    private final FirebaseAuth auth;

    public AuthenticatorFirebaseImplementation() {
        auth = FirebaseAuth.getInstance();

        auth.addAuthStateListener(this);
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        return getUserFromFirebaseUser(firebaseUser);
    }

    private User getUserFromFirebaseUser(FirebaseUser firebaseUser) {
        if (firebaseUser == null) {
            return null;
        }
        User u = new User(firebaseUser.getUid(), firebaseUser.getEmail());
        u.setDisplayName(firebaseUser.getDisplayName());
        if (firebaseUser.getPhotoUrl() != null) {
            u.setPhotoUrl(firebaseUser.getPhotoUrl().getPath());
        }
        return u;
    }

    @Override
    public void createAccount(final String email, String password, final CreateAccountCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onCreateAccountSuccess(email);
                    } else {
                        callback.onCreateAccountError(task.getException());
                    }
                });
    }

    @Override
    public void loginWithCredentials(final String email, final String password, final LoginCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Timber.d(task.getResult().getUser().getUid());
                        callback.onLoginSuccess();
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                            callback.onEmailDoesNotExist(email, password);
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            callback.onInvalidCredentials();
                        } else {
                            Timber.d(task.getException().toString());
                            callback.onRequestError(task.getException());
                        }
                    }
                });
    }

    @Override
    public void loginWithGoogle(String idToken, final LoginCallback callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Timber.d(task.getResult().getUser().getUid());
                        callback.onLoginSuccess();
                    } else {
                        Timber.d(task.getException().toString());
                        callback.onRequestError(task.getException());
                    }
                });
    }

    @Override
    public boolean isUserLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null && !StringUtils.isEmpty(user.getUid());
    }

    @Override
    public void logout() {
        auth.signOut();
    }

    @Override
    public void onDestroy() {
        auth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Timber.d("user signed in: " + user.getUid());
        } else {
            Timber.d("user signed out");
        }
    }
}
