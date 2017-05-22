package com.example.administrator.socket;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class UpLoadActivity extends AppCompatActivity {
    private Button bt7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load);
        bt7 = (Button) findViewById(R.id.button7);

        final String url="http://192.168.1.101:8888/Socket/LoadPhoto";
        File file=Environment.getExternalStorageDirectory();
        File fileABS=new File(file,"mao.jpg");

        final String filename=fileABS.getAbsolutePath();

        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpLoad upLoad=new UpLoad(url,filename);
                upLoad.start();
            }
        });
    }
}
