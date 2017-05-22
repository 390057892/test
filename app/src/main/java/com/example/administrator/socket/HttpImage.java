package com.example.administrator.socket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class HttpImage extends Thread {

    private ImageView imageView;
    private String url;
    private Handler handler;

    public HttpImage(String url,Handler handler,ImageView imageView){
        this.url=url;
        this.imageView=imageView;
        this.handler=handler;
    }

    @Override
    public void run() {
        try {
            URL httpUrl=new URL(url);
            try {
                HttpURLConnection connection= (HttpURLConnection) httpUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                InputStream is=connection.getInputStream();
                final Bitmap bitmap= BitmapFactory.decodeStream(is);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
