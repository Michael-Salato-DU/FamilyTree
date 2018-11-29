package familytree.a188project1.du.bestdamtree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    //Tree testFam = new Tree();
    //RealmList<Person> testFamMembers = new RealmList<Person>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TreeActivity.class);
                startActivity(intent);
            }
        });

    }


}
