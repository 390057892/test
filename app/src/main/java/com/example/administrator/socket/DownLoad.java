package com.example.administrator.socket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.RandomAccess;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/5/6 0006.
 */

public class DownLoad extends Thread {

    private String url;
    private ImageView img;
    private Handler handler;

    public  DownLoad(Handler handler){
        this.handler=handler;
    }

//    public DownLoad(String url,Handler handler,ImageView img){
//        this.url=url;
//        this.handler=handler;
//        this.img=img;
//    }

    //创建线程池
    static Executor threadPool= Executors.newFixedThreadPool(3);

    static class DownLoadRunable implements Runnable{
        private String url;
        private String fileName;
        private long start;
        private long end;
        private Handler handler;
        public DownLoadRunable(String url,String fileName,long start,long end,Handler handler){
            this.url=url;
            this.fileName=fileName;
            this.start=start;
            this.end=end;
            this.handler=handler;
        }

        @Override
        public void run() {
            try {
                URL httpUrl = new URL(url);
                try {
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
                    conn.setReadTimeout(5000);
                    RandomAccessFile accessFile = new RandomAccessFile(new File(fileName), "rwd");
                    accessFile.seek(start);
                    InputStream is = conn.getInputStream();
                    byte[] b = new byte[1024 * 4];
                    int len = 0;
                    while ((len=is.read(b)) != -1) {
                        accessFile.write(b, 0, len);
                    }
                    if (accessFile != null) {
                        accessFile.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }



    }
    public void DownLoading(String url){
        try {
            URL httpUrl=new URL(url);
            try {
                HttpURLConnection conn= (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);
                int count=conn.getContentLength();
                int block=count/3;
                String fileName=getFileName(url);
                File parent= Environment.getExternalStorageDirectory();
                File fileDownLoad=new File(parent,fileName);

                for (int i=0;i<3;i++){
                    long start=i*block;
                    long end=(i+1)*block-1;
                    if(i==2){
                        end=count;
                    }
                    DownLoadRunable runable=new DownLoadRunable(url,fileDownLoad.getAbsolutePath(),start,end,handler);
                    threadPool.execute(runable);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public String getFileName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }

}
