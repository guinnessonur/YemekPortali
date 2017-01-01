package com.portali.yemek.yemekportali;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AddRecipeMySQL extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_my_sql);
    }

    public void addRecipe(View view) {
        EditText editText1=(EditText)findViewById(R.id.newName);
        EditText editText2=(EditText)findViewById(R.id.newDesc);
        EditText editText3=(EditText)findViewById(R.id.newTime);
        EditText editText4=(EditText)findViewById(R.id.rating);
        EditText editText5=(EditText)findViewById(R.id.newIng);
        Spinner editText6=  (Spinner) findViewById(R.id.type);
        EditText editText7=(EditText)findViewById(R.id.enterusername);

        String name=editText1.getText().toString();
        String recipe=editText2.getText().toString();
        String time=editText3.getText().toString();
        String rating=editText4.getText().toString();
        String ingredients=editText5.getText().toString();
        String typeM=String.valueOf(editText6.getSelectedItem());
        String enterusername=editText7.getText().toString();


        String type="addMeal";

        OnlineConnection onlineConnection=new OnlineConnection(this);
        onlineConnection.execute(type,name,recipe,time,rating,ingredients,typeM,enterusername);
    }
}
