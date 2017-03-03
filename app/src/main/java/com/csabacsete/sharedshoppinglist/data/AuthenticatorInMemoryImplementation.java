package com.csabacsete.sharedshoppinglist.data;

import android.os.AsyncTask;

public class AuthenticatorInMemoryImplementation implements Authenticator {

    @Override
    public void loginWithCredentials(String email, String password, LoginCallback callback) {
        try {
            if (email.equals("example@email.com") && password.equals("password")) {
                callback.onLoginSuccess();
            } else {
                callback.onInvalidCredentials();
            }
        } catch (Exception e) {
            callback.onRequestError();
        }
    }

    @Override
    public void wait(int ms, final WaitCallback callback) {
        new BackgroundTask(ms).execute(callback);
    }

    @Override
    public boolean isUserLoggedIn() {
        return false;
    }

    public class BackgroundTask extends AsyncTask<WaitCallback, Void, WaitCallback> {

        private final int ms;

        public BackgroundTask(int ms) {
            this.ms = ms;
        }

        @Override
        protected WaitCallback doInBackground(WaitCallback... callbacks) {
            try {
                // Simulate network access.
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return callbacks[0];
        }

        @Override
        protected void onPostExecute(WaitCallback callback) {
            super.onPostExecute(callback);

            callback.onWaitFinished();
        }
    }
}
