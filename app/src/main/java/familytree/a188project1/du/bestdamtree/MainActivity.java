package familytree.a188project1.du.bestdamtree;

import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {

    // Declare variables
    private DrawerLayout mDrawerLayout;
    private Button newTreeButton;
    private RecyclerView treeList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter treeAdapter;
    public User user;
    public RealmList<Tree> familyList;
    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get realm instance
        realm = Realm.getDefaultInstance();
        // Get the user with the email address passed from LoginActivity
        String currentEmail = (String) getIntent().getStringExtra("current_email");
        user = realm.where(User.class).equalTo("email", currentEmail).findFirst();

        // Get a RealmList of all the family trees associated with this user
//        final RealmResults<Tree> trees = realm.where(Tree.class).findAll();
//        final RealmResults<Tree> trees = user.getTrees();
//        familyList = new RealmList<Tree>();
        familyList = user.getTrees();
//        familyList.addAll(trees.subList(0, trees.size()));
        // Populate family trees if there are none yet
        if (familyList.size() == 0) {
            populateTree();
        }

        // Log for checking that the correct User is active
        Log.d("UserEmail", user.getEmail());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // source for adding the nav drawer button, a.k.a "hamburger menu"
        // title: Create a navigation drawer: Add the nav drawer button
        // author: Android Developers Documentation
        // https://developer.android.com/training/implementing-navigation/nav-drawer#DrawerButton
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // enable app bar's "home" button
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp); // change button to use the "hamburger menu" icon
        // end source

        // source for handling clicks of navigation drawer items:
        // title: Create a navigation drawer: Handle navigation click events
        // author: Android Developers Documentation
        // https://developer.android.com/training/implementing-navigation/nav-drawer#ListItemClicks

        // Tie mDrawerLayout to DrawerLayout view
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Create a NavigationView and set its item selection listener
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setCheckable(true);
                        //close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        // end source

                        // Open the appropriate activity based on the selected menu item
                        // Open your profile to edit your card
                        if (menuItem.getTitle().equals("Profile")) {
                            Intent intent = new Intent(getBaseContext(), EditPersonActivity.class);
                            intent.putExtra("person", user.getPerson().getRealmID());
                            startActivity(intent);
                        }
                        // Open up your list of saved trees
                        else if (menuItem.getTitle().equals("Your Trees")) {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("current_email", user.getEmail());
                            startActivity(intent);
                        }
                        // Open up helpful hints
                        else if (menuItem.getTitle().equals("Helpful Hints")) {
                            Intent intent = new Intent(getBaseContext(), HelpfulHintsActivity.class);
                            startActivity(intent);
                        }
                        // Sign out and return to the login page
                        else if (menuItem.getTitle().equals("Sign Out")) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.putExtra("current_email", user.getEmail());
                            startActivity(intent);
                        }

                        return true;
                    }
                }
        );

        // Open an activity to create a new tree (family) when the "New Tree" button is clicked
        newTreeButton = (Button) findViewById(R.id.new_tree_button);
        newTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // Create a new family with just this user's person information
                        Tree newFam = new Tree();
                        newFam.setName(user.getPerson().getLastName()); // set name of family to this user's last name
                        RealmList<Person> testFamMembers = new RealmList<Person>();
                        testFamMembers.add(user.getPerson());
                        newFam.setPeople(testFamMembers);
                        realm.copyToRealmOrUpdate(newFam);
