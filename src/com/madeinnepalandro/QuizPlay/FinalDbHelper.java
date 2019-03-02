package com.madeinnepalandro.QuizPlay;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by munchymahesh on 2/1/16.
 */
public class FinalDbHelper extends SQLiteOpenHelper {
    private static final String TAG="SQLiteOpenHelper";
    private final Context context;
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="db_quizplay.sqlite";
    private boolean createDb=false,upgradeDb=false;

    public FinalDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,"onCreate db");
        createDb=true;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"onUpgrade db");
        upgradeDb=true;

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i(TAG,"onOpen db");
        if(createDb){
            createDb=false;
            copyDatabaseFromAssets(db);
        }
        if(upgradeDb){
            upgradeDb=false;
        }

        super.onOpen(db);
    }


    //user defined methods
    private void copyDatabaseFromAssets(SQLiteDatabase db){
        Log.i(TAG,"copyDatabase");
        InputStream myInput=null;
        OutputStream myOutput=null;
        try{
            myInput=context.getAssets().open(DATABASE_NAME);
            myOutput= new FileOutputStream(db.getPath());
            byte[] buffer = new byte[1024];
            int length;
            while((length=myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();

            SQLiteDatabase copiedDb=context.openOrCreateDatabase(DATABASE_NAME,0,null);
            copiedDb.execSQL("PRAGMA user_version="+DATABASE_VERSION);
            copiedDb.close();
        }catch (IOException e){
            e.printStackTrace();
            throw new Error(TAG+"Error copying Database");
        }finally {
            try{
                if(myOutput!=null){
                    myOutput.close();
                }
                if(myInput!=null){
                    myInput.close();
                }
            }catch (IOException e){
                e.printStackTrace();
                throw new Error(TAG+"Error closing Streams");

            }
        }
    }


    public Cursor getAllData(){
        SQLiteDatabase db= this.getReadableDatabase();
        String qrySel="SELECT * FROM tbl_questions";
        Cursor res=db.rawQuery(qrySel,null);
        return res;
    }



}
