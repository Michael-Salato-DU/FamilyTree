package familytree.a188project1.du.bestdamtree;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class HelpfulHintsActivity extends AppCompatActivity {

    // Declare variables
    private TextView hintsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpful_hints);

        // Source for creating a pop up window
        // title: How To Create Pop Up Window In Android
        // author: Filip Vujovic
        // https://www.youtube.com/watch?v=fn5OlqQuOCk&list=FLDplgDwlwCvYtcIIncET1tA&index=2&t=0s

        // Get metrics about this device (including screen dimensions)
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // Get width and height of screen
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // Set the pop up window's width and height
        getWindow().setLayout((int)(width*0.8), (int)(height*0.7));
        // end source

        // Tie hintsView to the TextView
        hintsView = (TextView) findViewById(R.id.hints_view);

        // Source for setMovementMethod to create a scrollable textView: How to Make Scrollable TextView in Android
        // Also added android:scrollbars="vertical" in fragment_band_profile.xml
        // https://www.viralandroid.com/2015/10/how-to-make-scrollable-textview-in-android.html
        // author: Pacific Regmi
        // date: April 4, 2018
        hintsView.setMovementMethod(new ScrollingMovementMethod());
        // end source

        // Set the list of hints
        hintsView.setText("You can have multiple family trees. We suggest at least creating two — one for each parent. \n\n" +
                "Quizzes will be generated based on which family tree you have open at the time. If you want to switch trees, head to “Your Trees” page in the menu and switch the tree you’re using.\n\n" +
                "If you want to add a new member to your tree, click on the leaf of whom your added person is related to, and scroll to the bottom where it says “add a connection.” Make sure to choose how they are related — the options are parent, child, or spouse. This way the tree will create your family lineage for you.\n\n" +
                "As you make your first tree, we suggest you create your leaves in this order: yours, your parents, your grandparents. Fill in subsequent family members from there and you won’t run into any issues. \n\n" +
                "The Who’s Who quiz will test you on how all your family members are related to one another. \n\n" +
                "The trivia quiz will test you on where your family members work or go to school, what their hobbies are, and when their birthdays are.\n\n" +
                "The Hybrid quiz is a mix of both quizzes’ questions.\n");
    }
}
