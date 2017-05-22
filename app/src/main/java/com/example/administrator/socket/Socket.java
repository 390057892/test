package com.example.administrator.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class Socket extends Thread{
    String url;
    String name;
    String age;

    public Socket(String url,String name,String age){
        this.url=url;
        this.age=age;
        this.name=name;
    }

    private void doGet(){
            url=url+"?name="+name+"&age="+age;
           // url=url+"?name="+ URLEncoder.encode(name,"utf-8")+"&age="+age;

        try {
            URL httpUrl=new URL(url);
            try {
                HttpURLConnection conn= (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);
                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                StringBuffer sb=new StringBuffer();
                while ((str=reader.readLine())!=null){
                       sb.append(str);
                }
                System.out.print(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void doPost(){
        try {
            URL httpUrl=new URL(url);
            try {
                HttpURLConnection conn= (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setReadTimeout(5000);
                OutputStream out =conn.getOutputStream();
                String content="name="+name+"&age="+age;
                out.write(content.getBytes());
                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb=new StringBuffer();
                String str;
                while((str=reader.readLine())!=null){
                    sb.append(str);
                }
                System.out.println(sb.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
      //doGet();
        doPost();
    }
}
