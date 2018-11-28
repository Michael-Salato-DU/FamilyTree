package familytree.a188project1.du.bestdamtree;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button nextButton;
    private RadioButton answerA;
    private RadioButton answerB;
    private RadioButton answerC;
    private RadioButton answerD;
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
        nextButton = (Button) view.findViewById(R.id.next_submit_button);

        final QuizActivity quizActivity = (QuizActivity)this.getActivity();

        questionView.setText(quiz.getQuestion());
        answerA.setText(quiz.getAnswer(0));
        answerB.setText(quiz.getAnswer(1));
        answerC.setText(quiz.getAnswer(2));
        answerD.setText(quiz.getAnswer(3));

        return view;
    }

}
