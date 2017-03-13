package com.csabacsete.sharedshoppinglist.data;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.csabacsete.sharedshoppinglist.utils.FileUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class StorageFirebaseImplementation implements Storage {

    private final StorageReference storage;

    public StorageFirebaseImplementation() {
        storage = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void createFile(String fileName, int purpose, CreateFileCallback callback) {
        try {
            File f = FileUtils.createImageFile();
            if (!f.exists()) {
                callback.onCreateImageFileCallbackError(new Exception("Could not read file from disk."));
            } else {
                callback.onCreateImageFileSuccess(purpose, f);
            }
        } catch (IOException e) {
            callback.onCreateImageFileCallbackError(e);
        }
    }

    @Override
    public void uploadFile(File file, final UploadFileCallback callback) {
        Uri fileUri = Uri.fromFile(file);
        StorageReference riversRef = storage.child("images/" + file.getName());

        riversRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getDownloadUrl() != null) {
                            callback.onFileUploadSuccess(taskSnapshot.getDownloadUrl().toString());
                        } else {
                            callback.onFileUploadError(new Throwable("Could not upload file."));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        callback.onFileUploadError(exception);
                    }
                });
    }

    @Override
    public void downloadFile(File destination, DownloadFileCallback callback) {

    }
}
