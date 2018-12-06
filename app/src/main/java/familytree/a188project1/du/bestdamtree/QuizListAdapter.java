package familytree.a188project1.du.bestdamtree;

//author: Johanan Tai

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.AnswersViewHolder> {

    private Context context;
    private ArrayList<Quiz> quizzes;

    public QuizListAdapter(Context context, ArrayList<Quiz> quizzes){
        this.context = context;
        this.quizzes = quizzes;
    }


    public class AnswersViewHolder extends RecyclerView.ViewHolder{
        private TextView questionView;
        private TextView userAnswerView;
        private TextView correctAnswerView;
        private View view;

        public AnswersViewHolder(View v) {
            super(v);
            this.view = v;
            questionView = v.findViewById(R.id.question_view);
            userAnswerView = v.findViewById(R.id.useranswer_view);
            correctAnswerView = v.findViewById(R.id.correctanswer_view);
        }

        public View getView(){
            return  this.view;
        }
    }

    @Override
    public QuizListAdapter.AnswersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_quiz_answers,parent,false);
        AnswersViewHolder vh = new AnswersViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(QuizListAdapter.AnswersViewHolder holder, int position) {
        Quiz quiz = quizzes.get(position);
        holder.questionView.setText(Integer.toString(position+1)+". "+quiz.getQuestion());
        holder.userAnswerView.setText(quiz.getuserAnswer());
        holder.correctAnswerView.setText(quiz.getCorrectAnswer());
        if(!quiz.getcorrect()){
            holder.questionView.setTextColor(Color.parseColor("#FA8072"));
        }
        else{
            holder.questionView.setTextColor(Color.parseColor("#00FF7F"));
        }
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }
}