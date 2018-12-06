package familytree.a188project1.du.bestdamtree;

//author: Johanan Tai

import android.os.Parcel; //Parcelable to pass values through Intent putParcelableArrayListExtra and getParcelableArrayListExtra
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Quiz implements Serializable, Parcelable {
    //Initialize all attributes of Quiz
    private int Type;
    private String Question;
    private ArrayList<String> Answers;
    private int CorrectAnswer;
    private int userResponse;
    private boolean correct = false;

    //Constructor for Quiz class sets the correct Answer of Quiz
    public Quiz(int type, int correctAnswer){
        this.CorrectAnswer = correctAnswer;
        this.Type = type;
    }

    protected Quiz(Parcel in) {
        CorrectAnswer = in.readInt();
        Question = in.readString();
        Answers = in.createStringArrayList();
        Type = in.readInt();
        userResponse = in.readInt();
        correct = in.readByte() != 0;
    }

    //Had to be implemented for Parcelable
    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    public int getType(){
        return this.Type;
    }

    public void setType(int type){
        this.Type = type;
    }

    public String getQuestion(){
        return this.Question;
    }

    public void setQuestion(String question){
        this.Question = question;
    }

    public String getAnswer(int i){
        return this.Answers.get(i);
    }

    public void setAnswers(String [] answers){
        this.Answers = new ArrayList<String>(Arrays.asList(answers));
    }

    public String getCorrectAnswer(){
        return getAnswer(CorrectAnswer);
    }

    public void setCorrectAnswer(String correctAnswer){
        this.Answers.add(this.CorrectAnswer, correctAnswer);
    }
    public void setCorrectAnswerId(int i){
        this.CorrectAnswer = i;
    }

    public String getuserAnswer(){
        return getAnswer(userResponse);
    }


    public int getUserResponse(){
        return userResponse;
    }

    public void setUserResponse(int response){
        this.userResponse = response;

    }

    public boolean getcorrect(){
        return this.correct;
    }

    //Check to see if the Quiz is answered correctly
    public boolean Markme(){
        if(this.userResponse == this.CorrectAnswer){
            this.correct = true;
            return true;
        }
        else{
            this.correct = false;
            return false;
        }
    }

    //Another methods from the Parcelable class
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Type);
        parcel.writeString(Question);
        parcel.writeStringList(Answers);
        parcel.writeInt(CorrectAnswer);
        parcel.writeInt(userResponse);
        parcel.writeByte((byte) (correct ? 1 : 0));
    }
}