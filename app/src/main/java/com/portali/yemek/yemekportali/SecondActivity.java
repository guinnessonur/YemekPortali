package com.portali.yemek.yemekportali;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.Date;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.HORIZONTAL;

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
    private int countB=0;

    static Context contex;

    public void showFavorites(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.favLay);
        layout.removeAllViews();
        Cursor cursorMeals = helper.showMeals(db, contex);
        int[] mealIds = new int[cursorMeals.getCount()];
        cursorMeals.moveToFirst();
        for(int i = 0; i < cursorMeals.getCount(); i++){
            mealIds[i] = Integer.parseInt(cursorMeals.getString(6));
            cursorMeals.moveToNext();
        }
        Cursor cursor = helper.showFavorites(db, contex);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            final int mealid = Integer.parseInt(cursor.getString(0));
            Log.v("mealid", mealid+"");
            int pos = -1;
            for(int f = 0; f < mealIds.length; f++){
                if(mealIds[f] == mealid){
                    pos = f;
                    break;
                }
            }
            if(pos==-1){
                return;
            }
            cursorMeals.moveToFirst();
            cursorMeals.moveToPosition(pos);
            String name = cursorMeals.getString(0);
            String recipe = cursorMeals.getString(1);
            int time = Integer.parseInt(cursorMeals.getString(2));
            String ingredients = cursorMeals.getString(3);
            LinearLayout l1 = new LinearLayout(this);
            TextView t1 = new TextView(this);
            ImageButton i1 = new ImageButton(this); // Star button will be
            i1.setScaleX(0.5f);
            i1.setScaleY(0.5f);
            i1.setImageResource(R.drawable.star_on);
            i1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFromFavorites(mealid);
                }
            });
            LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(70, 70);
            imageParam.gravity=Gravity.END;
            i1.setLayoutParams(imageParam);
            l1.setOrientation(HORIZONTAL);
            t1.setTextSize(18);
            t1.setText(name);
            l1.addView(t1);
            l1.addView(i1);
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
            img.setImageURI(Uri.parse(cursorMeals.getString(5)));
            LinearLayout.LayoutParams imageParamss = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            img.setLayoutParams(imageParamss);
            l4.addView(img);
            l3.setLayoutParams(leftParam);
            l4.setLayoutParams(rightParam);
            l2.addView(l3);
            l2.addView(l4);
            layout.addView(l2);
            cursor.moveToNext();
        }
        cursor.close();
        cursorMeals.close();
    }

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
            Cursor favCursor = helper.showFavorites(db, contex);
            int[] idArray = new int[favCursor.getCount()];
            favCursor.moveToFirst();
            for(int i = 0; i < favCursor.getCount(); i++){
                idArray[i] = Integer.parseInt(favCursor.getString(0));
                favCursor.moveToNext();
            }
            Cursor cursor = helper.showMeals(db, contex);
            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++){
                String name = cursor.getString(0);
                String recipe = cursor.getString(1);
                int time = Integer.parseInt(cursor.getString(2));
                String ingredients = cursor.getString(3);
                String type = cursor.getString(4); // Do nothing with it
                final int aydi = Integer.parseInt(cursor.getString(6));
                LinearLayout l1 = new LinearLayout(this);
                TextView t1 = new TextView(this);
                ImageButton i1 = new ImageButton(this); // Star button will be
                i1.setScaleX(0.5f);
                i1.setScaleY(0.5f);
                boolean isFavorited = false;
                for(int f = 0; f < idArray.length; f++){
                 if(idArray[f] == aydi){
                     isFavorited = true;
                     break;
                 }
                }
                if(isFavorited){
                    i1.setImageResource(R.drawable.star_on);
                    i1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeFromFavorites(aydi);
                        }
                    });
                }
                else{
                    i1.setImageResource(R.drawable.star_off);
                    i1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addToFavorites(aydi);
                        }
                    });
                }
                LinearLayout.LayoutParams imageParam = new LinearLayout.LayoutParams(70, 70);
                imageParam.gravity=Gravity.END;
                i1.setLayoutParams(imageParam);
                l1.setOrientation(HORIZONTAL);
                t1.setTextSize(18);
                t1.setText(name);
                l1.addView(t1);
                l1.addView(i1);
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
                LinearLayout.LayoutParams imageParamss = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                img.setLayoutParams(imageParamss);
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
    void addToFavorites(int aydi){
        helper.insertFavorite(db, aydi, 0);
        showRecipe(1);
        showFavorites();
    }
    void removeFromFavorites(int aydi){
        helper.deleteFavorite(db, aydi);
        showRecipe(1);
        showFavorites();
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
        viewGroceryList();
        showFavorites();

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

    public void viewGroceryList(){
        LinearLayout layout = (LinearLayout) (findViewById(R.id.layout));
        layout.removeAllViews();
        Cursor cursor = helper.showItems(db, contex);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            LinearLayout container = new LinearLayout(contex);
            EditText editText = new EditText(contex);
            LinearLayout.LayoutParams editTextParameters = new LinearLayout.LayoutParams(width-width/5,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            CheckBox checkBox = new CheckBox(contex);
            checkBox.setVisibility(CheckBox.INVISIBLE);
            editText.setLayoutParams(editTextParameters);
            editText.setText(cursor.getString(0));
            editText.setFocusable(false);
            container.addView(editText);
            container.addView(checkBox);
            layout.addView(container);
            cursor.moveToNext();
        }
    }

    public void addGroceries(View view) {
        LinearLayout layout = (LinearLayout) (findViewById(R.id.layout));
        layout.removeAllViews();
        //DONE AND NEW BUTTON
        Button doneButton = new Button(this);
        LinearLayout.LayoutParams doneButtonParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        doneButtonParameters.gravity=Gravity.END;
        doneButtonParameters.setMargins(20, 20, 20, 20);
        doneButton.setText("Done");
        doneButton.setLayoutParams(doneButtonParameters);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) (findViewById(R.id.layout));
                for(int i = 0; i < layout.getChildCount(); i++){
                    View child = layout.getChildAt(i);
                    if(child instanceof LinearLayout){
                        EditText editText = (EditText)((LinearLayout) child).getChildAt(0);
                        String text = editText.getText().toString();
                        if(!text.equalsIgnoreCase("")) {
                            helper.insertGrocery(db, text, 0);
                        }
                    }
                }
                viewGroceryList();
            }
        });

        Button newButton = new Button(this);
        LinearLayout.LayoutParams newButtonParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        newButtonParameters.gravity=Gravity.START;
        newButtonParameters.setMargins(20, 20, 20, 20);
        newButton.setText("New");
        newButton.setLayoutParams(newButtonParameters);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                LinearLayout layout = (LinearLayout) (findViewById(R.id.layout));
                LinearLayout container = new LinearLayout(contex);
                EditText editText = new EditText(contex);
                LinearLayout.LayoutParams editTextParameters = new LinearLayout.LayoutParams(width-width/5,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                CheckBox checkBox = new CheckBox(contex);
                checkBox.setVisibility(CheckBox.INVISIBLE);
                editText.setLayoutParams(editTextParameters);
                container.addView(editText);
                container.addView(checkBox);
                layout.addView(container);
            }
        });

        // ADDING ITEMS TO THE LAYOUT
        layout.addView(doneButton);
        layout.addView(newButton);

    }

    public void deleteGroceries(View view) {
        LinearLayout layout = (LinearLayout) (findViewById(R.id.layout));
        layout.removeAllViews();
        Button doneButton = new Button(this);
        LinearLayout.LayoutParams doneButtonParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        doneButtonParameters.gravity=Gravity.END;
        doneButtonParameters.setMargins(20, 20, 20, 20);
        doneButton.setText("DELET DIIIS");
        doneButton.setLayoutParams(doneButtonParameters);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) (findViewById(R.id.layout));
                for(int i = 0; i < layout.getChildCount(); i++){
                    View child = layout.getChildAt(i);
                    if(child instanceof LinearLayout){
                        CheckBox checkBox = (CheckBox) ((LinearLayout) child).getChildAt(1);
                        if(checkBox.isChecked()) {
                            EditText editText = (EditText) ((LinearLayout) child).getChildAt(0);
                            String text = editText.getText().toString();
                            helper.deleteGrocery(db, text);
                        }
                    }
                }
                viewGroceryList();
            }
        });
        layout.addView(doneButton);
        Cursor cursor = helper.showItems(db, contex);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            LinearLayout container = new LinearLayout(contex);
            EditText editText = new EditText(contex);
            LinearLayout.LayoutParams editTextParameters = new LinearLayout.LayoutParams(width-width/5,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            CheckBox checkBox = new CheckBox(contex);
            checkBox.setVisibility(CheckBox.VISIBLE);
            editText.setLayoutParams(editTextParameters);
            editText.setText(cursor.getString(0));
            editText.setFocusable(false);
            container.addView(editText);
            container.addView(checkBox);
            layout.addView(container);
            cursor.moveToNext();
        }
    }

    public void show(View view) {
        viewGroceryList();
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
