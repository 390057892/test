package com.example.administrator.socket;

import android.os.Environment;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/5/6 0006.
 */

public class UpLoad extends Thread {

    private String url;
    private String fileName;

    public UpLoad(String url,String fileName){
        this.url=url;
        this.fileName=fileName;
    }

    //原始长传方式
    @Override
    public void run() {
        String boundary="---------------------------7e1372680232";

        String prefix="--";
        String end="\r\n";

        try {
            URL httpUrl=new URL(url);
            try {
                HttpURLConnection conn= (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setReadTimeout(5000);
                //允许向服务器读取数据
                conn.setDoInput(true);
                //允许向服务器写出数据
                conn.setDoOutput(true);

                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

                DataOutputStream out=new DataOutputStream(conn.getOutputStream());
                out.writeBytes(prefix+boundary+end);
                out.writeBytes("Content-Disposition:form-data;"+"name=\"file\";filename=\""+"mao.jpg"+"\""+end);
                out.writeBytes(end);
                FileInputStream fileInputStream=new FileInputStream(new File(fileName));
                byte[] b=new byte[1024*4];
                int len;
                while ((len=fileInputStream.read(b))!=-1){
                    out.write(b,0,len);
                }
                out.writeBytes(end);
                out.writeBytes(prefix+boundary+prefix+end);
                out.flush();

                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                StringBuffer sb=new StringBuffer();
                while ((str=reader.readLine())!=null){
                    sb.append(str);
                }

                System.out.println("respose:"+sb.toString());
                if(out!=null){
                    out.close();
                }
                if(reader!=null){
                    reader.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 过时的HTTPclient上传方法
//     */
//    private void upLoadHttpClient(){
//        HttpClient client=new DefaultHttpClient();
//        HttpPost post=new HttpPost(url);
//        MultipartEntity multipartEntity=new MultipartEntity();
//        File file=Environment.getExternalStorageDirectory();
//        File abs=new File(file,"mao.jpg");
//
//
//        FileBody fileBody=new FileBody(abs);
//        multipartEntity.addPart("file",fileBody);
//        post.setEntity(multipartEntity);
//
//        try {
//            HttpResponse response=client.execute(post);
//            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
//                System.out.println(EntityUtils.toString(response.getEntity()));
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void run() {
//        upLoadHttpClient();
//    }
}


