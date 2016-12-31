package com.portali.yemek.yemekportali;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by GURKAN32 on 12/27/2016.
 */


public class OnlineConnection extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    OnlineConnection (Context ctx){
        context=ctx;
    }
    int uid;
    int mid;



    @Override
    protected String doInBackground(String... params) {
        String type= params[0];
        String login_url="http://10.0.2.2/login.php";
        String register_url="http://10.0.2.2/register.php";
        String addMeal_url="http://10.0.2.2/addMeal.php";
        if(type.equals("login")){
            try {
                String user_name= params[1];
                String password= params[2];
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line=bufferedReader.readLine())!=null){

                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("register")){
            try {
                String user_name= params[1];
                String user_password= params[2];
                String user_mail= params[3];
                uid=Math.abs(user_name.hashCode());
                URL url=new URL(register_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data=
                        URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(Integer.toString(uid),"UTF-8")+"&"
                        +URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("user_password","UTF-8")+"="+URLEncoder.encode(user_password,"UTF-8")+"&"
                        +URLEncoder.encode("user_mail","UTF-8")+"="+URLEncoder.encode(user_mail,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line=bufferedReader.readLine())!=null){

                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();



                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("addMeal")){
            try {
                String name= params[1];
                String recipe= params[2];
                String time= params[3];
                String rating= params[4];
                String ingredients= params[5];
                String typeM= params[6];
                mid=Math.abs(name.hashCode());

                URL url=new URL(addMeal_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data=
                        URLEncoder.encode("meal_id","UTF-8")+"="+URLEncoder.encode(Integer.toString(mid),"UTF-8")+"&"
                                +URLEncoder.encode("meal_name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                                +URLEncoder.encode("meal_recipe","UTF-8")+"="+URLEncoder.encode(recipe,"UTF-8")+"&"
                                +URLEncoder.encode("meal_time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&"
                                +URLEncoder.encode("meal_rating","UTF-8")+"="+URLEncoder.encode(rating,"UTF-8")+"&"
                                +URLEncoder.encode("meal_ingredients","UTF-8")+"="+URLEncoder.encode(ingredients,"UTF-8")+"&"
                                +URLEncoder.encode("meal_type","UTF-8")+"="+URLEncoder.encode(typeM,"UTF-8")+"&"
                                +URLEncoder.encode("meal_userid","UTF-8")+"="+URLEncoder.encode(Integer.toString(uid),"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line=bufferedReader.readLine())!=null){

                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();



                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals(" login success")){
            Intent i = new Intent(context,SecondActivity.class);
            context.startActivity(i);
        }
        else {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
