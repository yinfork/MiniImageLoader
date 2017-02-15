package com.inkenka.miniimageloader.library;

import android.graphics.Bitmap;

interface MainThreadCallback {
    void onSuccessed(Bitmap bitmap);
    void onFailed(String errorMsg);
}
