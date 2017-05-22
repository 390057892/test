package com.example.administrator.socket;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DownLoadActivity extends AppCompatActivity {
    private ImageView img;
    private Button b5;
    private TextView textView;
    private int count=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int result=msg.what;
            count+=result;
            if(count==3){
                textView.setText("下载成功");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);

        img = (ImageView) findViewById(R.id.downloadimg);
        b5 = (Button) findViewById(R.id.button5);
        textView= (TextView) findViewById(R.id.textView2);

        b5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        DownLoad downLoad=new DownLoad(handler);
                        downLoad.DownLoading("http://192.168.1.101:8888/Socket/mao.jpg");
                    }
                }.start();

            }
        });



//        String url="http://192.168.1.101:8888/Socket/mao.jpg";
//        DownLoad downLoad=new DownLoad(url,handler,img);
//        downLoad.start();


    }
}
