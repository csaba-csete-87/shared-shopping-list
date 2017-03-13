package com.csabacsete.sharedshoppinglist.data;

import java.io.File;

public interface Storage {

    interface CreateFileCallback {

        void onCreateImageFileSuccess(int purpose, File imageFile);

        void onCreateImageFileCallbackError(Throwable t);
    }

    void createFile(String fileName, int purpose, CreateFileCallback callback);

    interface UploadFileCallback {

        void onFileUploadSuccess(String uri);

        void onFileUploadError(Throwable t);
    }

    void uploadFile(File file, UploadFileCallback callback);

    interface DownloadFileCallback {

        void onFileDownloadSuccess(File file);

        void onFileDownloadError(Throwable t);
    }

    void downloadFile(File destination, DownloadFileCallback callback);
}
