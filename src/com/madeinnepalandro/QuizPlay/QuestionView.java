package com.madeinnepalandro.QuizPlay;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.madeinnepalandro.QuizPlay.Database.DatabaseHelper;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by munchymahesh on 1/20/16.
 */
public class QuestionView extends Activity implements View.OnClickListener {
    public String Question;
    public String OptionA,OptionB,OptionC,OptionD;
    public String RightAnswer;
    public String Category;
    String number;
    public int qNo;
    public Button layoutQuestion,lOptionA,lOptionB,lOptionC,lOptionD;
    public TextView lQuestionNumer;
    public ProgressBar lTimeProgress;

    public FinalDbHelper mydb;
    public Cursor dataCursor=null;

    public static final String DATABASE_NAME="db_quizplay.sqlite";
    private SQLiteDatabase database;


    public int points=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question);

        mydb= new FinalDbHelper(this);
        mydb.getReadableDatabase();
        /*try{
            mydb.copyDatabase();
        }catch (Exception e){

        }*/



        initDatabaseHelper();
        initReference();
        initQuestion();

    }

    private void initDatabaseHelper() {
        dataCursor=mydb.getAllData();
        //dataCursor=database.query("tbl_questions",new String[]{"QNo","Question","OptionA","OptionB","OptionC","OptionD","RightAnswer"},null,null,null,null,"Question");
        dataCursor.moveToFirst();


    }

    private void initReference() {
        layoutQuestion = (Button)findViewById(R.id.btnQuestion);
        lTimeProgress=(ProgressBar)findViewById(R.id.timeProgress);
        lOptionA=(Button) findViewById(R.id.btnOptionA);
        lOptionB=(Button) findViewById(R.id.btnOptionB);
        lOptionC=(Button) findViewById(R.id.btnOptionC);
        lOptionD=(Button) findViewById(R.id.btnOptionD);
        lQuestionNumer=(TextView)findViewById(R.id.txtQuestionNumber);



        lOptionA.setOnClickListener(this);
        lOptionB.setOnClickListener(this);
        lOptionC.setOnClickListener(this);
        lOptionD.setOnClickListener(this);
        lTimeProgress.setMax(9);

    }

    private void initQuestion() {
                //dataCursor.moveToFirst();
        if(dataCursor!=null){
            qNo=dataCursor.getInt(0);
            Question=dataCursor.getString(dataCursor.getColumnIndex("Question"));
            OptionA= dataCursor.getString(dataCursor.getColumnIndex("OptionA"));
            OptionB=dataCursor.getString(dataCursor.getColumnIndex("OptionB"));
            OptionC=dataCursor.getString(dataCursor.getColumnIndex("OptionC"));
            OptionD=dataCursor.getString(dataCursor.getColumnIndex("OptionD"));
            RightAnswer= dataCursor.getString(dataCursor.getColumnIndex("RightAnswer"));
            Category=dataCursor.getString(dataCursor.getColumnIndex("Category"));


            number=String.valueOf(qNo);
            lQuestionNumer.setText("Q.No. :   "+number);

            layoutQuestion.setText(Question.toString());
            lOptionA.setText(OptionA.toString());
            lOptionB.setText(OptionB.toString());
            lOptionC.setText(OptionC.toString());
            lOptionD.setText(OptionD.toString());


        }else{
            //
            Toast.makeText(QuestionView.this,"No Questions Available Now",Toast.LENGTH_LONG).show();
            endQuiz("No questions");
        }






    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnOptionA:
                //lOptionA.setTextColor(Integer.parseInt("#0044ff"));
                String optA=lOptionA.getText().toString();
                checkAnswer(optA);
                break;

            case R.id.btnOptionB:
                //Toast.makeText(this,"Button B is Clicked",Toast.LENGTH_LONG).show();
                String optB=lOptionB.getText().toString();
                checkAnswer(optB);
                break;
            case R.id.btnOptionC:
                String optC=lOptionC.getText().toString();
                checkAnswer(optC);
                break;
            case R.id.btnOptionD:
                String optD=lOptionD.getText().toString();
                checkAnswer(optD);
                break;
            default:
                break;

        }
    }

    private void checkAnswer(String answer) {
        String userAnswer=answer;
        //Toast.makeText(QuestionView.this,userAnswer, Toast.LENGTH_SHORT).show();
        if(userAnswer.equals(RightAnswer)){
            points=points+100;

            if(dataCursor.moveToNext()){
                nextQuestion(qNo);

            }
            else{
                endQuiz("Questions finished");
            }

        }
        else{
            //increase error count
            //check error count
            endQuiz("Wrong answer");

        }

    }

    public void endQuiz(String endCase) {
        //Display the Score and exit from QuestionView
       // Toast.makeText(QuestionView.this,"Your Score was "+points,Toast.LENGTH_LONG).show();
        Intent intent= new Intent(QuestionView.this,Score.class);
        intent.putExtra("score",points);
        if(endCase.equals("Questions finished")){
            intent.putExtra("endReason","Questions finished");

        }else{
            intent.putExtra("endReason","Wrong answer");
        }
        startActivity(intent);
    }

    public void nextQuestion(int currentQuestionNumber){
        qNo=currentQuestionNumber+1;
        //dataCursor.moveToNext();
        Question=dataCursor.getString(dataCursor.getColumnIndex("Question"));
        OptionA= dataCursor.getString(dataCursor.getColumnIndex("OptionA"));
        OptionB=dataCursor.getString(dataCursor.getColumnIndex("OptionB"));
        OptionC=dataCursor.getString(dataCursor.getColumnIndex("OptionC"));
        OptionD=dataCursor.getString(dataCursor.getColumnIndex("OptionD"));
        RightAnswer= dataCursor.getString(dataCursor.getColumnIndex("RightAnswer"));
        Category=dataCursor.getString(dataCursor.getColumnIndex("Category"));

        //lQuestionNumer.setText(questioNumber);
        number=String.valueOf(qNo);
        lQuestionNumer.setText("Q.No. :   "+number);
        layoutQuestion.setText(Question.toString());
        lOptionA.setText(OptionA.toString());
        lOptionB.setText(OptionB.toString());
        lOptionC.setText(OptionC.toString());
        lOptionD.setText(OptionD.toString());


        //lTimeProgress.setProgress(i);
    }
}
