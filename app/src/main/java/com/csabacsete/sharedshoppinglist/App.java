package com.csabacsete.sharedshoppinglist;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.AuthenticatorFirebaseImplementation;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.data.RepositoryFirebaseImplementation;
import com.csabacsete.sharedshoppinglist.data.Storage;
import com.csabacsete.sharedshoppinglist.data.StorageFirebaseImplementation;
import com.csabacsete.sharedshoppinglist.image_loader.ImageLoader;
import com.csabacsete.sharedshoppinglist.image_loader.ImageLoaderGlideImplementation;

import java.util.Collections;

import io.fabric.sdk.android.Fabric;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import timber.log.Timber;

public class App extends Application {

    private static App app;
    private Repository repository;
    private Authenticator authenticator;
    private Storage storage;
    private OkHttpClient okHttpClient;
    private ImageLoader imageLoader;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this, new Crashlytics());
        }
    }

    public Authenticator getAuthenticator() {
        if (authenticator == null) {
            authenticator = new AuthenticatorFirebaseImplementation();
        }
        return authenticator;
    }

    public Repository getRepository() {
        if (repository == null) {
            repository = new RepositoryFirebaseImplementation();
        }
        return repository;
    }

    public Storage getStorage() {
        if (storage == null) {
            storage = new StorageFirebaseImplementation();
        }
        return storage;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectionSpecs(Collections.singletonList(new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).build()));
            okHttpClient = builder.build();
        }
        return null;
    }

    public ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = new ImageLoaderGlideImplementation();
        }
        return imageLoader;
    }
}
