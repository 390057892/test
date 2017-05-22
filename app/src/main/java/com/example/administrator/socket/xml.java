package com.example.administrator.socket;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class xml extends AppCompatActivity {
    private TextView textView;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        textView = (TextView) findViewById(R.id.xmltext);
        String url="http://192.168.1.101:8888/Socket/girls.xml";
        XmlThread thread= new XmlThread(url,handler,textView);
        thread.start();

    }
}
