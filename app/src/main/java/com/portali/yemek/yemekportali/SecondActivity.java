package com.portali.yemek.yemekportali;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static android.R.attr.height;
import static android.R.attr.id;
import static android.R.attr.width;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.HORIZONTAL;
import static java.util.Objects.hash;

/**
 * Created by GURKAN32 on 10/21/2016.
 */


public class SecondActivity extends Activity {

    private int countL=0;
    private int countC=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Intent activityThatCalled=getIntent();

        TabHost th =(TabHost) findViewById(R.id.tabhost);
        th.setup();
        TabHost.TabSpec specs=th.newTabSpec("tag1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Share Recipe");
        th.addTab(specs);

        specs=th.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Random Meal");
        th.addTab(specs);

        specs=th.newTabSpec("tag3");
        specs.setContent(R.id.tab3);
        specs.setIndicator("Grocery List");
        th.addTab(specs);

        specs=th.newTabSpec("tag4");
        specs.setContent(R.id.tab4);
        specs.setIndicator("Favorites");
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
        String s1;
        s1="i"+countC;
        int i=hash(s1);
        String s2;
        s2="l"+countC;
        int l=hash(s2);

        LinearLayout linearLayout=(LinearLayout) (findViewById(R.id.layout));


        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT);

        LinearLayout linearLayout1=new LinearLayout(this);

        int resID = getResources().getIdentifier("i"+countC, "id", getPackageName());
        int res2ID = getResources().getIdentifier("l"+countL, "id2", getPackageName());

        linearLayout1.setOrientation(HORIZONTAL);
        linearLayout1.setId(l);


        EditText editText=new EditText(this);
        CheckBox checkBox=new CheckBox(this);
        checkBox.setId(i);


        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);


        linearLayout.addView(checkBox,lp2);
        linearLayout.addView(editText,lp2);


        linearLayout.addView(linearLayout1,lp1);


        countC++;
        countL++;


    }

    public void deleteGroceries(View view) {
        String s1;

        String s2;


        for(int k =0;k<countC;k++){
            s1="i"+k;
            int i=hash(s1);
            int resID = getResources().getIdentifier("i"+k, "id", getPackageName());
            CheckBox checkBox=(CheckBox)findViewById(i);
            if(checkBox.isChecked()) {
                s2="l"+k;
                int l=hash(s2);
                int res2ID = getResources().getIdentifier("l"+k, "id2", getPackageName());
                View myView = findViewById(l);
                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);

            }

        }

    }

    public void randomizer(View view) {
        Random r=new Random();
        int x=r.nextInt(5);
        TextView textView=(TextView)findViewById(R.id.random);
        textView.setText(x+"");

    }



}
