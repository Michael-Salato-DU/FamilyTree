package familytree.a188project1.du.bestdamtree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;


public class QuizActivityReward extends AppCompatActivity {
    private ImageView trophyImageView;
    private TextView scoreView;
    private TextView commentView;
    private Button reviewAnsButton;
    private Button returnMainButton;
    public User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_reward);

        trophyImageView = (ImageView)findViewById(R.id.trophy_imageview);
        scoreView = (TextView)findViewById(R.id.percent_score_view);
        commentView = (TextView)findViewById(R.id.comment_view);
        reviewAnsButton = (Button) findViewById(R.id.answers_review_button);
        returnMainButton = (Button)findViewById(R.id.return_main_button);

        Realm realm = Realm.getDefaultInstance();

        String current_email = getIntent().getStringExtra("current_email");
        user = realm.where(User.class).equalTo("email",current_email).findFirst();

        int Score = 0;
        ArrayList<Quiz> quizzes = getIntent().getParcelableArrayListExtra("Quizzes");
        int Count = getIntent().getIntExtra("quiz_count", quizzes.size());

        for(Quiz i: quizzes){
            if(i.getcorrect()){Score++;}
        }

        int percent = (Score*100)/Count;
        scoreView.setText(Integer.toString(percent)+"%");


        if(percent > 0 && percent < 40){
            trophyImageView.setImageResource(R.drawable.bronzetroph);
            commentView.setText("Try harder...");
        }
        else if(percent >=40 && percent <=80){
            trophyImageView.setImageResource(R.drawable.silvertroph);
            commentView.setText("So close yet so far...");
        }
        else if (percent >= 80){
            trophyImageView.setImageResource(R.drawable.goldtroph);
            commentView.setText("Family is dear to you...");
        }
        else{
            trophyImageView.setImageResource(R.drawable.egg);
            commentView.setText("Are you an egg?");
        }

        reviewAnsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent review_activity = new Intent(getBaseContext(),QuizActivityAnswers.class);
            review_activity.putParcelableArrayListExtra("Quizzes", quizzes);
            startActivity(review_activity);
            }
        });

        returnMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent tree_activity = new Intent(getBaseContext(),TreeActivity.class);
            tree_activity.putExtra("current_email", user.getEmail());
            startActivity(tree_activity);
            finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

}
