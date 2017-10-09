package com.csabacsete.sharedshoppinglist.image_loader;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.csabacsete.sharedshoppinglist.R;

public class ImageLoaderGlideImplementation implements ImageLoader {

    @Override
    public void loadInCircle(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void load(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
}
