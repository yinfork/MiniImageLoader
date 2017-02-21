package com.inkenka.miniimageloader.library.net;

import com.inkenka.miniimageloader.library.cache.DiskCache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网络请求类
 */
public class NetworkManager {

    public static Bitmap requestImageFromUrl(String imageUrl, DiskCache diskCache) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream inputStream = null;
        try {
            final URL url = new URL(imageUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);

            if(null != diskCache) {
                diskCache.put(imageUrl,new DiskCacheWriter(inputStream));
            }

            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    static class DiskCacheWriter implements DiskCache.Writer{

        InputStream mInputStream;

        public DiskCacheWriter(InputStream inputStream){
            mInputStream = inputStream;
        }

        @Override
        public boolean write(OutputStream outputStream) throws IOException {
            BufferedOutputStream out =null;

            out =new BufferedOutputStream(outputStream,8*1024);
            int b;
            while ((b =mInputStream.read()) !=-1){
                out.write(b);
            }
            return true;
        }
    }
}
