/* Jeremy Liu */

package familytree.a188project1.du.bestdamtree;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set Title
        super.setTitle(R.string.title_activity_login);
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<User> users = realm.where(User.class).findAll();
        if(users.size() == 0) {
            generateAdmin();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Widgets
        EditText email_input = findViewById(R.id.email);
        EditText password_input = findViewById(R.id.password);
        TextView message = findViewById(R.id.message);

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email_input_string = email_input.getText().toString();
                String password_input_string = password_input.getText().toString();

                if(checkLogin(email_input_string, password_input_string))
                {
                    message.setText("");

                    //Since this user is authenticated, we can set this as our current user
                    User current_user = realm.where(User.class).equalTo("email", email_input_string).findFirst();

                    Intent use = new Intent(getBaseContext(), MainActivity.class);
                    use.putExtra("current_email", current_user.getEmail());
                    startActivity(use);
                }
                else message.setText(R.string.incorrect_credentials);
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(getBaseContext(),RegisterActivity.class);
                startActivity(register_intent);
            }
        });
    }

    private void generateAdmin() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                User admin = new User();
                admin.setEmail("test@a.com");
                admin.setPassword("rr");
                admin.setFirst_name("Jacky");
                admin.setLast_name("Chan");

                // Creates a dummy Person
                Person admin_person = new Person();
                admin_person.setRealmID("AVSDSC52");
                admin_person.setFirstName("Jacky");
                admin_person.setLastName("Chan");

                admin_person.setMiddleName("Bot");
                admin_person.setOptionalSuffix("Dr.");
                admin_person.setBirthday("1/2/3");
                admin_person.setCity("Hogwarts");
                admin_person.setJob("Magician");
                admin_person.setEmployer("Monster University");
                admin_person.setInterests("Scaring people");
                admin_person.setMarried(false);
                admin_person.setAlive(true);
                RealmList<Person> kids = new RealmList<Person>();
                admin_person.setKids(kids);
                RealmList<Person> parents = new RealmList<Person>();
                admin_person.setParents(parents);

                admin.setPerson(admin_person);



                realm.copyToRealmOrUpdate(admin);
            }
        });
    }

    private boolean checkLogin(String email, String password)
    {
        Realm realm = Realm.getDefaultInstance();
        User email_check = realm.where(User.class).equalTo("email",email).findFirst();
        return (email_check != null) && (Objects.equals(email_check.getPassword(),password));
    }
}

