package com.inkenka.miniimageloader.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;


public class BitmapUtil {

    // 最大限制5MB的输入流
    private static final int MARK_POSITION = 5 * 1024 * 1024;

    // 计算缩放比例
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        int inSampleSize = 1;

        if(reqHeight > 0 && reqWidth > 0) {
            int inSampleSizeW = options.outWidth / reqWidth;
            int inSampleSizeH = options.outHeight / reqHeight;
            if (inSampleSizeW > inSampleSizeH) {
                inSampleSize = inSampleSizeW;
            } else {
                inSampleSize = inSampleSizeH;
            }
        }
        return inSampleSize;
    }


    // 获取缩放后的图片
    public static Bitmap getCompressedBitmap(InputStream inputStream,int reqWidth,int reqHeight)
        throws IOException {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        int[] size = getBitmapSize(inputStream);

        options.outWidth = size[0];
        options.outHeight = size[1];
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;

        //不需要缩放
        if(options.inSampleSize<=1){
            return BitmapFactory.decodeStream(inputStream);
        }
        //inSampleSize！=1进行缩放
        return BitmapFactory.decodeStream(inputStream, null, options);
    }


    private static int[] getBitmapSize(InputStream inputStream) throws IOException {
        int[] result = new int[2];

        inputStream.mark(MARK_POSITION);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeStream(inputStream, null, options);
        inputStream.reset();

        result[0] = options.outWidth;
        result[1] = options.outHeight;

        return result;
    }

}
