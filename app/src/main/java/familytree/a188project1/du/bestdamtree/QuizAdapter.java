package familytree.a188project1.du.bestdamtree;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class QuizAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Quiz> quizzes = null;
    public int qbanksize;

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
                question1.quiz = quizzes.get(0);
                return question1;
            case 1:
                QuizFragment question2 = new QuizFragment();
                question2.quiz = quizzes.get(1);
                return question2;
            case 2:
                QuizFragment question3 = new QuizFragment();
                question3.quiz = quizzes.get(2);
                return question3;
            case 3:
                QuizFragment question4 = new QuizFragment();
                question4.quiz = quizzes.get(3);
                return question4;
            case 4:
                QuizFragment question5 = new QuizFragment();
                question5.quiz = quizzes.get(4);
                return question5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return qbanksize;
    }
}
