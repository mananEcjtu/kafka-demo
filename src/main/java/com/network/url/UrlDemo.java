package com.network.url;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlDemo {
    public static void main(String[] args) throws IOException {
        String fileName = "test.txt";
        // url地址
        URL url = new URL("http://localhost:8080/ma/" + fileName);

        // 连接到这个资源
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream fos = new FileOutputStream(fileName);

        byte[] bytes = new byte[1024];
        int len;
        while ((len=inputStream.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }

        fos.close();
        inputStream.close();
        urlConnection.disconnect();
    }
}
