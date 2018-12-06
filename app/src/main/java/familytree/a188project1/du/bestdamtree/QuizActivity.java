package familytree.a188project1.du.bestdamtree;

//author: Johanan Tai

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        NoSwipeViewPager viewPager = (NoSwipeViewPager) findViewById(R.id.viewPager);//Using a
        // custom viewPager in this class that does not allow swiping

        Quiz quiz1 = new Quiz(0,1);
        Quiz quiz2 = new Quiz(1,0);
        Quiz quiz3 = new Quiz(2,2);
        Quiz quiz4 = new Quiz(3,2);
        Quiz quiz5 = new Quiz(4,0);
        Quiz quiz6 = new Quiz(5,1);
        Quiz quiz7 = new Quiz(6,0);
        Quiz quiz8 = new Quiz(7,2);
        Quiz quiz9 = new Quiz(8,2);
        Quiz quiz10 = new Quiz(2,0);

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

        //collects the list of people from the tree to be quizzed on
        RealmList<Person> persons = tree.getPeople();

        for(Quiz q: quizzes){
            //random number for more variety of questions based on different persons
            int randomNum = ThreadLocalRandom.current().nextInt(0, persons.size()-1);
            Person correctPerson = persons.get(randomNum);
            ArrayList<Person> wrongPersons = new ArrayList<Person>();

            //Initialize an array filled with persons who are not the correct person in the question
            //(basically all the wrong answers)
            for(Person p: persons){
                if(p.getRealmID()!=correctPerson.getRealmID()){
                    wrongPersons.add(p);
                }
            }
            //uses this method to set wrong answers only
            q.setAnswers(answerBank(q.getType(),wrongPersons));
            //uses this method to set the question and correct answer
            q.setQuestion(questionBank(q.getType(),correctPerson,q));
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

        //this click listener for the button that iterates the viewPager
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
    //customize the back button to avoid user going back to the previous quiz (allows only exiting the quiz and continuation)
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

    //Method to return question based on the correct person as well as setting the correct answer
    public String questionBank(int i, Person person, Quiz q){
        try{
            switch(i){
                case 0:
                    if(person.getFirstName().isEmpty()){q.setCorrectAnswer("Unknown");}
                    else{q.setCorrectAnswer(person.getFirstName());}
                    return "Who is this?";
                case 1:
                    if(person.getParents().isEmpty()){q.setCorrectAnswer("Unknown");}
                    else{q.setCorrectAnswer(person.getParents().get(0).getFirstName());}
                    return "Who is the parent of " + person.getFirstName() + "?";
                case 2:
                    if(person.getInterests().isEmpty()){q.setCorrectAnswer("Unknown");}
                    else{q.setCorrectAnswer(person.getInterests());}
                    return "What is " + person.getFirstName()+ " " + person.getLastName() + " interests?";
                case 3:
                    if(person.getCity().isEmpty()){q.setCorrectAnswer("Unknown");}
                    else{q.setCorrectAnswer(person.getCity());}
                    return "Where does " + person.getFirstName()+ " " + person.getLastName() + " currently lives?";
                case 4:
                    if(person.getCity().isEmpty()){q.setCorrectAnswer("Unknown");}
                    else{q.setCorrectAnswer(person.getCity());}
                    return "What is " + person.getFirstName()+ " " + person.getLastName() + " job?";
                case 5:
                    if(person.getKids().isEmpty()){q.setCorrectAnswer("Unknown");}
                    else{q.setCorrectAnswer(person.getKids().get(0).getFirstName());}
                    return "Name one of "+ person.getFirstName()+ " kid(s).";
                case 6:
                    if(person.getBirthday().isEmpty()){q.setCorrectAnswer("Unknown");}
                    else{q.setCorrectAnswer(person.getBirthday());}
                    return "When is " + person.getFirstName()+ " " + person.getLastName() + " birthday?";
                case 7:
                    if(person.getSignificantOther().isEmpty()){q.setCorrectAnswer("Unknown");}
                    else{q.setCorrectAnswer(person.getSignificantOther().get(0).getFirstName());}
                    return "Who is " + person.getFirstName()+ " " + person.getLastName() + " significant other?";
                case 8:
                    if(person.getEmployer().isEmpty()){q.setCorrectAnswer("Unknown");}
                    else{q.setCorrectAnswer(person.getEmployer());}
                    return "Who is " + person.getFirstName()+ " " + person.getLastName() + " employer?";
            }
        }
        catch(NullPointerException | ArrayIndexOutOfBoundsException na){
            q.setCorrectAnswerId(3);
            q.setCorrectAnswer("Unknown");
        }
        return "";
    }

    //Method to set wrong answers
    public String[] answerBank(int i, ArrayList<Person> persons){

        String[] answers = new String[3];
        ArrayList<Integer> randomInts = new ArrayList<Integer>();

        int flag = 0;
        //Generate wrong answers based on randomize persons
        while(flag < 3){
            int randomInt = ThreadLocalRandom.current().nextInt(0, persons.size()-1);
            if(!randomInts.contains(randomInt)){
                randomInts.add(randomInt);
                flag++;
            }
            else{ }
        }
        switch(i){
            case 0:
                for(int j=0; j<answers.length;j++){
                    try{
                        if(persons.get(randomInts.get(j)).getFirstName().isEmpty()){ answers[j] = "Unknown"; }
                        else{answers[j] = persons.get(randomInts.get(j)).getFirstName();}
                    }
                    catch (NullPointerException n ){ answers[j] = "Unknown"; }
                }
                return answers;
            case 1:

                for(int j=0; j<answers.length;j++){
                    try{
                        if(persons.get(randomInts.get(j)).getParents().isEmpty()){ answers[j] = "Unknown"; }
                        else{answers[j] = persons.get(randomInts.get(j)).getParents().get(0).getFirstName();}
                    }
                    catch (NullPointerException | ArrayIndexOutOfBoundsException a){ answers[j] = "Unknown"; }
                }
                return answers;
            case 2:
                for(int j=0; j<answers.length;j++){
                    try{
                        if(persons.get(randomInts.get(j)).getInterests().isEmpty()){ answers[j] = "Unknown"; }
                        else{answers[j] = persons.get(randomInts.get(j)).getInterests();}
                    }
                    catch (NullPointerException n){ answers[j] = "Unknown"; }
                }
                return answers;
            case 3:
                for(int j=0; j<answers.length;j++){
                    try{
                        if(persons.get(randomInts.get(j)).getCity().isEmpty()){ answers[j] = "Unknown"; }
                        else{answers[j] = persons.get(randomInts.get(j)).getCity();}
                    }
                    catch (NullPointerException n){ answers[j] = "Unknown"; }
                }
                return answers;
            case 4:
                for(int j=0; j<answers.length;j++){
                    try{
                        if(persons.get(randomInts.get(j)).getJob().isEmpty()){ answers[j] = "Unknown"; }
                        else{answers[j] = persons.get(randomInts.get(j)).getFirstName();}
                    }
                    catch (NullPointerException n){ answers[j] = "Unknown"; }
                }
                return answers;
            case 5:
                for(int j=0; j<answers.length;j++){
                    try{
                        if(persons.get(randomInts.get(j)).getKids().isEmpty()){ answers[j] = "Unknown"; }
                        else{answers[j] = persons.get(randomInts.get(j)).getKids().get(0).getFirstName();}
                    }
                    catch (NullPointerException | ArrayIndexOutOfBoundsException a){ answers[j] = "Unknown"; }
                }
                return answers;
            case 6:
                for(int j=0; j<answers.length;j++){
                    try{
                        if(persons.get(randomInts.get(j)).getBirthday().isEmpty()){ answers[j] = "Unknown"; }
                        else{answers[j] = persons.get(randomInts.get(j)).getBirthday();}
                    }
                    catch (NullPointerException n){ answers[j] = "Unknown"; }
                }
                return answers;
            case 7:
                for(int j=0; j<answers.length;j++){
                    try{
                        if(persons.get(randomInts.get(j)).getSignificantOther().isEmpty()){ answers[j] = "Unknown"; }
                        else{answers[j] = persons.get(randomInts.get(j)).getSignificantOther().get(0).getFirstName();}
                    }
                    catch (NullPointerException | ArrayIndexOutOfBoundsException a){ answers[j] = "Unknown"; }
                }
                return answers;
            case 8:
                for(int j=0; j<answers.length;j++){
                    try{
                        if(persons.get(randomInts.get(j)).getEmployer().isEmpty()){ answers[j] = "Unknown"; }
                        else{answers[j] = persons.get(randomInts.get(j)).getEmployer();}
                    }
                    catch (NullPointerException n){ answers[j] = "Unknown"; }
                }
                return answers;
        }
        return answers;
    }

}