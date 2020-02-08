package com.example.almighty.navigationdrawer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sandyasravan on 3/15/2017.
 */
public class CustomAdapter extends BaseAdapter {
    ArrayList<String> showlist1;
    ArrayList<String> showlist2;
    ArrayList<String> showlist3;
    ArrayList<String> showlist4;
    Context context;
    public CustomAdapter(Show_Savings showSavings, ArrayList<String> showsavings1, ArrayList<String> showsavings2, ArrayList<String> showsavings3, ArrayList<String> showsavings4){
        context=showSavings;
        showlist1=showsavings1;
        showlist2=showsavings2;
        showlist3=showsavings3;
        showlist4=showsavings4;
    }
    @Override
    public int getCount() {
        return showlist1.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View Rowview;
        Rowview=layoutInflater.inflate(R.layout.activity_customlist,null);
        TextView tv1= (TextView) Rowview.findViewById(R.id.text1);
        TextView tv2= (TextView) Rowview.findViewById(R.id.text2);
        TextView tv3= (TextView) Rowview.findViewById(R.id.text3);
        TextView tv4= (TextView) Rowview.findViewById(R.id.text4);
        Log.i("text",showlist1.get(position));
        tv1.setText(showlist1.get(position));
        tv2.setText(showlist2.get(position));
        tv3.setText(showlist3.get(position));
        tv4.setText(showlist4.get(position));
        return Rowview;
    }
}

