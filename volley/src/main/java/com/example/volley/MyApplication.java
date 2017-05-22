package com.example.volley;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2017/5/7 0007.
 */

public class MyApplication extends Application {

    public static RequestQueue queue;


    @Override
    public void onCreate() {
        super.onCreate();

        queue= Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getQueue(){
        return queue;
    }


}
