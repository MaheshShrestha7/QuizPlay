package com.madeinnepalandro.QuizPlay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by munchymahesh on 1/26/16.
 */
public class Score extends Activity implements View.OnClickListener {
    public Button btnPlayAgain,btnExit;
    public TextView txtScore,txtReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_score);

        txtReason=(TextView)findViewById(R.id.txtReason);
        btnPlayAgain= (Button)findViewById(R.id.btnPlayAgain);
        txtScore=(TextView)findViewById(R.id.txtScore);
        btnExit=(Button)findViewById(R.id.btnExit);

        Bundle extras= getIntent().getExtras();
        //int score=extras.getInt("score");
        String score=extras.get("score").toString();
        String endReason=extras.get("endReason").toString();


        if(endReason.equals("Questions finished")){
            txtReason.setText("BRAVO!! \nAll Questions Solved.");
        }
        else{
            txtReason.setText("TOUGH LUCK \n Game Over");
        }

        txtScore.setText(score);

        btnPlayAgain.setOnClickListener(this);
        btnExit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlayAgain:
                Intent intent= new Intent(Score.this,QuestionView.class);
                startActivity(intent);
                break;
            case R.id.btnExit:
                System.exit(0);
                break;
            default:
                break;
        }

    }
}
