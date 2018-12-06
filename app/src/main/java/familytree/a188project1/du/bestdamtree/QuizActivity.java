package familytree.a188project1.du.bestdamtree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class QuizActivity extends AppCompatActivity {

    public ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        nextButton = (Button)findViewById(R.id.next_submit_button);
        NoSwipeViewPager viewPager = (NoSwipeViewPager) findViewById(R.id.viewPager);

        Quiz quiz1 = new Quiz(1);
        Quiz quiz2 = new Quiz(0);
        Quiz quiz3 = new Quiz(2);
        Quiz quiz4 = new Quiz(2);
        Quiz quiz5 = new Quiz(0);
        Quiz quiz6 = new Quiz(1);
        Quiz quiz7 = new Quiz(0);
        Quiz quiz8 = new Quiz(2);
        Quiz quiz9 = new Quiz(2);
        Quiz quiz10 = new Quiz(0);

        quizzes.add(quiz1);
        quizzes.add(quiz2);
        quizzes.add(quiz3);
        quizzes.add(quiz4);
        quizzes.add(quiz5);
        quizzes.add(quiz6);
        quizzes.add(quiz7);
        quizzes.add(quiz8);
        quizzes.add(quiz9);
        quizzes.add(quiz10);

        RealmList<Person> persons = new RealmList<Person>();

        int questionNumber = 0;
        int randomNum = ThreadLocalRandom.current().nextInt(0, persons.size()-1);
        int[] rands_1 = new int[]{0,1,2,3,4,5,6,7,8,8};

        for(Quiz q: quizzes){

            Person correctPerson = persons.get(randomNum);
            ArrayList<Person> wrongPersons = new ArrayList<Person>();

            for(Person p: persons){
                if(p.getRealmID()!=correctPerson.getRealmID()){
                    wrongPersons.add(p);
                }
            }
            q.setPerson(correctPerson);
            q.setAnswers(answerBank(rands_1[questionNumber], wrongPersons));
            q.setQuestion(questionBank(rands_1[questionNumber],correctPerson,q));

            questionNumber++;
        }


        QuizAdapter adapter = new QuizAdapter(getSupportFragmentManager(),quizzes,quizzes.size());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        AlertDialog.Builder dialogStop = new AlertDialog.Builder(this);

        dialogStop.setMessage("Please select an answer.");
        dialogStop.setCancelable(true);
        dialogStop.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {dialogInterface.cancel();}
        });

        AlertDialog alertStop = dialogStop.create();

        nextButton.setOnClickListener(new View.OnClickListener() {

            int count = 1;
            int totalitems = viewPager.getAdapter().getCount();

            @Override
            public void onClick(View v) {
                Quiz question = quizzes.get(count -1);
                if (question.getUserResponse() != 1000) {
                    if (count < totalitems){
                        question.Markme();
                        viewPager.setCurrentItem(count);

                        if (count == totalitems - 1) {
                            nextButton.setText("Submit");
                        }
                        count ++;
                    }
                    else if (count == totalitems){
                        question.Markme();
                        Intent reward_intent = new Intent(getBaseContext(),QuizActivityReward.class);
                        reward_intent.putExtra("quiz_count",totalitems);
                        reward_intent.putParcelableArrayListExtra("Quizzes", quizzes);
                        startActivity(reward_intent);
                        count ++;
                    }
                }
                else{
                    alertStop.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogExit = new AlertDialog.Builder(this);
        dialogExit.setMessage("Do you want to quit the quiz?");
        dialogExit.setCancelable(true);
        dialogExit.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                QuizActivity.super.onBackPressed();
                finish();
            }
        });
        dialogExit.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertExit = dialogExit.create();
        alertExit.show();
    }

    public String questionBank(int i, Person person, Quiz q){

        switch(i){
            case 0:
                q.setCorrectAnswer(person.getFirstName());
                return "Who is this?";
            case 1:
                if(!person.getParents().isEmpty()){
                    q.setCorrectAnswer(person.getParents().get(0).getFirstName());
                }
                else{
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");}
                return "Who is the parent of " + person.getFirstName() + "?";
            case 2:
                if(!person.getLikes().isEmpty()){
                    q.setCorrectAnswer(person.getLikes());
                }
                else{
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "What is " + person.getFirstName()+ " " + person.getLastName() + " interests?";
            case 3:
                if(!person.getCity().isEmpty()){
                    q.setCorrectAnswer(person.getCity());
                }
                else{
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "Where does " + person.getFirstName()+ " " + person.getLastName() + " currently lives?";
            case 4:
                if(!person.getJob().isEmpty()){
                    q.setCorrectAnswer(person.getJob());
                }
                else{
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "What is " + person.getFirstName()+ " " + person.getLastName() + " job?";
            case 5:
                if(!person.getKids().isEmpty()){
                    q.setCorrectAnswer(person.getKids().get(0).getFirstName());
                }
                else{
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "Name one of "+ person.getFirstName()+ " kid(s).";
            case 6:
                if(!person.getBirthday().isEmpty()){
                    q.setCorrectAnswer(person.getBirthday());
                }
                else{
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "When is " + person.getFirstName()+ " " + person.getLastName() + " birthday?";
            case 7:
                if(person.getSignificantOther()!= null){
                    q.setCorrectAnswer(person.getSignificantOther().getFirstName());
                }
                else{
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "Who is " + person.getFirstName()+ " " + person.getLastName() + " significant other?";
            case 8:
                if(!person.getEmployer().isEmpty()){
                    q.setCorrectAnswer(person.getEmployer());
                }
                else{
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "Who is " + person.getFirstName()+ " " + person.getLastName() + " employer?";
        }
        return "";
    }

    public String[] answerBank(int i, ArrayList<Person> persons){

        String[] answers = new String[3];
        int[] randomint = new int []{1,2,3};
        switch(i){

            case 0:
                for(int j=0; j<answers.length;j++){
                    answers[j] = persons.get(randomint[j]).getFirstName();
                }
                return answers;
            case 1:
                for(int j=0; j<answers.length;j++){
                    answers[j] = persons.get(randomint[j]).getParents().get(0).getFirstName();
                }
                return answers;
            case 2:
                for(int j=0; j<answers.length;j++){
                    answers[j] = persons.get(randomint[j]).getLikes();
                }
                return answers;
            case 3:
                for(int j=0; j<answers.length;j++){
                    answers[j] = persons.get(randomint[j]).getCity();
                }
                return answers;
            case 4:
                for(int j=0; j<answers.length;j++){
                    answers[j] = persons.get(randomint[j]).getJob();
                }
                return answers;
            case 5:
                for(int j=0; j<answers.length;j++){
                    answers[j] = persons.get(randomint[j]).getKids().get(0).getFirstName();
                }
                return answers;
            case 6:
                for(int j=0; j<answers.length;j++){
                    answers[j] = persons.get(randomint[j]).getBirthday();
                }
                return answers;
            case 7:
                for(int j=0; j<answers.length;j++){
                    answers[j] = persons.get(randomint[j]).getSignificantOther().getFirstName();
                }
                return answers;
            case 8:
                for(int j=0; j<answers.length;j++){
                    answers[j] = persons.get(randomint[j]).getEmployer();
                }
                return answers;
        }
        return null;
    }

}

