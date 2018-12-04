package familytree.a188project1.du.bestdamtree;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {
    public Quiz quiz;
    private TextView questionView;
    private RadioGroup answersGroup;
    private RadioButton answerA;
    private RadioButton answerB;
    private RadioButton answerC;
    private RadioButton answerD;
    private int QuestionNumber;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_view, container, false);

        questionView = (TextView) view.findViewById(R.id.question_text);
        answersGroup = (RadioGroup) view.findViewById(R.id.answers_group);
        answerA = (RadioButton) view.findViewById(R.id.answer_button_a);
        answerB = (RadioButton) view.findViewById(R.id.answer_button_b);
        answerC = (RadioButton) view.findViewById(R.id.answer_button_c);
        answerD = (RadioButton) view.findViewById(R.id.answer_button_d);

        questionView.setText(Integer.toString(this.QuestionNumber)+". " +quiz.getQuestion());
        answerA.setText(quiz.getAnswer(0));
        answerB.setText(quiz.getAnswer(1));
        answerC.setText(quiz.getAnswer(2));
        answerD.setText(quiz.getAnswer(3));
        quiz.setUserResponse(1000);
        answersGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.answer_button_a:
                        quiz.setUserResponse(0);
                        break;
                    case R.id.answer_button_b:
                        quiz.setUserResponse(1);
                        break;
                    case R.id.answer_button_c:
                        quiz.setUserResponse(2);
                        break;
                    case R.id.answer_button_d:
                        quiz.setUserResponse(3);
                        break;
                }
            }
        });
        return view;
    }
    public void setQuestionNumber(int i){
        this.QuestionNumber = i;
    }
}