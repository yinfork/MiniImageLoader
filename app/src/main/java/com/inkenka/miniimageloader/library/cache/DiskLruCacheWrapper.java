package com.inkenka.miniimageloader.library.cache;

import com.inkenka.miniimageloader.library.Utils;
import com.jakewharton.disklrucache.DiskLruCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 对DiskLruCache进行接口封装
 */
public class DiskLruCacheWrapper implements DiskCache<Bitmap> {

    private static final String TAG = "DiskLruCacheWrapper";

    private static final String DIR_NAME = "diskCache";

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;

    private static final int DEFAULT_VALUE_COUNT = 1;

    private DiskLruCache mDiskLruCache;


    public DiskLruCacheWrapper(@NonNull DiskLruCache diskLruCache) {
        mDiskLruCache = diskLruCache;
    }

    public static DiskLruCacheWrapper build(Context context) {
        DiskLruCacheWrapper diskLruCacheWrapper = null;
        File diskCacheDir = getDiskCacheDir(context, DIR_NAME);
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }

        //sd卡的可用空间大于本地缓存的大小
        if (Utils.getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            //实例化diskLrucache
            DiskLruCache diskLruCache = null;
            try {
                diskLruCache = DiskLruCache.open(
                    getDiskCacheDir(context, DIR_NAME),
                    Utils.getAppVersion(context),
                    DEFAULT_VALUE_COUNT,
                    DISK_CACHE_SIZE);
                diskLruCacheWrapper = new DiskLruCacheWrapper(diskLruCache);
            } catch (IOException e) {
                Log.w(TAG, "build()", e);
            }
        }
        return diskLruCacheWrapper;
    }

    @Override
    public void put(@Nullable String key,@Nullable Writer writer){
        if(!TextUtils.isEmpty(key) || null == writer) return;

        String md5Key = Utils.hashKeyFromString(key);

        DiskLruCache.Editor editor = null;
        OutputStream outputStream = null;
        boolean isWrote = false;
        try {
            editor = mDiskLruCache.edit(md5Key);
            //打开本地缓存的输入流
            outputStream = editor.newOutputStream(DEFAULT_VALUE_COUNT);
            isWrote = writer.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭输出流
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 关闭DiskLruCache的Editor
            try {
                if(null != editor) {
                    if (isWrote) {
                        //提交数据，并是释放连接
                        editor.commit();
                    } else {
                        //释放连接
                        editor.abort();
                    }
                }
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public Bitmap get(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(Utils.hashKeyFromString(key));
            if (snapshot == null) //not find entry , or entry.readable = false
            {
                Log.e(TAG, "not find entry , or entry.readable = false");
                return null;
            }
            //write READ
            FileInputStream inputStream = (FileInputStream) snapshot.getInputStream(0);

            //不压缩图片
            return BitmapFactory.decodeFileDescriptor(inputStream.getFD());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
            || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
