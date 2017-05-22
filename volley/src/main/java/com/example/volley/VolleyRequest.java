package com.example.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/7 0007.
 */

public class VolleyRequest {

    public static StringRequest stringRequest;
    public static Context context;

    public static void RequestGet(Context context,String url,String tag,VolleyInterface volleyInterface){
        MyApplication.getQueue().cancelAll(tag);
        stringRequest=new StringRequest(Request.Method.GET, url,volleyInterface.LoadingListener(),volleyInterface.merror());
        stringRequest.setTag(tag);
        MyApplication.getQueue().add(stringRequest);
        MyApplication.getQueue().start();
    }


    public static void RequestPost(Context context, String url, String tag, Map<String,String>params ,VolleyInterface volleyInterface){
        MyApplication.getQueue().cancelAll(tag);
        stringRequest=new StringRequest(url,volleyInterface.LoadingListener(),volleyInterface.merror()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        stringRequest.setTag(tag);
        MyApplication.getQueue().add(stringRequest);
        MyApplication.getQueue().start();
    }
}
