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

    Tree testFam = new Tree();
    RealmList<Person> testFamMembers = new RealmList<Person>();
    Realm realm = Realm.getDefaultInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                testFam.setName("Smith");
                Person person1 = new Person();
                person1.setFirstName("Walt");
                person1.setMiddleName("");
                person1.setLastName("Smith");
                person1.setOptionalSuffix("");
                person1.setBirthday("");
                person1.setCity("");
                person1.setJob("");
                person1.setEmployer("");
                person1.setInterests("");
                person1.setMarried(true);
                person1.setAlive(true);
                person1.setRealmID("1");
                Person person2 = new Person();
                person2.setFirstName("Lillian");
                person2.setLastName("Smith");
                person2.setRealmID("2");
                Person person3 = new Person();
                person3.setFirstName("Chad");
                person3.setLastName("Smith");
                person3.setRealmID("3");
                Person person4 = new Person();
                person4.setFirstName("Michaela");
                person4.setLastName("Smith");
                person4.setRealmID("4");
                Person person5 = new Person();
                person5.setFirstName("Ted");
                person5.setLastName("Smith");
                person5.setRealmID("5");
                Person person6 = new Person();
                person6.setFirstName("Jeremiah");
                person6.setLastName("Smith");
                person6.setRealmID("6");
                Person person7 = new Person();
                person7.setFirstName("Bruce");
                person7.setLastName("Smith");
                person7.setRealmID("7");
                Person person8 = new Person();
                person8.setFirstName("Jill");
                person8.setLastName("Smith");
                person8.setRealmID("8");
                Person person9 = new Person();
                person9.setFirstName("Annie");
                person9.setLastName("Lee");
                person9.setRealmID("9");
                Person person10 = new Person();
                person10.setFirstName("Joe");
                person10.setLastName("Lee");
                person10.setRealmID("10");
                Person person11 = new Person();
                person11.setFirstName("Dustin");
                person11.setLastName("Lee");
                person11.setRealmID("11");
                Person person12 = new Person();
                person12.setFirstName("Joanna");
                person12.setLastName("Smith");
                person12.setRealmID("12");
                Person person13 = new Person();
                person13.setFirstName("Cassie");
                person13.setLastName("Smith");
                person13.setRealmID("13");
                Person person14 = new Person();
                person14.setFirstName("Jaime");
                person14.setLastName("Wick");
                person14.setRealmID("14");
                Person person15 = new Person();
                person15.setFirstName("John");
                person15.setLastName("Wick");
                person15.setRealmID("15");
                Person person16 = new Person();
                person16.setFirstName("Will");
                person16.setLastName("Wick");
                person16.setRealmID("16");
                Person person17 = new Person();
                person17.setFirstName("Ryan");
                person17.setLastName("Wick");
                person17.setRealmID("17");
                person1.setSignificantOther(person2);
                person1.setMarried(true);
                person2.setSignificantOther(person1);
                person2.setMarried(true);
                person3.setSignificantOther(person4);
                person3.setMarried(true);
                person4.setSignificantOther(person3);
                person4.setMarried(true);
                person7.setSignificantOther(person8);
                person7.setMarried(true);
                person8.setSignificantOther(person7);
                person8.setMarried(true);
                person9.setSignificantOther(person10);
                person9.setMarried(true);
                person10.setSignificantOther(person9);
                person10.setMarried(true);
                person14.setSignificantOther(person15);
                person14.setMarried(true);
                person15.setSignificantOther(person14);
                person15.setMarried(true);
                RealmList<Person> parents1 = new RealmList<Person>(person1, person2);
                RealmList<Person> parents2 = new RealmList<Person>(person3, person4);
                RealmList<Person> parents3 = new RealmList<Person>(person7, person8);
                RealmList<Person> parents4 = new RealmList<Person>(person9, person10);
                RealmList<Person> parents5 = new RealmList<Person>(person14, person15);
                person3.setParents(parents1);
                person7.setParents(parents1);
                person14.setParents(parents1);
                person5.setParents(parents2);
                person6.setParents(parents2);
                person9.setParents(parents3);
                person12.setParents(parents3);
                person13.setParents(parents3);
                person11.setParents(parents4);
                person16.setParents(parents5);
                person17.setParents(parents5);
                RealmList<Person> kids1 = new RealmList<Person>(person3, person7, person14);
                RealmList<Person> kids2 = new RealmList<Person>(person5, person6);
                RealmList<Person> kids3 = new RealmList<Person>(person9, person12, person13);
                RealmList<Person> kids4 = new RealmList<Person>(person11);
                RealmList<Person> kids5 = new RealmList<Person>(person16, person17);
                person1.setKids(kids1);
                person2.setKids(kids1);
                person3.setKids(kids2);
                person4.setKids(kids2);
                person7.setKids(kids3);
                person8.setKids(kids3);
                person9.setKids(kids4);
                person10.setKids(kids4);
                person14.setKids(kids5);
                person15.setKids(kids5);
                realm.copyToRealmOrUpdate(person1);
                realm.copyToRealmOrUpdate(person2);
                realm.copyToRealmOrUpdate(person3);
                realm.copyToRealmOrUpdate(person4);
                realm.copyToRealmOrUpdate(person5);
                realm.copyToRealmOrUpdate(person6);
                realm.copyToRealmOrUpdate(person7);
                realm.copyToRealmOrUpdate(person8);
                realm.copyToRealmOrUpdate(person9);
                realm.copyToRealmOrUpdate(person10);
                realm.copyToRealmOrUpdate(person11);
                realm.copyToRealmOrUpdate(person12);
                realm.copyToRealmOrUpdate(person13);
                realm.copyToRealmOrUpdate(person14);
                realm.copyToRealmOrUpdate(person15);
                realm.copyToRealmOrUpdate(person16);
                realm.copyToRealmOrUpdate(person17);
                testFamMembers.add(person1);
                testFamMembers.add(person2);
                testFamMembers.add(person3);
                testFamMembers.add(person4);
                testFamMembers.add(person5);
                testFamMembers.add(person6);
                testFamMembers.add(person7);
                testFamMembers.add(person8);
                testFamMembers.add(person9);
                testFamMembers.add(person10);
                testFamMembers.add(person11);
                testFamMembers.add(person12);
                testFamMembers.add(person13);
                testFamMembers.add(person14);
                testFamMembers.add(person15);
                testFamMembers.add(person16);
                testFamMembers.add(person17);
                testFam.setPeople(testFamMembers);
                realm.copyToRealmOrUpdate(testFam);
            }
        });





        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TreeActivity.class);
                intent.putExtra("family", testFam.getName());
                startActivity(intent);
            }
        });

    }

}
