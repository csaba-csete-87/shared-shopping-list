package com.csabacsete.sharedshoppinglist.utils;

import android.support.annotation.IntDef;

import com.yalantis.ucrop.UCrop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {

    public static final String INTENT_EXTRA_LIST_ID = "listId";
    public static final String INTENT_EXTRA_SHOPPING_LIST = "shoppingList";
    public static final String VERSION_FORMAT = "%s (Build number: %d) %s";

    private Constants() {
    }

    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int REQUEST_CODE_CAMERA = 1001;
    public static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1002;

    public static final int RESULT_OK = -1;
    public static final int RESULT_CANCELED = 0;

    @IntDef(value = {RESULT_OK, RESULT_CANCELED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IntentResult {
    }

    public static final int CREATE_FILE_TO_TAKE_PHOTO = 1;
    public static final int CREATE_FILE_TO_CROP_PHOTO_AFTER_TAKE_PHOTO = 2;
    public static final int CREATE_FILE_TO_CROP_PHOTO_AFTER_PICK_PHOTO = 3;

    @IntDef(value = {CREATE_FILE_TO_TAKE_PHOTO, CREATE_FILE_TO_CROP_PHOTO_AFTER_TAKE_PHOTO, CREATE_FILE_TO_CROP_PHOTO_AFTER_PICK_PHOTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CreatePhotoRequest {
    }

    public static final int REQUEST_TAKE_PHOTO = 100;
    public static final int REQUEST_PICK_PHOTO = 101;
    public static final int REQUEST_CROP_PHOTO = UCrop.REQUEST_CROP;

    @IntDef(value = {REQUEST_TAKE_PHOTO, REQUEST_PICK_PHOTO, REQUEST_CROP_PHOTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaRequest {
    }
}
