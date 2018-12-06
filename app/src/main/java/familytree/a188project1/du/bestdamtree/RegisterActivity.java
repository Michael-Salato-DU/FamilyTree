package familytree.a188project1.du.bestdamtree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import io.realm.Realm;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Widgets
        EditText first_name_input = findViewById(R.id.first_name_input);
        EditText last_name_input = findViewById(R.id.last_name_input);
        EditText email_input = findViewById(R.id.email_input);
        EditText password_input = findViewById(R.id.password_input);
        EditText confirm_password_input = findViewById(R.id.confirm_password_input);

        TextView message_register = findViewById(R.id.message_register);

        Button mConfirmRegisterButton = (Button) findViewById(R.id.confirm_register_button);
        mConfirmRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name_input_string = first_name_input.getText().toString();
                String last_name_input_string = last_name_input.getText().toString();
                String email_input_string = email_input.getText().toString();
                String password_input_string = password_input.getText().toString();
                String confirm_password_input_string = confirm_password_input.getText().toString();

                if(checkRegister(email_input_string, password_input_string, confirm_password_input_string))
                {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm) {
                            User new_user = new User();
                            new_user.setFirst_name(first_name_input_string);
                            new_user.setLast_name(last_name_input_string);
                            new_user.setEmail(email_input_string);
                            new_user.setPassword(password_input_string);

                            //Create its person class
                            Person new_person = new Person();
                            new_person.setRealmID(random_string_generator());
                            new_person.setFirstName(first_name_input_string);
                            new_person.setLastName(last_name_input_string);
                            new_user.setPerson(new_person);

                            realm.copyToRealmOrUpdate(new_user);
                        }
                        });
                    // Register should immediately bypass login
                    message_register.setText(R.string.account_created);

                    //Since this user is authenticated, we can set this as our current user
                    User current_user = realm.where(User.class).equalTo("email", email_input_string).findFirst();

                    Intent use = new Intent(getBaseContext(), MainActivity.class);
                    use.putExtra("current_email", current_user.getEmail());
                    startActivity(use);
                }
                else message_register.setText(R.string.register_error);
            }
        });
    }

    private boolean checkRegister(String email, String password, String confirm_password)
    {
        Realm realm = Realm.getDefaultInstance();
        User email_check = realm.where(User.class).equalTo("email",email).findFirst();
        return (email_check == null) && (Objects.equals(password,confirm_password));
    }

    public String random_string_generator() {
        return randomAlphanumeric(10);
    }
}
