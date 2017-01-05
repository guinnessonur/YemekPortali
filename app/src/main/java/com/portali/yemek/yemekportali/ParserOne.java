package com.portali.yemek.yemekportali;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Oclemy on 5/15/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class ParserOne  extends AsyncTask<Void,Void,Integer>{

    Context c;
    ListView lv;
    String jsonData;

    ProgressDialog pd;
    ArrayList<Meal> meals=new ArrayList<>();

    public ParserOne(Context c, ListView lv, String jsonData) {
        this.c = c;
        this.lv = lv;
        this.jsonData = jsonData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Parsing...Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        pd.dismiss();
        if(result==0)
        {
            Toast.makeText(c,"Unable to parse",Toast.LENGTH_SHORT).show();
        }else {
            //CALL ADAPTER TO BIND DATA
            CustomAdapter adapter=new CustomAdapter(c,meals);
            lv.setAdapter(adapter);
        }
    }

    private int parseData()
    {
        try {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo=null;

            meals.clear();
            Meal s=null;

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                s=new Meal();

                String name=jo.getString("name");
                String recipe=jo.getString("recipe");
                int time=jo.getInt("time");
                int rating=jo.getInt("rating");
                String ingredients=jo.getString("ingredients");
                String type=jo.getString("type");



                s.setName(name);
                s.setRecipe(recipe);
                s.setTime(time);
                s.setRating(rating);
                s.setIngredients(ingredients);
                s.setType(type);

                meals.add(s);


            }

            return 1;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
