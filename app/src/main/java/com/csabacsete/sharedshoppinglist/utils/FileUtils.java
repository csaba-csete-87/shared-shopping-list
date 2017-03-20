package com.csabacsete.sharedshoppinglist.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.csabacsete.sharedshoppinglist.App;
import com.csabacsete.sharedshoppinglist.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class FileUtils {

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private FileUtils() {
    }

    public static File createCopyOfFileFromMediaStore(ContentResolver contentResolver, Uri uri) throws Throwable {
        return writeToDisk(MediaStore.Images.Media.getBitmap(contentResolver, uri));
    }

    public static File writeToDisk(@NonNull final Bitmap image) throws Throwable {
        final Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        final File imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + UUID.randomUUID().toString() + "." + format.name().toLowerCase());

        File parent = imageFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new Throwable("could not create parent directory");
        }

        try {
            if (!imageFile.createNewFile()) {
                throw new Throwable("could not create file");
            }
        } catch (IOException e) {
            throw new Throwable(e);
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            image.compress(format, 85, fos);
        } catch (IOException e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imageFile;
    }

    public static File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp;
        return File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, getAlbumDir());
    }

    private static File getAlbumDir() throws IOException {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getAlbumName());
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    throw new IOException("Failed to create directory");
                }
            }
            return storageDir;
        }
        throw new IOException("External storage is not mounted READ/WRITE.");
    }

    private static String getAlbumName() {
        return App.getInstance().getApplicationContext().getString(R.string.app_album);
    }
}
