//Tess Julien
//pop up card tutorial: https://youtu.be/fn5OlqQuOCk

package familytree.a188project1.du.bestdamtree;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;

public class PersonActivity extends AppCompatActivity{

    //declare variables
    private Person person;
    private Tree family;
    private ImageView imageView;
    private TextView nameView;
    private TextView birthdayView;
    private TextView cityView;
    private TextView jobView;
    private TextView employerView;
    private TextView interestsView;
    private Button editButton;
    private Button newPersonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person);

        //get screen size
        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);
        int width = screenSize.widthPixels;
        int height = screenSize.heightPixels;

        //set pop up card size
        getWindow().setLayout((int)(width*.85), (int)(height*.85));

        //refresh activity to update
        refresh();
    }

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    private void refresh(){
        //connect variables to fields from layout file
        imageView = (ImageView) findViewById(R.id.image_view);
        nameView = (TextView) findViewById(R.id.name_view);
        birthdayView = (TextView) findViewById(R.id.birthday_view);
        cityView = (TextView) findViewById(R.id.city_view);
        jobView = (TextView) findViewById(R.id.job_view);
        employerView = (TextView) findViewById(R.id.employer_view);
        interestsView = (TextView) findViewById(R.id.interests_view);

        //get extras from intent
        Realm realm = Realm.getDefaultInstance();
        String personID = (String) getIntent().getStringExtra("person");
        String familyTree = (String) getIntent().getStringExtra("family");
        person = realm.where(Person.class).equalTo("RealmID", personID).findFirst();
        family = realm.where(Tree.class).equalTo("name", familyTree).findFirst();


        //set image field
        if(person.getImage()!=null){
            Bitmap bmp = BitmapFactory.decodeByteArray(person.getImage(), 0, person.getImage().length);
            imageView.setImageBitmap(bmp);
        }
        //set name display based on what fields have been filled for the person
        if (!person.getLastName().matches("")){
            if (!person.getMiddleName().matches("")){
                if (!person.getOptionalSuffix().matches("")){
                    nameView.setText(person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName() + ", " + person.getOptionalSuffix());
                }
                else {
                    nameView.setText(person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName());
                }
            }
            else {
                if (!person.getOptionalSuffix().matches("")){
                    nameView.setText(person.getFirstName() + " " + person.getLastName() + ", " + person.getOptionalSuffix());
                }
                else {
                    nameView.setText(person.getFirstName() + " " + person.getLastName());
                }
            }
        }
        else {
            if (!person.getMiddleName().matches("")){
                if (!person.getOptionalSuffix().matches("")){
                    nameView.setText(person.getFirstName() + " " + person.getMiddleName() + ", " + person.getOptionalSuffix());
                }
                else {
                    nameView.setText(person.getFirstName() + " " + person.getMiddleName());
                }
            }
            else {
                if (!person.getOptionalSuffix().matches("")){
                    nameView.setText(person.getFirstName() + ", " + person.getOptionalSuffix());
                }
                else {
                    nameView.setText(person.getFirstName());
                }
            }
        }
        //set views to person's attributes
        birthdayView.setText(person.getBirthday());
        cityView.setText(person.getCity());
        jobView.setText(person.getJob());
        employerView.setText(person.getEmployer());
        interestsView.setText(person.getInterests());

        //what to do when add connection button is clicked
        newPersonButton = (Button) findViewById(R.id.add_connection);
        newPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass person and tree primary keys to new person activity
                Intent intent = new Intent(v.getContext(), NewPersonActivity.class);
                intent.putExtra("person",person.getRealmID());
                intent.putExtra("family", family.getName());
                startActivity(intent);
            }
        });

        //what to do when edit button is clicked
        editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass person primary key to edit person activity
                Intent intent = new Intent(v.getContext(), EditPersonActivity.class);
                intent.putExtra("person", person.getRealmID());
                startActivity(intent);
            }
        });
    }
}
