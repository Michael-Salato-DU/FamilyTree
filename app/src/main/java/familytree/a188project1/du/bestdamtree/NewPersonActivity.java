//Tess Julien
package familytree.a188project1.du.bestdamtree;

//import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;

public class NewPersonActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Person person;
    private Tree family;
    private ImageView pictureView;
    private TextView relationView;
    private Spinner relationSpinner;
    private EditText firstNameView;
    private EditText middleNameView;
    private EditText lastNameView;
    private EditText suffixView;
    private EditText birthdayView;
    private EditText cityView;
    private EditText jobView;
    private EditText employerView;
    private EditText interestsView;
    private CheckBox marriedCheckbox;
    private CheckBox aliveView;
    private Button saveButton;
    private Realm realm;
    private Person newPerson = new Person();

    private String relationship = "";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_person);

        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);
        int width = screenSize.widthPixels;
        int height = screenSize.heightPixels;

        getWindow().setLayout((int)(width*.85), (int)(height*.85));

        pictureView = (ImageView) findViewById(R.id.picture_view);
        relationView = (TextView) findViewById(R.id.relation_view);
        relationSpinner = (Spinner) findViewById(R.id.relation_spinner);
        firstNameView = (EditText) findViewById(R.id.first_name_view);
        middleNameView = (EditText) findViewById(R.id.middle_name_view);
        lastNameView = (EditText) findViewById(R.id.last_name_view);
        suffixView = (EditText) findViewById(R.id.suffix_view);
        birthdayView = (EditText) findViewById(R.id.birthday_view);
        cityView = (EditText) findViewById(R.id.city_view);
        jobView = (EditText) findViewById(R.id.job_view);
        employerView = (EditText) findViewById(R.id.employer_view);
        interestsView = (EditText) findViewById(R.id.interests_view);
        marriedCheckbox = (CheckBox) findViewById(R.id.married_checkbox);
        aliveView = (CheckBox) findViewById(R.id.alive_view);
        saveButton = (Button) findViewById(R.id.save_button);



        realm = Realm.getDefaultInstance();
        String personID = (String) getIntent().getStringExtra("person");
        person = realm.where(Person.class).equalTo("RealmID", personID).findFirst();
        String familyName = (String) getIntent().getStringExtra("family");
        family = realm.where(Tree.class).equalTo("name", familyName).findFirst();


        pictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (addImage.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(addImage, 1);
                }
            }
        });

        relationView.setText("Relation to " + person.getFirstName() + " :");


        relationSpinner.setOnItemSelectedListener(this);
        ArrayList<String> relationOptions = new ArrayList<String>();
        relationOptions.add("Significant Other");
        relationOptions.add("Child");
        relationOptions.add("Parent");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, relationOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationSpinner.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstNameView.getText().toString().matches("") && !relationship.matches("")){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            //newPerson.setRealmID(realm.where(Person.class).findAll().sort("RealmID").last().getRealmID()+1);
                            //newPerson.setRealmID(realm.where(Person.class).findAll().last().getRealmID()+1);
                            newPerson.setRealmID(random());
                            newPerson.setFirstName(firstNameView.getText().toString());
                            newPerson.setMiddleName(middleNameView.getText().toString());
                            newPerson.setLastName(lastNameView.getText().toString());
                            newPerson.setOptionalSuffix(suffixView.getText().toString());
                            newPerson.setBirthday(birthdayView.getText().toString());
                            newPerson.setCity(cityView.getText().toString());
                            newPerson.setJob(jobView.getText().toString());
                            newPerson.setEmployer(employerView.getText().toString());
                            newPerson.setInterests(interestsView.getText().toString());
                            if (marriedCheckbox.isChecked()){
                                newPerson.setMarried(true);
                            }
                            else {
                                newPerson.setMarried(false);
                            }
                            if (aliveView.isChecked()){
                                newPerson.setAlive(false);
                            }
                            else {
                                newPerson.setAlive(true);
                            }
                            if (relationship.matches("Significant Other")){
                                newPerson.setSignificantOther(person);
                                person.setSignificantOther(newPerson);
                                realm.copyToRealmOrUpdate(person);
                                newPerson.setKids(person.getKids());
                            }
                            if (relationship.matches("Child")){
                                RealmList<Person> parents = new RealmList<Person>();
                                parents.add(person);
                                parents.add(person.getSignificantOther());
                                newPerson.setParents(parents);
                                RealmList<Person> kids = person.getKids();
                                kids.add(newPerson);
                                person.setKids(kids);
                                realm.copyToRealmOrUpdate(person);
                            }
                            if (relationship.matches("Parent")){
                                RealmList<Person> parents = person.getParents();
                                parents.add(newPerson);
                                person.setParents(parents);
                                realm.copyToRealmOrUpdate(person);
                                RealmList<Person> kids = new RealmList<Person>();
                                kids.add(person);
                                newPerson.setKids(kids);
                            }
                            BitmapDrawable image = (BitmapDrawable) pictureView.getDrawable();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInByte = baos.toByteArray();
                            newPerson.setImage(imageInByte);
                            realm.copyToRealmOrUpdate(newPerson);
                            RealmList<Person> familyMembers = family.getPeople();
                            familyMembers.add(newPerson);
                            family.setPeople(familyMembers);
                            realm.copyToRealmOrUpdate(family);
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> relationSetter, View view, int position, long id){
        String relation = relationSetter.getItemAtPosition(position).toString();
        if (relation.matches("Significant Other")){
//            newPerson.setSignificantOther(person);
//            person.setSignificantOther(newPerson);
//            realm.copyToRealmOrUpdate(person);
//            newPerson.setKids(person.getKids());
            relationship = "Significant Other";
        }
        if (relation.matches("Child")){
//            RealmList<Person> parents = new RealmList<Person>();
//            parents.add(person);
//            parents.add(person.getSignificantOther());
//            newPerson.setParents(parents);
//            RealmList<Person> kids = new RealmList<Person>();
//            kids.add(newPerson);
//            person.setKids(kids);
//            realm.copyToRealmOrUpdate(person);
            relationship = "Child";
        }
        if (relation.matches("Parent")){
//            RealmList<Person> parents = person.getParents();
//            parents.add(newPerson);
//            person.setParents(parents);
//            realm.copyToRealmOrUpdate(person);
//            RealmList<Person> kids = new RealmList<Person>();
//            kids.add(person);
//            newPerson.setKids(kids);
            relationship = "Parent";
        }
    }

    public void onNothingSelected(AdapterView<?> arg0){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pictureView.setImageBitmap(imageBitmap);
        }
    }

    public static String random(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        char newChar;
        for (int i = 0; i <8; i++){
            newChar = (char) (generator.nextInt(96)+32);
            randomStringBuilder.append(newChar);
        }
        return randomStringBuilder.toString();
    }
}
