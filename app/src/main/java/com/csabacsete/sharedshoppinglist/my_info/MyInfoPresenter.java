package com.csabacsete.sharedshoppinglist.my_info;

import com.csabacsete.sharedshoppinglist.data.Authenticator;
import com.csabacsete.sharedshoppinglist.data.Repository;
import com.csabacsete.sharedshoppinglist.data.Storage;
import com.csabacsete.sharedshoppinglist.data.User;
import com.csabacsete.sharedshoppinglist.utils.Constants;

import java.io.File;

import timber.log.Timber;

public class MyInfoPresenter implements MyInfoContract.Presenter, Repository.GetUserCallback, Storage.UploadFileCallback, Repository.CreateUserCallback, Storage.CreateFileCallback {

    private final MyInfoContract.View view;
    private final Repository repository;
    private final Authenticator authenticator;
    private final Storage storage;


    public MyInfoPresenter(MyInfoContract.View view, Repository repository, Authenticator authenticator, Storage storage) {
        this.view = view;
        this.repository = repository;
        this.authenticator = authenticator;
        this.storage = storage;
    }

    @Override
    public void onPageLoaded() {
        view.showProgress();
        repository.getUserById(authenticator.getCurrentUser().getId(), this);
    }

    @Override
    public void onProfileImageClicked() {
        view.showSelectImageSourceDialog();
    }

    @Override
    public void onSaveButtonClicked() {
        view.showProgress();
        User user = view.getUser();
        user.setDisplayName(view.getDisplayName());
        repository.saveUser(user, this);
    }

    @Override
    public void onImageSourceOptionSelected(int i) {
        switch (i) {
            case 0:
                onTakePictureClicked();
                break;
            case 1:
                onChooseMediaClicked();
                break;
            case 2:
            default:
                view.dismissDialog();
                break;

        }
    }

    @Override
    public void onChooseMediaClicked() {
        if (view.hasStoragePermissions()) {
            view.dismissDialog();
            view.pickImageFromGallery();
        } else {
            view.askForStoragePermissions();
        }
    }

    @Override
    public void onTakePictureClicked() {
        if (view.hasCameraAndStoragePermissions()) {
            if (view.hasCameraActivityToHandleIntent()) {
                view.dismissDialog();
                storage.createFile(authenticator.getCurrentUser().getId(), Constants.CREATE_FILE_TO_TAKE_PHOTO, this);
            } else {
                view.showThereIsNoCameraActivityToHandleIntent();
            }
        } else {
            view.askForCameraPermissions();
        }
    }

    @Override
    public void onMediaResult(int requestCode, int resultCode, File outputFile) {
        switch (resultCode) {
            case Constants.RESULT_OK:
                break;
            case Constants.RESULT_CANCELED:
            default:
                view.showProcessCanceled();
                return;
        }
        switch (requestCode) {
            case Constants.REQUEST_TAKE_PHOTO:
                storage.createFile(null, Constants.CREATE_FILE_TO_CROP_PHOTO_AFTER_TAKE_PHOTO, this);
                break;
            case Constants.REQUEST_PICK_PHOTO:
                storage.createFile(null, Constants.CREATE_FILE_TO_CROP_PHOTO_AFTER_PICK_PHOTO, this);
                break;
            case Constants.REQUEST_CROP_PHOTO:
                view.showProgress();
                storage.uploadFile(outputFile, this);
                break;
            default:
                Timber.e("Invalid request code.");
                break;
        }
    }

    @Override
    public void onPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_CAMERA:
                if (resultsAreGranted(grantResults)) {
                    onTakePictureClicked();
                } else {
                    view.dismissDialog();
                    view.showPermissionsAreNeededForCameraDialog();
                }
                break;
            case Constants.REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (resultsAreGranted(grantResults)) {
                    onChooseMediaClicked();
                } else {
                    view.dismissDialog();
                    view.showPermissionsAreNeededForStorageDialog();
                }
                break;
            default:
                Timber.e("Permission request code is invalid.");
                break;
        }
    }

    @Override
    public void onGetUserSuccess(User user) {
        view.hideProgress();
        view.setUserData(user);
    }

    @Override
    public void onGetUserError(Throwable t) {
        view.hideProgress();
        view.showCouldNotGetUserError();
    }

    @Override
    public void onFileUploadSuccess(String uri) {
        view.loadImage(uri);
    }

    @Override
    public void onFileUploadError(Throwable t) {
        view.showCouldNotUpdateImageError();
    }

    @Override
    public void onSaveUserSuccess(User user) {
        view.showSaveUserSuccess();
    }

    @Override
    public void onSaveUserError(Throwable t) {
        view.showSaveUserError();
    }

    @Override
    public void onCreateImageFileSuccess(int purpose, File imageFile) {
        switch (purpose) {
            case Constants.CREATE_FILE_TO_TAKE_PHOTO:
                view.setCurrentPhotoPath(imageFile.getAbsolutePath());
                view.takePicture(imageFile);
                break;
            case Constants.CREATE_FILE_TO_CROP_PHOTO_AFTER_TAKE_PHOTO:
            case Constants.CREATE_FILE_TO_CROP_PHOTO_AFTER_PICK_PHOTO:
                view.cropPhoto(new File(view.getCurrentPhotoPath()), imageFile);
                break;
            default:
                view.hideProgress();
                Timber.e("Invalid request code.");
                break;
        }
    }

    @Override
    public void onCreateImageFileCallbackError(Throwable t) {

    }

    private boolean resultsAreGranted(int[] grantResults) {
        boolean result = true;
        for (int grantResult : grantResults) {
            if (grantResult != 0) {
                result = false;
            }
        }
        return result;
    }
}
