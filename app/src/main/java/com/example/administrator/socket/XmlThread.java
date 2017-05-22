package com.example.administrator.socket;

import android.os.Handler;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class XmlThread extends Thread {

    private String url;

    private TextView text;

    private Handler handler;

    public XmlThread(String url,Handler handler,TextView text){
        this.url=url;
        this.text=text;
        this.handler=handler;
    }

    @Override
    public void run() {
        try {
            URL httpUrl = new URL(url);
            try {
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);
                InputStream is=conn.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));

                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser=factory.newPullParser();
                    parser.setInput(is,"UTF-8");
                    int event=parser.getEventType();
                    final List<girl> girls=new ArrayList<>();

                    girl g=null;
                    while (event!=XmlPullParser.END_DOCUMENT){

                        String str=parser.getName();

                        switch (event){
                            case XmlPullParser.START_TAG:
                                if ("girl".equals(str)){
                                    g=new girl();
                                }
                                if("name".equals(str)){
                                    g.setName(parser.nextText());
                                }
                                if ("age".equals(str)){
                                    g.setAge(Integer.parseInt(parser.nextText()));
                                }
                                if ("school".equals(str)){
                                    g.setSchool(parser.nextText());
                                }

                                break;
                            case XmlPullParser.END_TAG:
                                if("girl".equals(str)&&g!=null){
                                    girls.add(g);
                                }

                                break;

                        }
                        event=parser.next();
                    }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(girls.toString());
                    }
                });

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
