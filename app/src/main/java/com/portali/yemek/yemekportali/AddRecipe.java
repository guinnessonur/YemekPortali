package com.portali.yemek.yemekportali;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class AddRecipe extends AppCompatActivity {

    DatabaseHelper helper;
    SQLiteDatabase db;
    private int PICK_IMAGE_REQUEST = 1;
    String selectedImagePath;

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

                if(selectedImagePath == null){
                    Toast toast = Toast.makeText(getApplicationContext(), "Select an image you must", Toast.LENGTH_SHORT);
                    toast.show();
                }


                helper.insertMeal(db, name, desc, time, ingr, "default",selectedImagePath);

                Intent bad_intention = new Intent(AddRecipe.this, SecondActivity.class);
                startActivity(bad_intention);
            }
        });
    }

    public void picImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Activity Picture"), 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
            }
        }
    }
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }


}
