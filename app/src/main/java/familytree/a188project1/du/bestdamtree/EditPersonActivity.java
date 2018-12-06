//Tess Julien
package familytree.a188project1.du.bestdamtree;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;

public class EditPersonActivity extends AppCompatActivity{

    private Person person;
    private ImageView pictureView;
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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_person);

        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);
        int width = screenSize.widthPixels;
        int height = screenSize.heightPixels;

        getWindow().setLayout((int)(width*.85), (int)(height*.85));

        pictureView = (ImageView) findViewById(R.id.picture_view);
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

        pictureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (addImage.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(addImage, 1);
                }
            }
        });

        if (!person.getFirstName().matches("")){
            firstNameView.setText(person.getFirstName());
        }
        if (!person.getMiddleName().matches("")){
            middleNameView.setText(person.getMiddleName());
        }
        if (!person.getLastName().matches("")){
            lastNameView.setText(person.getLastName());
        }
        if (!person.getOptionalSuffix().matches("")){
            suffixView.setText(person.getOptionalSuffix());
        }
        if (!person.getBirthday().matches("")){
            birthdayView.setText(person.getBirthday());
        }
        if (!person.getCity().matches("")){
            cityView.setText(person.getCity());
        }
        if (!person.getJob().matches("")){
            jobView.setText(person.getJob());
        }
        if (!person.getEmployer().matches("")){
            employerView.setText(person.getEmployer());
        }
        if (!person.getInterests().matches("")){
            interestsView.setText(person.getInterests());
        }
        if (person.isMarried()){
            marriedCheckbox.setChecked(true);
        }

        if (!person.isAlive()){
            aliveView.setChecked(true);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        //add if statements
                        BitmapDrawable image = (BitmapDrawable) pictureView.getDrawable();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageInByte = baos.toByteArray();
                        person.setImage(imageInByte);
                        if (!firstNameView.getText().toString().matches("First Name")){
                            person.setFirstName(firstNameView.getText().toString());
                        }
                        if (!middleNameView.getText().toString().matches("Middle Name")){
                            person.setMiddleName(middleNameView.getText().toString());
                        }
                        if (!lastNameView.getText().toString().matches("Last Name")){
                            person.setLastName(lastNameView.getText().toString());
                        }
                        if (!suffixView.getText().toString().matches("Suffix")){
                            person.setOptionalSuffix(suffixView.getText().toString());
                        }
                        if (!birthdayView.getText().toString().matches("Birthday")){
                            person.setBirthday(birthdayView.getText().toString());
                        }
                        if (!cityView.getText().toString().matches("City")){
                            person.setCity(cityView.getText().toString());
                        }
                        if (!jobView.getText().toString().matches("Job/Major")){
                            person.setJob(jobView.getText().toString());
                        }
                        if (!employerView.getText().toString().matches("Employer/School")){
                            person.setEmployer(employerView.getText().toString());
                        }
                        if (!interestsView.getText().toString().matches("Interests")){
                            person.setInterests(interestsView.getText().toString());
                        }

                        person.setMarried(marriedCheckbox.isChecked());
                        person.setAlive(!aliveView.isChecked());

                        realm.copyToRealmOrUpdate(person);
                        finish();
                    }
                });
            }
        });

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
}