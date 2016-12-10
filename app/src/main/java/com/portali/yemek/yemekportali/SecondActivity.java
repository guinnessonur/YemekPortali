package com.portali.yemek.yemekportali;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static android.R.attr.height;
import static android.R.attr.width;

/**
 * Created by GURKAN32 on 10/21/2016.
 */


public class SecondActivity extends Activity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Intent activityThatCalled=getIntent();

        TabHost th =(TabHost) findViewById(R.id.tabhost);
        th.setup();
        TabHost.TabSpec specs=th.newTabSpec("tag1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Yemek Paylaş");
        th.addTab(specs);

        specs=th.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Rastgele Yemek");
        th.addTab(specs);

        specs=th.newTabSpec("tag3");
        specs.setContent(R.id.tab3);
        specs.setIndicator("Alış-Veriş Listesi");
        th.addTab(specs);

        specs=th.newTabSpec("tag4");
        specs.setContent(R.id.tab4);
        specs.setIndicator("Favoriler");
        th.addTab(specs);

        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,View itemView,int position,long id){
                if(position==0){
                    //TO DO
                }
            }
        };






    }

    public void addGroceries(View view) {
        EditText editText=(EditText)(findViewById(R.id.itemName));

    }

    public void deleteGroceries(View view) {


    }

    public void randomizer(View view) {
        Random r=new Random();
        int x=r.nextInt(5);
        TextView textView=(TextView)findViewById(R.id.random);
        textView.setText(x+"");

    }



}
