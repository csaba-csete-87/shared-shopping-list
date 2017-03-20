package com.csabacsete.sharedshoppinglist.image_loader;

import android.content.Context;
import android.widget.ImageView;

public class ImageLoaderPicassoImplementation implements ImageLoader {

    @Override
    public void loadInCircle(Context context, String url, ImageView imageView) {
//        Picasso.with(context)
//                .loadInCircle(url)
//                .into(imageView);
    }

    @Override
    public void load(Context context, String url, ImageView imageView) {

    }
}
