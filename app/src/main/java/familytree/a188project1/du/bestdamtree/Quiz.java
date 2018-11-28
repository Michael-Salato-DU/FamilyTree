package familytree.a188project1.du.bestdamtree;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quiz implements Serializable {
    private String Type;
    private String Question;
    private ArrayList<String> Answers;
    private int CorrectAnswer;
    private int Response;

    private static int Score = 0;
    private static int Count = 0;

    public Quiz(int correctAnswer){
        this.CorrectAnswer = correctAnswer;
        Count++;
    }

    public String getType(){
        return this.Type;
    }

    public void setType(String type){
        this.Type = type;
    }

    public String getQuestion(){
        return this.Question;
    }

    public void setQuestion(String question){
        this.Question = question;
    }

    public String getCorrectAnswer(){
        return this.Answers.get(CorrectAnswer);
    }

    public void setCorrectAnswer(String correctAnswer){
        this.Answers.add(this.CorrectAnswer, correctAnswer);
    }

    public String getAnswer(int i){
        return this.Answers.get(i);
    }
    public void setAnswers(String [] answers){
        this.Answers = new ArrayList<String>(Arrays.asList(answers));
    }

    public void setUserResponse(int response){
        this.Response = response;
    }

    public boolean Markme(){
        if(this.Response == this.CorrectAnswer){
            Score++;
            return true;
        }
        else{
            return false;
        }
    }

}
