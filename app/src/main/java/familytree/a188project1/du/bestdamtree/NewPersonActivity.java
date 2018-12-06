//Tess Julien
//random generator source: https://stackoverflow.com/questions/12116092/android-random-string-generator
package familytree.a188project1.du.bestdamtree;

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

    //declare variables
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

        //find screen size and save width and height to variables
        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);
        int width = screenSize.widthPixels;
        int height = screenSize.heightPixels;

        //set pop up window size to
        getWindow().setLayout((int)(width*.85), (int)(height*.85));

        //link variables to views in the layout file
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


        //get extras from intent and set activity variables
        realm = Realm.getDefaultInstance();
        String personID = (String) getIntent().getStringExtra("person");
        person = realm.where(Person.class).equalTo("RealmID", personID).findFirst();
        String familyName = (String) getIntent().getStringExtra("family");
        family = realm.where(Tree.class).equalTo("name", familyName).findFirst();

        //open camera when image view is clicked
        pictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (addImage.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(addImage, 1);
                }
            }
        });

        //set relation
        relationView.setText("Relation to " + person.getFirstName() + " :");

        //set spinner options
        relationSpinner.setOnItemSelectedListener(this);
        ArrayList<String> relationOptions = new ArrayList<String>();
        relationOptions.add("Significant Other");
        relationOptions.add("Child");
        relationOptions.add("Parent");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, relationOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationSpinner.setAdapter(adapter);

        //save new person
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //must enter a first name and a relationship to save
                if (!firstNameView.getText().toString().matches("") && !relationship.matches("")){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            //generate random id
                            newPerson.setRealmID(random());
                            //set new person's first name
                            newPerson.setFirstName(firstNameView.getText().toString());
                            //if middle name was entered save answer to middle name, if not save blank
                            if (!middleNameView.getText().toString().matches("Middle Name")){
                                newPerson.setMiddleName(middleNameView.getText().toString());
                            }
                            else {
                                newPerson.setMiddleName("");
                            }
                            //if last name was entered save answer, if not save blank
                            if (!lastNameView.getText().toString().matches("Last Name")){
                                newPerson.setLastName(lastNameView.getText().toString());
                            }
                            else {
                                newPerson.setLastName("");
                            }
                            //if suffix was entered save answer, if not save blank
                            if (!suffixView.getText().toString().matches("Suffix")){
                                newPerson.setOptionalSuffix(suffixView.getText().toString());
                            }
                            else {
                                newPerson.setOptionalSuffix("");
                            }
                            //if birthday was entered save answer, if not save blank
                            if (!birthdayView.getText().toString().matches("Birthday")){
                                newPerson.setBirthday(birthdayView.getText().toString());
                            }
                            else {
                                newPerson.setBirthday("");
                            }
                            //if city was entered save answer, if not save blank
                            if (!cityView.getText().toString().matches("City")){
                                newPerson.setCity(cityView.getText().toString());
                            }
                            else {
                                newPerson.setCity("");
                            }
                            //if job was entered save answer, if not save blank
                            if (!jobView.getText().toString().matches("Job/Major")){
                                newPerson.setJob(jobView.getText().toString());
                            }
                            else {
                                newPerson.setJob("");
                            }
                            //if employer was entered save answer, if not save blank
                            if (!employerView.getText().toString().matches("Employer/School")){
                                newPerson.setEmployer(employerView.getText().toString());
                            }
                            else {
                                newPerson.setEmployer("");
                            }
                            //if interests were entered save answer, if not save blank
                            if (!interestsView.getText().toString().matches("Interests")){
                                newPerson.setInterests(interestsView.getText().toString());
                            }
                            else {
                                newPerson.setInterests("");
                            }
                            //save married based on checkbox
                            if (marriedCheckbox.isChecked()){
                                newPerson.setMarried(true);
                            }
                            else {
                                newPerson.setMarried(false);
                            }
                            //save alive based on checkbox
                            if (aliveView.isChecked()){
                                newPerson.setAlive(false);
                            }
                            else {
                                newPerson.setAlive(true);
                            }
                            //check if new person is significant other of base person
                            if (relationship.matches("Significant Other")){
                                //get base person's significant other
                                RealmList<Person> personSO = person.getSignificantOther();
                                //create new object for new person's significant other
                                RealmList<Person> newPersonSO = new RealmList<Person>();
                                //add base person to new person's significant other
                                newPersonSO.add(person);
                                //set new person's significant other
                                newPerson.setSignificantOther(newPersonSO);
                                //add new person to base person's significant other
                                personSO.add(newPerson);
                                //update base person's significant other
                                person.setSignificantOther(personSO);
                                realm.copyToRealmOrUpdate(person);
                                //set base person's kids as new person's kids
                                newPerson.setKids(person.getKids());
                            }
                            //check if new person is child of base person
                            if (relationship.matches("Child")){
                                //create list of parents for new person
                                RealmList<Person> parents = new RealmList<Person>();
                                parents.add(person);
                                //if base person has a significant other, add that person to parent list
                                if(!person.getSignificantOther().isEmpty()){
                                    parents.add(person.getSignificantOther().last());
                                }
                                //set new person's parents
                                newPerson.setParents(parents);
                                //get base person's kids, add new person, and update base person
                                RealmList<Person> kids = person.getKids();
                                kids.add(newPerson);
                                person.setKids(kids);
                                realm.copyToRealmOrUpdate(person);
                            }
                            //check if new person is parent of base person
                            if (relationship.matches("Parent")){
                                //get base person's parent list, add new person, and update base person
                                RealmList<Person> parents = person.getParents();
                                parents.add(newPerson);
                                person.setParents(parents);
                                realm.copyToRealmOrUpdate(person);
                                //create kids list for new person, add base person, and set new person's kids
                                RealmList<Person> kids = new RealmList<Person>();
                                kids.add(person);
                                newPerson.setKids(kids);
                            }
                            //set new person's image
                            BitmapDrawable image = (BitmapDrawable) pictureView.getDrawable();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInByte = baos.toByteArray();
                            newPerson.setImage(imageInByte);
                            //copy new person to realm
                            realm.copyToRealmOrUpdate(newPerson);
                            //update tree's family members
                            RealmList<Person> familyMembers = family.getPeople();
                            familyMembers.add(newPerson);
                            family.setPeople(familyMembers);
                            //update tree
                            realm.copyToRealmOrUpdate(family);
                            finish();
                        }
                    });
                }
            }
        });
    }

    //set relationship variable based on spinner selection
    @Override
    public void onItemSelected(AdapterView<?> relationSetter, View view, int position, long id){
        String relation = relationSetter.getItemAtPosition(position).toString();
        if (relation.matches("Significant Other")){
            relationship = "Significant Other";
        }
        if (relation.matches("Child")){
            relationship = "Child";
        }
        if (relation.matches("Parent")){
            relationship = "Parent";
        }
    }

    public void onNothingSelected(AdapterView<?> arg0){

    }

    //get image taken with camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pictureView.setImageBitmap(imageBitmap);
        }
    }

    //generate a random realm id
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