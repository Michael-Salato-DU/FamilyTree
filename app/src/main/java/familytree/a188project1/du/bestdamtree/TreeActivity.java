//Tess Julien and Michael Salato
package familytree.a188project1.du.bestdamtree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmList;

public class TreeActivity extends AppCompatActivity {

    //declare variables
    private RecyclerView treeList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter treeAdapter;
    private DrawerLayout mDrawerLayout;
    public Tree testFam;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);

        //get intent extras
        Realm realm = Realm.getDefaultInstance();
        String family = (String) getIntent().getStringExtra("family");
        testFam = realm.where(Tree.class).equalTo("name", family).findFirst();
        // Get the user with the email address passed from MainActivity
        String currentEmail = (String) getIntent().getStringExtra("current_email");
        user = realm.where(User.class).equalTo("email", currentEmail).findFirst();

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

        // Set family name in the toolbar
        super.setTitle(testFam.getName() + " Family");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), QuizActivity.class);
                intent.putExtra("family", testFam.getName());
                intent.putExtra("current_email", user.getEmail());
                startActivity(intent);
            }
        });

        treeList = (RecyclerView) findViewById(R.id.tree_list);

        layoutManager = new LinearLayoutManager(this);
        treeList.setLayoutManager(layoutManager);
        refreshList();

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

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    //update list function
    private void refreshList(){
        Realm realm = Realm.getDefaultInstance();
        final RealmList<Person> family = testFam.getPeople();

        //open person activity when item is selected
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //pass person and tree to person activity
                Person person = (Person)family.get(position);
                Intent intent = new Intent(view.getContext(), PersonActivity.class);
                intent.putExtra("person",person.getRealmID());
                intent.putExtra("family", testFam.getName());
                startActivity(intent);
            }
        };

        treeAdapter = new TreeListAdapter(this, family, listener);
        treeList.setAdapter(treeAdapter);
    }

}
