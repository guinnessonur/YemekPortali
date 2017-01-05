package com.portali.yemek.yemekportali;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by Oclemy on 5/15/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<Meal> meals;
    LayoutInflater inflater;

    public CustomAdapter(Context c, ArrayList<Meal> meals) {
        this.c = c;
        this.meals = meals;

        //INITIALIE
        inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return meals.size();
    }

    @Override
    public Object getItem(int position) {
        return meals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return meals.get(position).getTime();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.model,parent,false);
        }

        TextView nameTxt= (TextView) convertView.findViewById(R.id.nameTxt);
        TextView propellantTxt= (TextView) convertView.findViewById(R.id.propellantTxt);
        TextView descTxt= (TextView) convertView.findViewById(R.id.descTxt);

        nameTxt.setText(meals.get(position).getName());
        propellantTxt.setText(meals.get(position).getRecipe());
        descTxt.setText(meals.get(position).getType());

        //ITEM CLICKS
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,meals.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}