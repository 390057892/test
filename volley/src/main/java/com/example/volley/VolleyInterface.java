package com.example.volley;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2017/5/7 0007.
 */

public abstract class VolleyInterface {
    public Context context;
    public static Response.Listener<String> listener;
    public static Response.ErrorListener errorListener;
    public VolleyInterface(Context context, Response.Listener<String>listener, Response.ErrorListener errorListener){
        this.context=context;
        this.listener=listener;
        this.errorListener=errorListener;
    }

    public abstract void onMySuccess(String result);
    public abstract void onMyError(VolleyError error);


    public Response.Listener<String> LoadingListener(){
        listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onMySuccess(response);
            }
        };
        return listener;
    }

    public Response.ErrorListener merror(){
        errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            onMyError(error);
            }
        };
        return errorListener;
    }

}
