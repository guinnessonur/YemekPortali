package com.portali.yemek.yemekportali;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends Activity {

    EditText editText1;
    EditText editText2;
    EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editText1=(EditText)findViewById(R.id.name2);
        editText2=(EditText)findViewById(R.id.password2);
        editText3=(EditText)findViewById(R.id.mail);




    }

    public void OnReg(View view) {
        String str_name=editText1.getText().toString();
        String str_password=editText2.getText().toString();
        String str_mail=editText3.getText().toString();
        int id=str_name.hashCode();
        String idReal=id+"";
        String type="register";

        OnlineConnection onlineConnection=new OnlineConnection(this);
        onlineConnection.execute(type,str_name,str_password,str_mail);
    }
}
