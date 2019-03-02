package com.madeinnepalandro.QuizPlay;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.*;
import java.sql.SQLDataException;
import java.sql.SQLException;

/**
 * Created by munchymahesh on 1/29/16.
 */
class MyDbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase myDb;

    public SQLiteDatabase db;
    public final Context context;
    private static String DATABASE_NAME="db_quizplay.sqlite";
    private static String DATABASE_PATH;
    public static final int DATABASE_VERSION=1;
    public static final int DATABASE_VERSION_old=1;
    public SQLiteDatabase database=null;

   public SQLiteDatabase getDb(){
       return database;

   }
    public MyDbHelper(Context context,String databaseName) {
        super(context, databaseName, null, 1);
        this.context = context;

        String packageName = context.getPackageName();
        DATABASE_PATH = String.format("/data/data/%s/databases/", packageName);
        DATABASE_NAME = databaseName;

        boolean dbFlag = checkDb();
        if (dbFlag == false) {
            //copy database
            try {
                copyDb();
            } catch (IOException e) {
                Log.d(null, "Error while copying database"+e);
            }
        } else {
            //open the existing database
            try{
                openDb();
            }catch (SQLException e){
                Log.d(null,"Error while opening database"+e);
            }


        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public Cursor getAllData(){
        SQLiteDatabase db= this.getReadableDatabase();
        String qrySel="SELECT * FROM tbl_questions";
        Cursor res=db.rawQuery(qrySel,null);
        return res;
    }

    public boolean checkDb(){
        SQLiteDatabase check=null;
        String path=DATABASE_PATH+DATABASE_NAME;
        //check=context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);

        try{
            check=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e){
            check=null;
        }
        if(check!=null){
            return true;
        }
        else{
            return false;
        }

    }

    public void copyDb() throws IOException{
        InputStream extDbStream= context.getAssets().open(DATABASE_NAME);
        String outFileName= DATABASE_PATH+DATABASE_NAME;
        OutputStream localDbStream= new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while((bytesRead=extDbStream.read(buffer))>0){
            localDbStream.write(buffer,0,bytesRead);
        }

        extDbStream.close();
    }

    public void openDb() throws SQLException{
        String path= DATABASE_PATH+DATABASE_NAME;
        SQLiteDatabase db=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
        if(db!=null){
            //open successfull
        }else{
            //error opening database
            Log.d(null,"Error opening database");
        }
    }
}
