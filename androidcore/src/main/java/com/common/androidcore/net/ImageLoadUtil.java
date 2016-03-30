package com.common.androidcore.net;

import android.content.Context;
import android.widget.ImageView;

import com.common.androidcore.net.uil.UilImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;

/**
 * 异步加载图片类（使用ImageLoader）
 *
 * @author lifan
 * @version 1.0
 */
public class ImageLoadUtil {
    private static volatile ImageLoadUtil instance = null;
    private UilImageLoader uilImageLoader;

    /**
     * 获得ImageLoadUtil实例
     *
     * @param context  context
     * @param cacheDir 缓存目录
     * @return ImageLoadUtil实例
     */
    public static ImageLoadUtil getInstance(Context context, File cacheDir) {
        if (instance == null) {
            synchronized (ImageLoadUtil.class) {
                if (instance == null) {
                    instance = new ImageLoadUtil(context, cacheDir);
                }
            }
        }
        return instance;
    }

    private ImageLoadUtil(Context context, File cacheDir) {
        uilImageLoader = UilImageLoader.getInstance(context, cacheDir);
    }

    public void loadImg(String url, ImageView imageView) {
        uilImageLoader.loadImg(url, imageView);
    }

    public void loadImg(String url, ImageView imageView, ImageLoadingListener listener) {
        uilImageLoader.loadImg(url, imageView, listener);
    }

    public void cancelDisplayTask(ImageView imageView) {
        uilImageLoader.cancelDisplayTask(imageView);
    }
}
