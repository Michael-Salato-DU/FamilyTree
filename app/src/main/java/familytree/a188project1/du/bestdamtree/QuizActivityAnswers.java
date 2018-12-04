package familytree.a188project1.du.bestdamtree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class QuizActivityAnswers extends AppCompatActivity {
    private RecyclerView answerlist;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answers);


        ArrayList<Quiz> quizzes = getIntent().getParcelableArrayListExtra("Quizzes");

        answerlist = (RecyclerView)findViewById(R.id.correct_answer_list);
        layoutManager = new LinearLayoutManager(getBaseContext());
        answerlist.setLayoutManager(layoutManager);

        QuizListAdapter adapter = new QuizListAdapter(getBaseContext(),quizzes);
        answerlist.setAdapter(adapter);
    }
}
