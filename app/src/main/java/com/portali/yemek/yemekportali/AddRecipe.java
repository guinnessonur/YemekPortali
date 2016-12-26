package com.portali.yemek.yemekportali;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddRecipe extends AppCompatActivity {

    DatabaseHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        helper=new DatabaseHelper(this);
        db=helper.getWritableDatabase();

        Button btn = (Button) findViewById(R.id.doneButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.newName)).getText().toString();
                String desc = ((EditText) findViewById(R.id.newDesc)).getText().toString();
                int time = Integer.parseInt(((EditText) findViewById(R.id.newTime)).getText().toString());
                String ingr = ((EditText) findViewById(R.id.newIng)).getText().toString();
                helper.insertMeal(db, name, desc, time, ingr, "default");

                Intent bad_intention = new Intent(AddRecipe.this, SecondActivity.class);
                startActivity(bad_intention);
            }
        });
    }
}
