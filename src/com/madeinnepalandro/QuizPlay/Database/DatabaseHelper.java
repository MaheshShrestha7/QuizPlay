package com.madeinnepalandro.QuizPlay.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by munchymahesh on 1/20/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="db_quizplay.sqlite";
    public static  String DATABASE_PATH;
    public static final String TABLE_NAME="tbl_questions";
    public static final String col1="QNo";
    public static final String col2="Question";
    public static final String col3="OptionA";
    public static final String col4="OptionB";
    public static final String col5="OptionC";
    public static final String col6="OptionD";
    public static final String col7="RightAnswer";
    public static final String col8="Category";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
        DATABASE_PATH=context.getDatabasePath(DATABASE_NAME).toString();

        //SQLiteDatabase db= this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qryCreate="CREATE TABLE "+TABLE_NAME +" (QNo INTEGER PRIMARY KEY AUTOINCREMENT,Question TEXT,OptionA TEXT,OptionB TEXT,OptionC TEXT,OptionD TEXT,RightAnswer TEXT,Category TEXT)";
        db.execSQL(qryCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    //user defined methods
    public boolean insertData(String question,String a,String b,String c,String d,String rightAns,String category){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentVals= new ContentValues();
        contentVals.put(col2,question);
        contentVals.put(col3,a);
        contentVals.put(col4,b);
        contentVals.put(col5,c);
        contentVals.put(col6,d);
        contentVals.put(col7,rightAns);
        contentVals.put(col8,category);

        long res=db.insert(TABLE_NAME,null,contentVals);

        if(res == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db= this.getReadableDatabase();
        String qrySel="SELECT * FROM "+TABLE_NAME;
        Cursor res=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }
}
