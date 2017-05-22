package com.example.administrator.socket;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class message extends AppCompatActivity {
    private ListView listView;
    private JsonAdapter adapter;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        listView= (ListView) findViewById(R.id.list);
        adapter=new JsonAdapter(this);
        String url="http://192.168.1.101:8888/Socket/JsonServlet";

        new httpJson(url,listView,adapter,handler).start();

        listView.setAdapter(adapter);

    }
}
