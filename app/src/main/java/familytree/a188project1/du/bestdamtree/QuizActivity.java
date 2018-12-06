package familytree.a188project1.du.bestdamtree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import io.realm.Realm;
import io.realm.RealmList;


public class QuizActivity extends AppCompatActivity {

    public ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
    private Button nextButton;
    public User user;
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

        Realm realm = Realm.getDefaultInstance();
        String family = getIntent().getStringExtra("family");
        String current_email = getIntent().getStringExtra("current_email");

        user = realm.where(User.class).equalTo("email",current_email).findFirst();
        Tree tree = realm.where(Tree.class).equalTo("name",family).findFirst();
        RealmList<Person> persons = tree.getPeople();

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
                        reward_intent.putExtra("current_email",user.getEmail());
                        reward_intent.putExtra("family",family);
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
                try{
                    q.setCorrectAnswer(person.getParents().get(0).getFirstName());
                }
                catch(ArrayIndexOutOfBoundsException a){
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "Who is the parent of " + person.getFirstName() + "?";
            case 2:
                try{
                    q.setCorrectAnswer(person.getInterests());
                }
                catch(NullPointerException n){
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "What is " + person.getFirstName()+ " " + person.getLastName() + " interests?";
            case 3:
                try{
                    q.setCorrectAnswer(person.getCity());
                }
                catch(NullPointerException n){
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "Where does " + person.getFirstName()+ " " + person.getLastName() + " currently lives?";
            case 4:
                try{
                    q.setCorrectAnswer(person.getJob());
                }
                catch(NullPointerException n){
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "What is " + person.getFirstName()+ " " + person.getLastName() + " job?";
            case 5:
                try{
                    q.setCorrectAnswer(person.getKids().get(0).getFirstName());
                }
                catch(ArrayIndexOutOfBoundsException n){
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }

                return "Name one of "+ person.getFirstName()+ " kid(s).";
            case 6:
                try{
                    q.setCorrectAnswer(person.getBirthday());
                }
                catch(NullPointerException n){
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }

                return "When is " + person.getFirstName()+ " " + person.getLastName() + " birthday?";
            case 7:
                try{
                    q.setCorrectAnswer(person.getSignificantOther().get(0).getFirstName());
                }
                catch(ArrayIndexOutOfBoundsException a){
                    q.setCorrectAnswerId(3);
                    q.setCorrectAnswer("None of these answers");
                }
                return "Who is " + person.getFirstName()+ " " + person.getLastName() + " significant other?";
            case 8:
                try{
                    q.setCorrectAnswer(person.getEmployer());
                }
                catch(NullPointerException n){
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
                    try{
                        answers[j] = persons.get(randomint[j]).getFirstName();
                    }
                    catch (NullPointerException n){
                        answers[j] = "None";
                    }
                }
                return answers;
            case 1:

                for(int j=0; j<answers.length;j++){
                    try{
                        answers[j] = persons.get(randomint[j]).getParents().get(0).getFirstName();
                    }
                    catch (ArrayIndexOutOfBoundsException a){
                        answers[j] = "None";
                    }
                }
                return answers;
            case 2:
                for(int j=0; j<answers.length;j++){
                    try{
                        answers[j] = persons.get(randomint[j]).getInterests();
                    }
                    catch (NullPointerException n){
                        answers[j] = "None";
                    }
                }
                return answers;
            case 3:
                for(int j=0; j<answers.length;j++){
                    try{
                        answers[j] = persons.get(randomint[j]).getCity();
                    }
                    catch (NullPointerException n){
                        answers[j] = "None";
                    }
                }
                return answers;
            case 4:
                for(int j=0; j<answers.length;j++){
                    try{
                        answers[j] = persons.get(randomint[j]).getJob();
                    }
                    catch (NullPointerException n){
                        answers[j] = "None";
                    }
                }
                return answers;
            case 5:
                for(int j=0; j<answers.length;j++){
                    try{
                        answers[j] = persons.get(randomint[j]).getKids().get(0).getFirstName();
                    }
                    catch (ArrayIndexOutOfBoundsException a){
                        answers[j] = "None";
                    }
                }
                return answers;
            case 6:
                for(int j=0; j<answers.length;j++){
                    try{
                        answers[j] = persons.get(randomint[j]).getBirthday();
                    }
                    catch (NullPointerException n){
                        answers[j] = "None";
                    }
                }
                return answers;
            case 7:
                for(int j=0; j<answers.length;j++){
                    try{
                        answers[j] = persons.get(randomint[j]).getSignificantOther().get(0).getFirstName();
                    }
                    catch (ArrayIndexOutOfBoundsException a){
                        answers[j] = "None";
                    }
                }
                return answers;
            case 8:
                for(int j=0; j<answers.length;j++){
                    try{
                        answers[j] = persons.get(randomint[j]).getEmployer();
                    }
                    catch (NullPointerException n){
                        answers[j] = "None";
                    }
                }
                return answers;
        }
        return answers;
    }

}

