package com.csabacsete.sharedshoppinglist.image_loader;

import android.content.Context;
import android.widget.ImageView;

public interface ImageLoader {

    void loadInCircle(Context context, String url, ImageView imageView);

    void load(Context context, String url, ImageView imageView);
}
