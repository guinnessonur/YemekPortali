package com.portali.yemek.yemekportali;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Hello world]asgasg


    public void loginAttempt(View view) {
        EditText username= (EditText)findViewById(R.id.user_name);
        EditText password= (EditText)findViewById(R.id.user_password);
        TextView error_box= (TextView)findViewById(R.id.error_box);
        if(username.getText().toString().equals("")&&password.getText().toString().equals("")){
            Intent intent =new Intent(this,SecondActivity.class);
            startActivity(intent);
        }
        else{
            error_box.setText("shithead");
        }
    }
}
