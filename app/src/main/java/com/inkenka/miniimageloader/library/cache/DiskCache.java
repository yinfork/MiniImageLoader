package com.inkenka.miniimageloader.library.cache;

import java.io.IOException;
import java.io.OutputStream;

public interface DiskCache<T> {

    T get(String key);
    void put(String key, Writer writer) throws IOException;

    /**
     * 参考Glide使用Writer,用来写入到DiskLruCache的输出流
     */
    interface Writer{
        boolean write(OutputStream outputStream) throws IOException;
    }
}
