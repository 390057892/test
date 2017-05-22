package com.example.administrator.socket;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class JsonAdapter extends BaseAdapter {

    private List<Person> list=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private Handler handler=new Handler();

    public JsonAdapter(List<Person> list,Context context,Handler handler){
        this.list=list;
        this.context=context;
        this.handler=handler;
        layoutInflater=layoutInflater.from(context);
    }

    public JsonAdapter(Context context){
        this.context=context;
        layoutInflater=layoutInflater.from(context);

    }
    public  void setData(List<Person> data){
        this.list=data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     *
     * @param i 视图在适配器数据中的位置
     * @param view 旧视图
     * @param viewGroup 此视图最终会被附加到的父级视图
     * @return View中的setTag(Onbect)表示给View添加一个格外的数据，以后可以用getTag()将这个数据取出来。
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder  holder=null;
        if(view==null){
            view=layoutInflater.inflate(R.layout.item,null);
            holder=new Holder(view);
            view.setTag(holder);
        }else {
            holder= (Holder) view.getTag();
        }

        Person person=list.get(i);
        holder.name.setText(person.getName());
        holder.age.setText(""+person.getAge());
        List<SchoolInfo>schoolInfos=person.getSchoolInfo();
        SchoolInfo schoolInfo1=schoolInfos.get(0);
        SchoolInfo schoolInfo2=schoolInfos.get(1);

        holder.school1.setText(schoolInfo1.getSchool_name());
        holder.school2.setText(schoolInfo2.getSchool_name());

        new HttpImage(person.getUrl(),handler,holder.image).start();

        return view;
    }

    //初始化Item元素
    class Holder{
        private TextView name;
        private TextView age;
        private TextView school1;
        private TextView school2;
        private ImageView image;

        public Holder(View view){
            name = (TextView) view.findViewById(R.id.name);
            age = (TextView) view.findViewById(R.id.age);
            school1 = (TextView) view.findViewById(R.id.school1);
            school2 = (TextView) view.findViewById(R.id.school2);
            image = (ImageView) view.findViewById(R.id.image);

        }
    }

}
