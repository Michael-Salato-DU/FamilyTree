package familytree.a188project1.du.bestdamtree;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    public ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        nextButton = (Button)findViewById(R.id.next_submit_button);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        Quiz quiz1 = new Quiz(1);
        Quiz quiz2 = new Quiz(0);
        Quiz quiz3 = new Quiz(3);
        Quiz quiz4 = new Quiz(2);
        Quiz quiz5 = new Quiz(0);

        quiz1.setQuestion("Who is Joe's Dad?");
        quiz2.setQuestion("What is my name?");
        quiz3.setQuestion("What is the meaning of life?");
        quiz4.setQuestion("Who is Elsa's sister?");
        quiz5.setQuestion("What do you call a bank in a city?");

        String [] quiz1Ans = {"Jeremy","Bruce","Dan","Burger"};
        String [] quiz2Ans = {"Johanan","Jupiter","Justin", "Ralph"};
        String [] quiz3Ans = {"Politics","Career","Relationships", "43"};
        String [] quiz4Ans = {"Snow White","Vanellope","Anna", "Jensen"};
        String [] quiz5Ans = {"CitiBank","USBank","Electricity", "United Bank"};

        quiz1.setAnswers(quiz1Ans);
        quiz2.setAnswers(quiz2Ans);
        quiz3.setAnswers(quiz3Ans);
        quiz4.setAnswers(quiz4Ans);
        quiz5.setAnswers(quiz5Ans);

        quizzes.add(quiz1);
        quizzes.add(quiz2);
        quizzes.add(quiz3);
        quizzes.add(quiz4);
        quizzes.add(quiz5);

        QuizAdapter adapter = new QuizAdapter(getSupportFragmentManager(),quizzes,quizzes.size());
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();


        nextButton.setOnClickListener(new View.OnClickListener() {
            int count = 1;
            @Override
            public void onClick(View v) {
                if(count < quizzes.size()-1)
                {
                    viewPager.setCurrentItem(count);
                    count+=1;
                }
                else if (count == quizzes.size()){
                    alert11.show();
                }

                else{
                    viewPager.setCurrentItem(count);
                    nextButton.setText("Submit");
                    if (count == quizzes.size()-1){
                        count++;
                    }
                }

            }
        });


    }
}
