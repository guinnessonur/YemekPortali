package com.portali.yemek.yemekportali;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper  {

    private static final String DB_NAME="GROCERYLIST";
    private static final int DB_VERSION=1;
    public static Cursor cursor;

    public DatabaseHelper(Context context){

        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE GROCERYLIST (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
        "element TEXT, "+
        "state INTEGER);");
        db.execSQL("CREATE TABLE MEAL (id INTEGER PRIMARY KEY, "+
                "name TEXT, "+
                "recipe TEXT, "+
                "time INTEGER, "+
                "ingredients TEXT, "+
                "type TEXT, "+
                "path TEXT);");
        db.execSQL("CREATE TABLE FAVORITES (mealid INTEGER PRIMARY KEY, "+
                "time INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static void insertGrocery(SQLiteDatabase db, String element,int state){
        ContentValues contactValues=new ContentValues();
        contactValues.put("element",element);
        contactValues.put("state",state);
        db.insert("GROCERYLIST",null,contactValues);
    }
    public static void insertMeal(SQLiteDatabase db, String name, String recipe, int time, String ingredients, String type,String path){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("recipe", recipe);
        contentValues.put("time", time);
        contentValues.put("ingredients", ingredients);
        contentValues.put("type", type);
        contentValues.put("path", path);
        db.insert("MEAL", null, contentValues);
    }
    public static Cursor showItems(SQLiteDatabase db, Context context){
        cursor=db.query("GROCERYLIST",new String[]{"element","state"},null,null,null,null,null);
        return cursor;
    }
    public static Cursor showMeals(SQLiteDatabase db, Context context){
        cursor = db.query("MEAL", new String[]{"name", "recipe", "time", "ingredients", "type","path"}, null, null, null, null, null);
        return cursor;
    }
    public void update(SQLiteDatabase db,String element,String state,int phoneNumber){
        db.execSQL("Update GROCERYLIST SET element='"+element+"', state = '"+state+"' WHERE phoneNumber="+phoneNumber+" ;");
    }
    public static void deleteGrocery(SQLiteDatabase db,String element){
        db.execSQL("delete from "+"GROCERYLIST"+" where element = '"+element+"' ;");
    }

}
