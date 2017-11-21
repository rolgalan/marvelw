package me.rolgalan.marvelw.view.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.rolgalan.marvelw.R;

/**
 * Created by Roldán Galán on 21/11/2017.
 */

public class ImageUtils {

    public static void loadImageLandscape(Context context, ImageView image, String imgUrl) {

        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(context)
                    .load(imgUrl)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder_landscape)
                    .crossFade()
                    .into(image);
        }
    }

}
