package com.example.administrator.socket;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by Administrator on 2017/5/4 0004.
 */

public class httpJson extends Thread {

    private String url;
    private Context context;
    private ListView listView;
    private JsonAdapter jsonAdapter;
    private Handler handler;

    public httpJson(String url,ListView listView,JsonAdapter jsonAdapter,Handler handler){
        this.url=url;
        this.listView=listView;
        this.jsonAdapter=jsonAdapter;
        this.handler=handler;
    }



    @Override
    public void run() {
        try {
            URL httpUrl=new URL(url);
            try {
                HttpURLConnection connection= (HttpURLConnection) httpUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream())) ;
                StringBuffer sb=new StringBuffer();
                String str;
                while ((str=reader.readLine())!=null){
                    sb.append(str);
                }
                final List<Person> data=parseJson(sb.toString());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        jsonAdapter.setData(data);
                        listView.setAdapter(jsonAdapter);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    private List<Person>  parseJson(String json){
        try {
            JSONObject object=new JSONObject(json);
            int result=object.getInt("result");
            List<Person> persons=new ArrayList<>();

            if(result==1){
                JSONArray personData=object.getJSONArray("personData");
                for(int i=0;i<personData.length();i++){
                    Person personObject=new Person();
                    persons.add(personObject);
                    JSONObject person=personData.getJSONObject(i);
                    String name=person.getString("name");
                    int age=person.getInt("age");
                    String url=person.getString("url");

                    personObject.setName(name);
                    personObject.setAge(age);
                    personObject.setUrl(url);

                    JSONArray schools=person.getJSONArray("school");
                    List<SchoolInfo> schoolInfos=new ArrayList<>();
                    for (int j=0;j<schools.length();j++){
                        JSONObject school=schools.getJSONObject(j);
                        String schoolName=school.getString("school_name");
                        SchoolInfo schoolInfo=new SchoolInfo();
                        schoolInfo.setSchool_name(schoolName);
                        schoolInfos.add(schoolInfo);
                    }
                    personObject.setSchoolInfo(schoolInfos);

                }
                return persons;
            }else {
                Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
