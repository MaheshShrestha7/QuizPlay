package com.madeinnepalandro.QuizPlay;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.madeinnepalandro.QuizPlay.Database.DatabaseHelper;

/**
 * Created by munchymahesh on 1/20/16.
 */
public class AddQuestion extends Activity {
    Button btnAdd;
    EditText txtQ,txtOptA,txtOptB,txtOptC,txtoptD,txtRightAns;
    DatabaseHelper mydb;
    Spinner listCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_questions);

        mydb=new DatabaseHelper(this);
        getAllComponents();
        addDataToDb();



    }
    public void getAllComponents(){
        txtQ=(EditText)findViewById(R.id.txtQuestion);
        txtOptA=(EditText)findViewById(R.id.txtOptionA);
        txtOptB=(EditText)findViewById(R.id.txtOptionB);
        txtOptC=(EditText)findViewById(R.id.txtOptionC);
        txtoptD=(EditText)findViewById(R.id.txtOptionD);
        txtRightAns=(EditText)findViewById(R.id.txtRightOption);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        listCategories=(Spinner)findViewById(R.id.listCategory);
    }

    private void addDataToDb() {
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean isInserted=mydb.insertData(
                                            txtQ.getText().toString()
                                            ,txtOptA.getText().toString()
                                            ,txtOptB.getText().toString()
                                            ,txtOptC.getText().toString()
                                            ,txtoptD.getText().toString()
                                            ,txtRightAns.getText().toString()
                                            ,listCategories.getSelectedItem().toString()
                                        );
                        if(isInserted==true){
                            Toast.makeText(AddQuestion.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(AddQuestion.this," ERROR   Something went Wrong!!!",Toast.LENGTH_LONG ).show();
                        }
                    }
                }
        );
    }



}
