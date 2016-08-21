package cn.ittiger.app.base.util;

import cn.ittiger.app.R;

import com.squareup.picasso.Picasso;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @author: laohu on 2016/7/3
 * @site: http://ittiger.cn
 */
public class PicassoUtil {

    public static void displayPhoto(ImageView imageView, String url) {

        Picasso.with(ApplicationHelper.getInstance().getApplication()).load(url)
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).into(imageView);
    }

    public static void displayPhoto(ImageView imageView, String url, int width) {

        Picasso.with(ApplicationHelper.getInstance().getApplication()).load(url)
                .resize(width, width)
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).into(imageView);
    }

    public static void displayPhoto(ImageView imageView, String url, String tag) {

        Picasso.with(ApplicationHelper.getInstance().getApplication()).load(url)
                .config(Bitmap.Config.RGB_565)
                .tag(tag)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image).into(imageView);
    }

    public static void cancelTag(String tag) {

        Picasso.with(ApplicationHelper.getInstance().getApplication()).cancelTag(tag);
    }
}
