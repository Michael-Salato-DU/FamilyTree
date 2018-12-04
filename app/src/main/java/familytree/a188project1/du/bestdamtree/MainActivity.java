package familytree.a188project1.du.bestdamtree;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    // Declare variables
    private DrawerLayout mDrawerLayout;
    private Button newTreeButton;
    private RecyclerView treeList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter treeAdapter;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get realm instance
        Realm realm = Realm.getDefaultInstance();
        // Get the user with the email address passed from LoginActivity
        String currentEmail = (String) getIntent().getStringExtra("current_email");
        user = realm.where(User.class).equalTo("email", currentEmail).findFirst();

        // Log for checking that the correct User is active
        Log.d("UserEmail", user.getEmail());

        // source for setting the toolbar as the action bar:
        // title: Create a navigation drawer: Set the toolbar as the action bar
        // author: Android Developers Documentation
        // https://developer.android.com/training/implementing-navigation/nav-drawer#SetToolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // end source

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

                        // Open the appropriate activity or fragment based on the selected menu item
                        // Open your profile to edit your card
                        if (menuItem.getTitle().equals("Profile")) {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("current_email", user.getEmail());
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
//                            intent.putExtra("current_email", user.getEmail());
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

        // Tie variable to the respective view
        newTreeButton = (Button) findViewById(R.id.new_tree_button);

        // Open an activity to create a new tree when the "New Tree" button is clicked
        newTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("current_email", user.getEmail());
                startActivity(intent);
            }
        });

        // Find all tree names in realm
//        final RealmResults<Tree> treeNamesRealm = realm.where(User.class).findAll();
//
//        //Add all event objects found in Realm to a list to be displayed in the fragment
//        RealmList<String> trees = new RealmList<String>();
//        trees.addAll(allEventsRealm.subList(0, allEventsRealm.size()));
        treeList = (RecyclerView) findViewById(R.id.tree_list);

        // set LayoutMangager
        layoutManager = new LinearLayoutManager(this);
        treeList.setLayoutManager(layoutManager);

        // list of tree names (remove)
        List<String> treeNames = new ArrayList<String>();
        treeNames.add("Smith");
        treeNames.add("Singh");

        // Clicking on an event in the list will open up a TreeActivity.
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Determine which tree is selected.
                String treeName = (String) treeNames.get(position);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                // Pass tree name and user email to the next activity.
                intent.putExtra("event", treeName);
                intent.putExtra("current_email", user.getEmail());
                startActivity(intent);
            }

        };

        // Create a TreeAdapter and pass in the list of trees
        treeAdapter = new TreeAdapter(this, treeNames);
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
}