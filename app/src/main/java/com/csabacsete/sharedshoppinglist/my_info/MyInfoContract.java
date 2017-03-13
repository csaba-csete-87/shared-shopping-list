package com.csabacsete.sharedshoppinglist.my_info;

import com.csabacsete.sharedshoppinglist.data.User;

import java.io.File;

public interface MyInfoContract {

    interface View {

        void showProgress();

        void hideProgress();

        void setUserData(User user);

        void showCouldNotGetUserError();

        boolean hasStoragePermissions();

        void askForStoragePermissions();

        User getUser();

        String getDisplayName();

        void loadImage(String uri);

        void showFileUploadError();

        void showSaveUserSuccess();

        void showSaveUserError();

        void showSelectImageSourceDialog();

        void dismissDialog();

        void pickImageFromGallery();

        boolean hasCameraAndStoragePermissions();

        boolean hasCameraActivityToHandleIntent();

        void showThereIsNoCameraActivityToHandleIntent();

        void askForCameraPermissions();

        void setCurrentPhotoPath(String path);

        void takePicture(File imageFile);

        String getCurrentPhotoPath();

        void cropPhoto(File source, File destination);

        void showProcessCanceled();

        void showPermissionsAreNeededForCameraDialog();

        void showPermissionsAreNeededForStorageDialog();

        void showCouldNotUpdateImageError();
    }

    interface Presenter {

        void onPageLoaded();

        void onProfileImageClicked();

        void onSaveButtonClicked();

        void onImageSourceOptionSelected(int i);

        void onMediaResult(int requestCode, int resultCode, File outputFile);

        void onPermissionResult(int requestCode, String[] permissions, int[] grantResults);

        void onTakePictureClicked();

        void onChooseMediaClicked();
    }
}
