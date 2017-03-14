package com.csabacsete.sharedshoppinglist.my_info;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.csabacsete.sharedshoppinglist.R;
import com.csabacsete.sharedshoppinglist.data.User;
import com.csabacsete.sharedshoppinglist.drawer.DrawerActivity;
import com.csabacsete.sharedshoppinglist.utils.Constants;
import com.csabacsete.sharedshoppinglist.utils.FileUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfoActivity extends DrawerActivity implements MyInfoContract.View {

    private static final String CURRENT_PHOTO_PATH = "currentPhotoPath";

    @BindView(R.id.display_name)
    TextInputEditText displayName;

    @BindView(R.id.email)
    TextInputEditText email;

    @BindView(R.id.profile_image)
    ImageView profileImage;

    private User user;
    private AlertDialog chooseMediaFragmentDialog;
    private MyInfoContract.Presenter presenter;
    private String currentPhotoPath;

    @OnClick(R.id.profile_image)
    void onProfileImageClicked() {
        presenter.onProfileImageClicked();
    }

    @OnClick(R.id.button_save)
    void onSaveButtonClicked() {
        presenter.onSaveButtonClicked();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        setTitle(getString(R.string.my_profile));

        presenter = new MyInfoPresenter(
                this,
                getRepository(),
                getAuthenticator(),
                getStorage()
        );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_PHOTO_PATH, currentPhotoPath);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //We cannot send objects from the Android library to the presenter, so we need to create a file depending on the request.
        boolean noExceptionsThrown = true;
        File outputFile = null;
        if (requestCode == Constants.REQUEST_PICK_PHOTO) {
            try {
                if (data != null && data.getData() != null) {
                    outputFile = FileUtils.createCopyOfFileFromMediaStore(this.getContentResolver(), data.getData());
                    setCurrentPhotoPath(outputFile.getPath());
                } else {
                    showProcessCanceled();
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                showErrorMessage(R.string.could_not_update_image_too_large);
                noExceptionsThrown = false;
            }
        } else if (requestCode == Constants.REQUEST_CROP_PHOTO) {
            if (data != null) {
                final Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null) {
                    outputFile = new File(resultUri.getEncodedPath());
                } else {
                    showCouldNotUpdateImageError();
                }
            } else {
                showCouldNotUpdateImageError();
            }
        }
        if (noExceptionsThrown) {
            presenter.onMediaResult(requestCode, resultCode, outputFile);
        }
    }

    private void showErrorMessage(@StringRes int stringResource) {
        showErrorMessage(getString(stringResource));
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.onPageLoaded();
    }

    @Override
    public void showProgress() {
// TODO: 3/13/17
    }

    @Override
    public void hideProgress() {
// TODO: 3/13/17
    }

    @Override
    public void setUserData(User user) {
        this.user = user;
        email.setText(user.getEmail());
        displayName.setText(user.getDisplayName());
    }

    @Override
    public void showCouldNotGetUserError() {
        Toast.makeText(this, getString(R.string.could_not_get_user), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean hasStoragePermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void askForStoragePermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                Constants.REQUEST_CODE_READ_EXTERNAL_STORAGE);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String getDisplayName() {
        return displayName.getText().toString();
    }

    @Override
    public void loadImage(String uri) {
        Glide.with(this)
                .load(uri)
                .into(profileImage);
    }

    @Override
    public void showFileUploadError() {
        Toast.makeText(this, getString(R.string.error_uploading_file), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSaveUserSuccess() {
        Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSaveUserError() {
        Toast.makeText(this, getString(R.string.error_updating_info), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSelectImageSourceDialog() {
        chooseMediaFragmentDialog = new AlertDialog.Builder(this)
                .setSingleChoiceItems(getResources().getStringArray(R.array.image_sources), 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.onImageSourceOptionSelected(i);
                    }
                })
                .show();
    }

    @Override
    public void dismissDialog() {
        if (chooseMediaFragmentDialog != null && chooseMediaFragmentDialog.isShowing()) {
            chooseMediaFragmentDialog.dismiss();
        }
    }

    @Override
    public void pickImageFromGallery() {
        Intent pickMediaIntent = new Intent();
        pickMediaIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        pickMediaIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        pickMediaIntent.addCategory(Intent.CATEGORY_OPENABLE);
        pickMediaIntent.setType("*/*");
        pickMediaIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*"});
        startActivityForResult(pickMediaIntent, Constants.REQUEST_PICK_PHOTO);
    }

    @Override
    public boolean hasCameraAndStoragePermissions() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public boolean hasCameraActivityToHandleIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return (takePictureIntent.resolveActivity(this.getPackageManager()) != null);
    }

    @Override
    public void showThereIsNoCameraActivityToHandleIntent() {
        Toast.makeText(this, getString(R.string.no_activity_to_handle_action), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void askForCameraPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },
                Constants.REQUEST_CODE_CAMERA);
    }

    @Override
    public void setCurrentPhotoPath(String path) {
        currentPhotoPath = path;
    }

    @Override
    public void takePicture(File imageFile) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(takePictureIntent, Constants.REQUEST_TAKE_PHOTO);
    }

    @Override
    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    @Override
    public void cropPhoto(File source, File destination) {
        UCrop uCrop = UCrop.of(Uri.fromFile(source), Uri.parse("file:///" + destination.getAbsolutePath()))
                .withMaxResultSize(getResources().getInteger(R.integer.image_max_width), getResources().getInteger(R.integer.image_max_width))
                .withOptions(getCropViewOptions());
        uCrop.withAspectRatio(1, 1);
        uCrop.start(this);
    }

    @Override
    public void showProcessCanceled() {
        Toast.makeText(this, getString(R.string.process_canceled), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPermissionsAreNeededForCameraDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.camera_permissions_needed_title)
                .setMessage(R.string.camera_permissions_needed_message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.onTakePictureClicked();
                    }
                })
                .setNegativeButton(R.string.never_mind, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void showPermissionsAreNeededForStorageDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.storage_permissions_needed_title)
                .setMessage(R.string.storage_permissions_needed_message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.onChooseMediaClicked();

                    }
                })
                .setNegativeButton(R.string.never_mind, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void showCouldNotUpdateImageError() {
        showErrorMessage(R.string.could_not_update_image);
    }

    private UCrop.Options getCropViewOptions() {
        UCrop.Options uCropOptions = new UCrop.Options();
        uCropOptions.setShowCropGrid(false);
        uCropOptions.setCompressionFormat(Bitmap.CompressFormat.PNG);
        uCropOptions.setShowCropFrame(false);
        uCropOptions.setCircleDimmedLayer(true);
        uCropOptions.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        uCropOptions.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        uCropOptions.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));
        return uCropOptions;
    }
}