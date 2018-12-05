package familytree.a188project1.du.bestdamtree;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Quiz implements Serializable, Parcelable {
    private String Type;
    private String Question;
    private ArrayList<String> Answers;
    private int CorrectAnswer;
    private int userResponse;
    private boolean correct = false;

    public Quiz(int correctAnswer){
        this.CorrectAnswer = correctAnswer;
    }

    protected Quiz(Parcel in) {
        Type = in.readString();
        Question = in.readString();
        Answers = in.createStringArrayList();
        CorrectAnswer = in.readInt();
        userResponse = in.readInt();
        correct = in.readByte() != 0;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Type);
        parcel.writeString(Question);
        parcel.writeStringList(Answers);
        parcel.writeInt(CorrectAnswer);
        parcel.writeInt(userResponse);
        parcel.writeByte((byte) (correct ? 1 : 0));
    }
}
