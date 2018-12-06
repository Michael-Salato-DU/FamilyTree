package familytree.a188project1.du.bestdamtree;

//author: Johanan Tai

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    public Quiz quiz;
    private TextView questionView;
    private ImageView imageView;
    private RadioGroup answersGroup;
    private RadioButton answerA;
    private RadioButton answerB;
    private RadioButton answerC;
    private RadioButton answerD;

    private int QuestionNumber;
    private Bitmap imageQuiz;
    private int imageItem;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_pager_quiz, container, false);

        questionView = (TextView) view.findViewById(R.id.question_text);
        imageView = (ImageView) view.findViewById(R.id.image_quiz);
        answersGroup = (RadioGroup) view.findViewById(R.id.answers_group);
        answerA = (RadioButton) view.findViewById(R.id.answer_button_a);
        answerB = (RadioButton) view.findViewById(R.id.answer_button_b);
        answerC = (RadioButton) view.findViewById(R.id.answer_button_c);
        answerD = (RadioButton) view.findViewById(R.id.answer_button_d);

        questionView.setText(Integer.toString(this.QuestionNumber)+ ". " +quiz.getQuestion());

        //Sets the image based on the question generated
        switch (imageItem) {
            case 0:
                imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.smile));
                break;
            case 1:
                imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.parent));
                break;
            case 2:
                imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.like));
                break;
            case 3:
                imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.house));
                break;
            case 4:
                imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.job));
                break;
            case 5:
                imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.kids));
                break;
            case 6:
                imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.birthday));
                break;
            case 7:
                imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.sigother));
                break;
            case 8:
                imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.employer));
                break;
            default:
                return null;
        }

        answerA.setText(quiz.getAnswer(0));
        answerB.setText(quiz.getAnswer(1));
        answerC.setText(quiz.getAnswer(2));
        answerD.setText(quiz.getAnswer(3));

        //to avoid null reference in Quiz Activity
        quiz.setUserResponse(1000);

        //sets user response based on the radio button clicked
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
    public void setimageItem(int i) { this.imageItem = i; }
}