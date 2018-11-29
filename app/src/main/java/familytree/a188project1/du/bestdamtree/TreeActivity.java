package familytree.a188project1.du.bestdamtree;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class TreeActivity extends AppCompatActivity {
//    private RecyclerView testRecyclerView;
//    private RecyclerView.Adapter testAdapter;
//    private RecyclerView.LayoutManager testLayoutManager;
//
    //final MainActivity mainActivity = (MainActivity) this.getActivity();
    private RealmList<Person> testFam = new RealmList<Person>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        Realm realm = Realm.getDefaultInstance();
        //final RealmList<Person> testFam = new RealmList<Person>();
        if(testFam.size()==0){
            populateFam();
        }
//        testRecyclerView = (RecyclerView) findViewById(R.id.test_recycler_view);
//
//        Person person1 = new Person();
//        person1.setRealmID(1);
//        person1.setFirstName("Walt");
//        person1.setLastName("Disney");
//        Person person2 = new Person();
//        person2.setRealmID(2);
//        person2.setFirstName("Queen of");
//        person2.setLastName("Arendelle");

//
//        testLayoutManager = new LinearLayoutManager(this);
//        testRecyclerView.setLayoutManager(testLayoutManager);
//
//        testAdapter = new TreeListAdapter(getBaseContext(), family);
//        testRecyclerView.setAdapter(testAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), QuizActivity.class);
                startActivity(intent);
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Family"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        final TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

        }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public void populateFam() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person1 = new Person();
                person1.setRealmID(1);
                person1.setFirstName("Walt");
                person1.setLastName("Disney");
                realm.copyToRealmOrUpdate(person1);
                testFam.add(person1);
                Person person2 = new Person();
                person2.setRealmID(2);
                person2.setFirstName("Queen of");
                person2.setLastName("Arendelle");
                realm.copyToRealmOrUpdate(person2);
                testFam.add(person2);
                RealmList<Person> grandparents = new RealmList<>();
                grandparents.add(person1);
                person2.setParents(grandparents);
                Person person3 = new Person();
                person3.setRealmID(3);
                person3.setFirstName("Queen");
                person3.setLastName("of Corona");
                person3.setParents(grandparents);
                Person person4 = new Person();
                person4.setRealmID(4);
                person4.setFirstName("King of");
                person4.setLastName("Arendelle");
                person4.setSignificantOther(person2);
                person2.setSignificantOther(person4);
                Person person5 = new Person();
                person5.setRealmID(5);
                person5.setFirstName("King");
                person5.setLastName("of Corona");
                person5.setSignificantOther(person3);
                person3.setSignificantOther(person5);
                Person person6 = new Person();
                person6.setRealmID(6);
                person6.setFirstName("Elsa");
                person6.setLastName("Arendelle");
                Person person7 = new Person();
                person7.setRealmID(7);
                person7.setFirstName("Anna");
                person7.setLastName("Arendelle");
                RealmList<Person> arendelleParents = new RealmList<>();
                arendelleParents.add(person2);
                arendelleParents.add(person4);
                RealmList<Person> coronaParents = new RealmList<>();
                coronaParents.add(person3);
                coronaParents.add(person5);
                person6.setParents(arendelleParents);
                person7.setParents(arendelleParents);
                Person person8 = new Person();
                person8.setRealmID(8);
                person8.setFirstName("Rapunzel");
                person8.setLastName("of Corona");
                person8.setParents(coronaParents);
                realm.copyToRealmOrUpdate(person3);
                realm.copyToRealmOrUpdate(person4);
                realm.copyToRealmOrUpdate(person5);
                realm.copyToRealmOrUpdate(person6);
                realm.copyToRealmOrUpdate(person7);
                realm.copyToRealmOrUpdate(person8);
                testFam.add(person3);
                testFam.add(person4);
                testFam.add(person5);
                testFam.add(person6);
                testFam.add(person7);
                testFam.add(person8);
            }
        });

    }


}
