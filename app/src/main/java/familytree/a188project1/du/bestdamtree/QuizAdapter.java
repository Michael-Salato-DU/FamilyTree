package familytree.a188project1.du.bestdamtree;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class QuizAdapter extends FragmentStatePagerAdapter{

    public ArrayList<Quiz> quizzes = null;
    public int qbanksize;
    private int userResponse;

    public QuizAdapter(FragmentManager fm, ArrayList<Quiz> quizzes, int qbanksize) {
        super(fm);
        this.quizzes = quizzes;
        this.qbanksize = qbanksize;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                QuizFragment question1 = new QuizFragment();
                question1.quiz = quizzes.get(i);
                question1.setQuestionNumber(i+1);
//                question1.setImageQuiz(BitmapFactory.decodeByteArray(quizzes.get(i).getPerson().getImage(),
//                        0,quizzes.get(i).getPerson().getImage().length));
                return question1;
            case 1:
                QuizFragment question2 = new QuizFragment();
                question2.quiz = quizzes.get(i);
                question2.setQuestionNumber(i+1);
                question2.setImageQuiz(BitmapFactory.decodeResource(question2.getResources(), R.drawable.parent));
                return question2;
            case 2:
                QuizFragment question3 = new QuizFragment();
                question3.quiz = quizzes.get(i);
                question3.setQuestionNumber(i+1);
                question3.setImageQuiz(BitmapFactory.decodeResource(question3.getResources(), R.drawable.like));
                return question3;
            case 3:
                QuizFragment question4 = new QuizFragment();
                question4.quiz = quizzes.get(i);
                question4.setQuestionNumber(i+1);
                question4.setImageQuiz(BitmapFactory.decodeResource(question4.getResources(), R.drawable.house));
                return question4;
            case 4:
                QuizFragment question5 = new QuizFragment();
                question5.quiz = quizzes.get(i);
                question5.setQuestionNumber(i+1);
                question5.setImageQuiz(BitmapFactory.decodeResource(question5.getResources(), R.drawable.job));
                return question5;
            case 5:
                QuizFragment question6 = new QuizFragment();
                question6.quiz = quizzes.get(i);
                question6.setQuestionNumber(i+1);
                question6.setImageQuiz(BitmapFactory.decodeResource(question6.getResources(), R.drawable.kids));
                return question6;
            case 6:
                QuizFragment question7 = new QuizFragment();
                question7.quiz = quizzes.get(i);
                question7.setQuestionNumber(i+1);
                question7.setImageQuiz(BitmapFactory.decodeResource(question7.getResources(), R.drawable.birthday));
                return question7;
            case 7:
                QuizFragment question8 = new QuizFragment();
                question8.quiz = quizzes.get(i);
                question8.setQuestionNumber(i+1);
                question8.setImageQuiz(BitmapFactory.decodeResource(question8.getResources(), R.drawable.sigother));
                return question8;
            case 8:
                QuizFragment question9 = new QuizFragment();
                question9.quiz = quizzes.get(i);
                question9.setQuestionNumber(i+1);
                question9.setImageQuiz(BitmapFactory.decodeResource(question9.getResources(), R.drawable.employer));
                return question9;
            case 9:
                QuizFragment question10 = new QuizFragment();
                question10.quiz = quizzes.get(i);
                question10.setQuestionNumber(i+1);
//                question10.setImageQuiz(BitmapFactory.decodeByteArray(quizzes.get(i).getPerson().getImage(),
//                        0,quizzes.get(i).getPerson().getImage().length));
                return question10;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return qbanksize;
    }
}
