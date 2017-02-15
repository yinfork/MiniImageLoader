package com.inkenka.miniimageloader.library.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络请求类
 */
public class NetworkManager {

    public static Bitmap requestImageFromUrl(String url) throws Exception {
        URL imgurl = null;
        Bitmap bitmap = null;

        HttpURLConnection urlConnection;
        imgurl = new URL(url);
        urlConnection = (HttpURLConnection)
            imgurl.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        InputStream is = urlConnection.getInputStream();
        bitmap = BitmapFactory.decodeStream(is);
        is.close();
        return bitmap;
    }

}
