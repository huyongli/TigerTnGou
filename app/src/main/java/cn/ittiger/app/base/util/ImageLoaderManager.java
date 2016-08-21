package cn.ittiger.app.base.util;

import cn.ittiger.app.R;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

/**
 * @author: laohu on 2016/7/13
 * @site: http://ittiger.cn
 */
public class ImageLoaderManager {

    private static volatile ImageLoaderManager sImageLoaderManager;

    private DisplayImageOptions mImageOptions;

    private ImageLoaderManager() {

        mImageOptions = new DisplayImageOptions.Builder()
        // 设置图片在下载期间显示的图片
        .showImageOnLoading(R.drawable.default_image)
        // 设置图片Uri为空或是错误的时候显示的图片
        .showImageForEmptyUri(R.drawable.default_image)
        // 设置图片加载/解码过程中错误时候显示的图片
        .showImageOnFail(R.drawable.default_image)
        // 设置下载的图片是否缓存在内存中
        .cacheInMemory(true)
        // 设置下载的图片是否缓存在SD卡中
        .cacheOnDisk(true)
        .considerExifParams(true)
        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
        .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
        // .decodingOptions(android.graphics.BitmapFactory.Options
        // decodingOptions)//设置图片的解码配置
        // 设置图片下载前的延迟
        // .delayBeforeLoading(int delayInMillis)//int
        // delayInMillis为你设置的延迟时间
        // 设置图片加入缓存前，对bitmap进行设置
        // 。preProcessor(BitmapProcessor preProcessor)
        .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
        // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
        .displayer(new FadeInBitmapDisplayer(100))// 淡入
        .build();
    }

    public static void init(Context  context) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        .threadPoolSize(3)//线程池内加载的数量
        .threadPriority(Thread.NORM_PRIORITY - 2)
        .denyCacheImageMultipleSizesInMemory()
        .diskCacheFileNameGenerator(new Md5FileNameGenerator())
        .diskCacheSize(50 * 1024 * 1024) // 50 MiB
        .tasksProcessingOrder(QueueProcessingType.LIFO)
        .writeDebugLogs() // Remove for release app
        .build();

        ImageLoader.getInstance().init(config);
    }

    public static ImageLoaderManager getInstance() {

        if(sImageLoaderManager == null) {
            synchronized (ImageLoaderManager.class) {
                if(sImageLoaderManager == null) {
                    sImageLoaderManager = new ImageLoaderManager();
                }
            }
        }
        return sImageLoaderManager;
    }

    public void displayImage(final ImageView imageView, String url) {

        ImageLoader.getInstance().displayImage(url, imageView, mImageOptions, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                super.onLoadingComplete(imageUri, view, loadedImage);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                super.onLoadingFailed(imageUri, view, failReason);
            }
        });


    }
}
