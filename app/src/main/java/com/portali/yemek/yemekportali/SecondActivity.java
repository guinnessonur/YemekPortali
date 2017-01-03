package com.portali.yemek.yemekportali;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static android.R.attr.content;
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

    DatabaseHelper helper;
    SQLiteDatabase db;

    String list_url="http://10.0.2.2/list.php";
    String listMeals_url="http://10.0.2.2/listMeal.php";

    int position1;

    private int countL=0;
    private int countC=0;

    static Context contex;

    public void showRecipe(int x){
        LinearLayout layout = (LinearLayout) findViewById(R.id.recipes);
        layout.removeAllViews();
        if(x==0){

            ListView lv2= new ListView(this);

            OnlineConnectionThree onlineConnectionThree=new OnlineConnectionThree(this,listMeals_url,lv2);
            onlineConnectionThree.execute();
            layout.addView(lv2);


        }
        else{

            Cursor cursor = helper.showMeals(db, contex);
            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++){
                String name = cursor.getString(0);
                String recipe = cursor.getString(1);
                int time = Integer.parseInt(cursor.getString(2));
                String ingredients = cursor.getString(3);
                String type = cursor.getString(4); // Do nothing with it
                LinearLayout l1 = new LinearLayout(this);
                TextView t1 = new TextView(this);
                ImageButton i1 = new ImageButton(this); // Star button will be
                l1.setOrientation(HORIZONTAL);
                t1.setTextSize(18);
                t1.setText(name);
                l1.addView(t1);
                layout.addView(l1);
                RelativeLayout l2 = new RelativeLayout(this);
                LinearLayout l3 = new LinearLayout(this);
                l3.setOrientation(LinearLayout.VERTICAL);
                LinearLayout l4 = new LinearLayout(this);
                l4.setOrientation(LinearLayout.VERTICAL);
                int imageDp = (int) getResources().getDimension(R.dimen.dpImage);
                int textDp = (int) getResources().getDimension(R.dimen.dpText);
                RelativeLayout.LayoutParams rightParam = new RelativeLayout.LayoutParams(imageDp, imageDp);
                RelativeLayout.LayoutParams leftParam = new RelativeLayout.LayoutParams(textDp, WRAP_CONTENT);
                rightParam.addRule(RelativeLayout.ALIGN_PARENT_END);
                TextView nameText = new TextView(this);
                nameText.setText("Ingredients: " +  ingredients);
                l3.addView(nameText);
                TextView timeText = new TextView(this);
                timeText.setText("Preperation Time: " + time);
                l3.addView(timeText);
                TextView recipeText = new TextView(this);
                recipeText.setText(recipe);
                l3.addView(recipeText);
                ImageView img = new ImageView(this);
                img.setImageURI(Uri.parse(cursor.getString(5)));


                img.setMaxHeight(imageDp);
                img.setMaxWidth(imageDp);
                l4.addView(img);
                l3.setLayoutParams(leftParam);
                l4.setLayoutParams(rightParam);
                l2.addView(l3);
                l2.addView(l4);
                layout.addView(l2);
                cursor.moveToNext();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        contex = getApplicationContext();

        helper=new DatabaseHelper(this);
        db=helper.getWritableDatabase();

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

        showRecipe(1);

        ImageButton imgBtn = (ImageButton) findViewById(R.id.addRecipeButton);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position1==1){
                    Intent bad_intent = new Intent(SecondActivity.this, AddRecipe.class);
                    startActivity(bad_intent);
                }
                else{
                    Intent bad_intent = new Intent(SecondActivity.this, AddRecipeMySQL.class);
                    startActivity(bad_intent);
                }
            }
        });


        Spinner spin = (Spinner) findViewById(R.id.spinna);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showRecipe(position);
                position1=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,View itemView,int position,long id){
                if(position==0){
                    //TO DO
                }
            }
        };
    }

    public void addGroceries(View view) {

        int state=0;

        String s1;
        s1="i"+countC;
        int i=hash(s1);
        String s2;
        s2="l"+countC;
        int l=hash(s2);
        String s3;
        s3="e"+countC;
        int e=hash(s3);

        LinearLayout linearLayout=(LinearLayout) (findViewById(R.id.layout));

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT);

        LinearLayout linearLayout1=new LinearLayout(this);

        linearLayout1.setOrientation(HORIZONTAL);
        linearLayout1.setId(l);

        EditText editText=new EditText(this);
        editText.setId(e);
        CheckBox checkBox=new CheckBox(this);
        checkBox.setId(i);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);

        linearLayout1.addView(checkBox,lp2);
        linearLayout1.addView(editText,lp2);

        linearLayout.addView(linearLayout1,lp1);

        countC++;
        countL++;

        if(checkBox.isChecked()) {
            state = 1;
        }
        helper.insertGrocery(db,editText.getText().toString(),state);
    }

    public void deleteGroceries(View view) {
        String s1;
        String s2;
        String s3;

        LinearLayout linearLayout;
        EditText editText;

        for(int k =0;k<countC;k++){
            s1="i"+k;
            int i=hash(s1);
            CheckBox checkBox=(CheckBox)findViewById(i);
            if(checkBox != null)
            if(checkBox.isChecked()) {
                s2="l"+k;
                int l=hash(s2);

                s3="e"+k;
                int e=hash(s3);

                editText=(EditText)findViewById(e);
                helper.deleteGrocery(db,editText.getText().toString());

                View myView = findViewById(l);
                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);

                countC--;
                countL--;

            }
        }
    }

    public void show(View view) {
        Cursor cursor = helper.showItems(db, getApplicationContext());
        cursor.moveToFirst();
        for(int j = 0; j < cursor.getCount(); j++){
            String element = cursor.getString(0);
            String state2 = cursor.getString(1);
            int state=Integer.parseInt(state2);

            String s1;
            s1="i"+countC;
            int i=hash(s1);
            String s2;
            s2="l"+countC;
            int l=hash(s2);
            String s3;
            s3="e"+countC;
            int e=hash(s3);

            LinearLayout linearLayout=(LinearLayout) (findViewById(R.id.layout));

            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT);

            LinearLayout linearLayout1=new LinearLayout(this);

            linearLayout1.setOrientation(HORIZONTAL);
            linearLayout1.setId(l);

            EditText editText=new EditText(this);
            editText.setId(e);
            CheckBox checkBox=new CheckBox(this);
            checkBox.setId(i);

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);

            linearLayout1.addView(checkBox,lp2);
            linearLayout1.addView(editText,lp2);

            linearLayout.addView(linearLayout1,lp1);

            countC++;
            countL++;

            cursor.moveToNext();
        }


    }

    public void randomizer(View view) {
        TextView textView=(TextView)findViewById(R.id.random);
        Spinner spinner=(Spinner)findViewById(R.id.typeMeal);
        String typeM=String.valueOf(spinner.getSelectedItem());

        ListView lv= (ListView) findViewById(R.id.lv);
        OnlineConnectionTwo onlineConnectionTwo=new OnlineConnectionTwo(this,list_url,lv);
        onlineConnectionTwo.execute(typeM);
    }
}
