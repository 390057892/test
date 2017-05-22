package com.example.volley;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private NetworkImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.img);
        img = (NetworkImageView) findViewById(R.id.img1);


        //LruCacheImg();
        //imgLoad();
        // volley_Get();
       // two_Get();
       // volley_Post();
        NetWorkImg();
    }

    private void NetWorkImg() {
        String url="http://192.168.1.101:8888/Socket/mao.jpg";
        ImageLoader imageLoader=new ImageLoader(MyApplication.getQueue(),new BitmapCache());
        img.setDefaultImageResId(R.mipmap.ic_launcher);
        img.setErrorImageResId(R.mipmap.ic_launcher);
        img.setImageUrl(url,imageLoader);
    }

    private void LruCacheImg() {
        String url="http://192.168.1.101:8888/Socket/mao.jpg";
        ImageLoader imageLoader=new ImageLoader(MyApplication.getQueue(),new BitmapCache());
        ImageLoader.ImageListener listner=ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        imageLoader.get(url,listner);



    }

    private void imgLoad() {
        String url="http://192.168.1.101:8888/Socket/mao.jpg";
        ImageRequest request=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        request.setTag("abcPost");
        MyApplication.getQueue().add(request);
    }


    private void two_Get() {
        String url="http://v.juhe.cn/postcode/query?postcode=215001&key=30596f95c2549d763af593fcca731f8b";
        VolleyRequest.RequestGet(this, url, "abcGet", new VolleyInterface(this,VolleyInterface.listener,VolleyInterface.errorListener) {
            @Override
            public void onMySuccess(String result) {
                Toast.makeText(MainActivity.this,result.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void volley_Post() {

        String url="http://v.juhe.cn/postcode/query?";
//        HashMap<String,String> map=new HashMap<String, String>();
//        map.put("postcode","454003");
//        map.put("key","30596f95c2549d763af593fcca731f8b");
//        JSONObject object=new JSONObject(map);
//
//        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
//            }
//        });



        //StringRequest Post请求
        StringRequest request=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> hashMap=new HashMap<String, String>();
                hashMap.put("postcode","215001");
                hashMap.put("key","30596f95c2549d763af593fcca731f8b");

                return hashMap;
            }
        };
        request.setTag("abcPost");
        MyApplication.getQueue().add(request);
    }

    private void volley_Get() {
        String url="http://v.juhe.cn/postcode/query?postcode=215001&key=30596f95c2549d763af593fcca731f8b";
//StringRequest Get请求
//        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
//            }
//        });
//        request.setTag("abcGET");
//        MyApplication.getQueue().add(request);

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag("abcGET");
        MyApplication.getQueue().add(request);
    }


    //与Activity生命周期关联
    @Override
    protected void onStop() {
        //Activity关闭后，执行CancelAll关闭指定TAG
        MyApplication.getQueue().cancelAll("abcGET");

    }




}
