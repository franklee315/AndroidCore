package com.common.androidcore.net.uil;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

/**
 * Created by lifa on 2015.8.24.
 * uil图片工具类
 */
public class UilImageLoader {
    private static volatile UilImageLoader instance = null;
    private ImageLoader imageLoader;

    public static UilImageLoader getInstance(Context context, File cacheDir) {
        if (instance == null) {
            synchronized (UilImageLoader.class) {
                if (instance == null) {
                    instance = new UilImageLoader(context, cacheDir);
                }
            }
        }
        return instance;
    }

    private UilImageLoader(Context context, File cacheDir) {
        UnlimitedDiskCache diskCache = null;
        if (cacheDir != null) {
            diskCache = new UnlimitedDiskCache(cacheDir);
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                .diskCache(diskCache)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(getWholeOptions())
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    private DisplayImageOptions getWholeOptions() {
        DisplayImageOptions.Builder build = new DisplayImageOptions.Builder();
        build.cacheOnDisk(true);
        build.cacheInMemory(true);
        build.imageScaleType(ImageScaleType.EXACTLY);
        build.bitmapConfig(Bitmap.Config.RGB_565);
        build.delayBeforeLoading(0);
        return build.build();
    }

    /**
     * 异步加载图片
     *
     * @param url       图片地址
     * @param imageView 图片容器
     */
    public void loadImg(String url, ImageView imageView) {
        loadImg(url, imageView, null);
    }

    /**
     * 异步加载图片
     *
     * @param url       图片地址
     * @param imageView 图片容器
     * @param listener  加载监听
     */
    public void loadImg(String url, ImageView imageView, ImageLoadingListener listener) {
        imageLoader.displayImage(url, imageView, listener);
    }

    /**
     * 取消图片加载
     *
     * @param imageView
     */
    public void cancelDisplayTask(ImageView imageView) {
        imageLoader.cancelDisplayTask(imageView);
    }
}