//                        familyList.add(newFam);
//                        realm.copyToRealmOrUpdate(familyList);
                        user.getTrees().add(newFam);
                        realm.copyToRealmOrUpdate(user);

                        // Pass tree name and user email to the next activity
                        Intent intent = new Intent(view.getContext(), TreeActivity.class);
                        intent.putExtra("family", newFam.getName());
                        intent.putExtra("current_email", user.getEmail());
                        startActivity(intent);
                    }
                });
            }
        });

        // set LayoutMangager
        treeList = (RecyclerView) findViewById(R.id.tree_list);
        layoutManager = new LinearLayoutManager(this);
        treeList.setLayoutManager(layoutManager);

        // Clicking on an event in the list will open up a TreeActivity.
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Determine which tree is selected.
                Tree selectedFamily = (Tree) familyList.get(position);
                Intent intent = new Intent(view.getContext(), TreeActivity.class);
                // Pass tree name and user email to the next activity.
                intent.putExtra("family", selectedFamily.getName());
                intent.putExtra("current_email", user.getEmail());
                startActivity(intent);
            }

        };

        // Create a TreeAdapter and pass in the list of trees
        treeAdapter = new TreeAdapter(this, familyList, listener);
        treeList.setAdapter(treeAdapter);
    }

    // source for opening the navigation drawer when the "hamburger menu" is tapped:
    // title: Create a navigation drawer: Open the drawer when the button is tapped
    // author: Android Developers Documentation
    // https://developer.android.com/training/implementing-navigation/nav-drawer#OpenDrawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateTree() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Get list of family trees
                Tree testFam = new Tree();
                RealmList<Person> testFamMembers = new RealmList<Person>();

                RealmList<Person> emptyList = new RealmList<Person>();

                testFam.setName("Smith");
                Person person1 = new Person();
                person1.setFirstName("Walt");
                person1.setMiddleName("Robert");
                person1.setLastName("Smith");
                person1.setOptionalSuffix("");
                person1.setBirthday("12/5/1940");
                person1.setCity("Burbank");
                person1.setJob("Artist");
                person1.setEmployer("Self-employed");
                person1.setInterests("Movies, comics, mice");
                person1.setMarried(true);
                person1.setAlive(true);
                person1.setRealmID("1");
                person1.setParents(emptyList);

                Person person2 = new Person();
                person2.setFirstName("Lillian");
                person2.setLastName("Smith");
                person2.setRealmID("2");
                person2.setMiddleName("");
                person2.setOptionalSuffix("");
                person2.setBirthday("");
                person2.setCity("");
                person2.setJob("");
                person2.setEmployer("");
                person2.setInterests("");
                person2.setMarried(true);
                person2.setAlive(true);
                person2.setParents(emptyList);

                Person person3 = new Person();
                person3.setFirstName("Chad");
                person3.setLastName("Smith");
                person3.setRealmID("3");
                person3.setMiddleName("");
                person3.setOptionalSuffix("");
                person3.setBirthday("");
                person3.setCity("");
                person3.setJob("");
                person3.setEmployer("");
                person3.setInterests("");
                person3.setMarried(true);
                person3.setAlive(true);

                Person person4 = new Person();
                person4.setFirstName("Michaela");
                person4.setLastName("Smith");
                person4.setRealmID("4");
                person4.setMiddleName("");
                person4.setOptionalSuffix("");
                person4.setBirthday("");
                person4.setCity("");
                person4.setJob("");
                person4.setEmployer("");
                person4.setInterests("");
                person4.setMarried(true);
                person4.setAlive(true);

                Person person5 = new Person();
                person5.setFirstName("Ted");
                person5.setLastName("Smith");
                person5.setRealmID("5");
                person5.setMiddleName("");
                person5.setOptionalSuffix("");
                person5.setBirthday("");
                person5.setCity("");
                person5.setJob("");
                person5.setEmployer("");
                person5.setInterests("");
                person5.setMarried(false);
                person5.setAlive(true);
                person5.setSignificantOther(emptyList);

                Person person6 = new Person();
                person6.setFirstName("Jeremiah");
                person6.setLastName("Smith");
                person6.setRealmID("6");
                person6.setMiddleName("");
                person6.setOptionalSuffix("");
                person6.setBirthday("");
                person6.setCity("");
                person6.setJob("");
                person6.setEmployer("");
                person6.setInterests("");
                person6.setMarried(false);
                person6.setAlive(true);
                person6.setSignificantOther(emptyList);

                Person person7 = new Person();
                person7.setFirstName("Bruce");
                person7.setLastName("Smith");
                person7.setRealmID("7");
                person7.setMiddleName("");
                person7.setOptionalSuffix("");
                person7.setBirthday("");
                person7.setCity("");
                person7.setJob("");
                person7.setEmployer("");
                person7.setInterests("");
                person7.setMarried(true);
                person7.setAlive(true);

                Person person8 = new Person();
                person8.setFirstName("Jill");
                person8.setLastName("Smith");
                person8.setRealmID("8");
                person8.setMiddleName("");
                person8.setOptionalSuffix("");
                person8.setBirthday("");
                person8.setCity("");
                person8.setJob("");
                person8.setEmployer("");
                person8.setInterests("");
                person8.setMarried(true);
                person8.setAlive(true);

                Person person9 = new Person();
                person9.setFirstName("Annie");
                person9.setLastName("Lee");
                person9.setRealmID("9");
                person9.setMiddleName("");
                person9.setOptionalSuffix("");
                person9.setBirthday("");
                person9.setCity("");
                person9.setJob("");
                person9.setEmployer("");
                person9.setInterests("");
                person9.setMarried(true);
                person9.setAlive(true);

                Person person10 = new Person();
                person10.setFirstName("Joe");
                person10.setLastName("Lee");
                person10.setRealmID("10");
                person10.setMiddleName("");
                person10.setOptionalSuffix("");
                person10.setBirthday("");
                person10.setCity("");
                person10.setJob("");
                person10.setEmployer("");
                person10.setInterests("");
                person10.setMarried(true);
                person10.setAlive(true);

                Person person11 = new Person();
                person11.setFirstName("Dustin");
                person11.setLastName("Lee");
                person11.setRealmID("11");
                person11.setMiddleName("");
                person11.setOptionalSuffix("");
                person11.setBirthday("");
                person11.setCity("");
                person11.setJob("");
                person11.setEmployer("");
                person11.setInterests("");
                person11.setMarried(false);
                person11.setAlive(true);
                person11.setSignificantOther(emptyList);

                Person person12 = new Person();
                person12.setFirstName("Joanna");
                person12.setLastName("Smith");
                person12.setRealmID("12");
                person12.setMiddleName("");
                person12.setOptionalSuffix("");
                person12.setBirthday("");
                person12.setCity("");
                person12.setJob("");
                person12.setEmployer("");
                person12.setInterests("");
                person12.setMarried(false);
                person12.setAlive(true);
                person12.setSignificantOther(emptyList);

                Person person13 = new Person();
                person13.setFirstName("Cassie");
                person13.setLastName("Smith");
                person13.setRealmID("13");
                person13.setMiddleName("");
                person13.setOptionalSuffix("");
                person13.setBirthday("");
                person13.setCity("");
                person13.setJob("");
                person13.setEmployer("");
                person13.setInterests("");
                person13.setMarried(false);
                person13.setAlive(true);
                person13.setSignificantOther(emptyList);

                Person person14 = new Person();
                person14.setFirstName("Jaime");
                person14.setLastName("Wick");
                person14.setRealmID("14");
                person14.setMiddleName("");
                person14.setOptionalSuffix("");
                person14.setBirthday("");
                person14.setCity("");
                person14.setJob("");
                person14.setEmployer("");
                person14.setInterests("");
                person14.setMarried(true);
                person14.setAlive(true);

                Person person15 = new Person();
                person15.setFirstName("John");
                person15.setLastName("Wick");
                person15.setRealmID("15");
                person15.setMiddleName("");
                person15.setOptionalSuffix("");
                person15.setBirthday("");
                person15.setCity("");
                person15.setJob("");
                person15.setEmployer("");
                person15.setInterests("");
                person15.setMarried(true);
                person15.setAlive(true);

                Person person16 = new Person();
                person16.setFirstName("Will");
                person16.setLastName("Wick");
                person16.setRealmID("16");
                person16.setMiddleName("");
                person16.setOptionalSuffix("");
                person16.setBirthday("");
                person16.setCity("");
                person16.setJob("");
                person16.setEmployer("");
                person16.setInterests("");
                person16.setMarried(false);
                person16.setAlive(true);
                person16.setSignificantOther(emptyList);

                Person person17 = new Person();
                person17.setFirstName("Ryan");
                person17.setLastName("Wick");
                person17.setRealmID("17");
                person17.setMiddleName("");
                person17.setOptionalSuffix("");
                person17.setBirthday("");
                person17.setCity("");
                person17.setJob("");
                person17.setEmployer("");
                person17.setInterests("");
                person17.setMarried(false);
                person17.setAlive(true);
                person17.setSignificantOther(emptyList);

                RealmList<Person> person1SO = new RealmList<Person>();
                person1SO.add(person2);
                person1.setSignificantOther(person1SO);
                person1.setMarried(true);
                RealmList<Person> person2SO = new RealmList<Person>();
                person2SO.add(person1);
                person2.setSignificantOther(person2SO);
                person2.setMarried(true);
                RealmList<Person> person3SO = new RealmList<Person>();
                person3SO.add(person4);
                person3.setSignificantOther(person3SO);
                person3.setMarried(true);
                RealmList<Person> person4SO = new RealmList<Person>();
                person3SO.add(person3);
                person4.setSignificantOther(person4SO);
                person4.setMarried(true);
                RealmList<Person> person7SO = new RealmList<Person>();
                person3SO.add(person8);
                person7.setSignificantOther(person7SO);
                person7.setMarried(true);
                RealmList<Person> person8SO = new RealmList<Person>();
                person3SO.add(person7);
                person8.setSignificantOther(person8SO);
                person8.setMarried(true);
                RealmList<Person> person9SO = new RealmList<Person>();
                person3SO.add(person10);
                person9.setSignificantOther(person9SO);
                person9.setMarried(true);
                RealmList<Person> person10SO = new RealmList<Person>();
                person3SO.add(person9);
                person10.setSignificantOther(person10SO);
                person10.setMarried(true);
                RealmList<Person> person14SO = new RealmList<Person>();
                person3SO.add(person15);
                person14.setSignificantOther(person14SO);
                person14.setMarried(true);
                RealmList<Person> person15SO = new RealmList<Person>();
                person3SO.add(person14);
                person15.setSignificantOther(person15SO);
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
//                familyList.add(testFam);
//                realm.copyToRealmOrUpdate(familyList);
                user.getTrees().add(testFam);
                realm.copyToRealmOrUpdate(user);
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}