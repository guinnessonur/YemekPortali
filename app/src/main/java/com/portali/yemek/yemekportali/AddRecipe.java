package com.portali.yemek.yemekportali;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class AddRecipe extends AppCompatActivity {

    DatabaseHelper helper;
    SQLiteDatabase db;
    private int PICK_IMAGE_REQUEST = 1;

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

    public void picImage(View view) {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                byte [] data2 = outputStream.toByteArray();


                ContentValues cv = new ContentValues();
                cv.put("a",data2);

                db.insert("IMAGES", null, cv);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
