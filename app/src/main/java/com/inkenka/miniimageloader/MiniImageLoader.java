package com.inkenka.miniimageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 图片框架API接口类
 */
public class MiniImageLoader {

    public void loadImage(final String url, final ImageView imageView){

        MiniImageLoaderExecutor.getInstance().execute(new Job(url, new Job.MainThreadCallback() {
            @Override
            public void onSuccessed(Bitmap bitmap) {
                if(null != bitmap){
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailed(String errorMsg) {

            }
        }));
    }
}
