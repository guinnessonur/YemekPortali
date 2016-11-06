package com.portali.yemek.yemekportali;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

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
    }
}
