package familytree.a188project1.du.bestdamtree;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class NewPersonActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_person);

        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);
        int width = screenSize.widthPixels;
        int height = screenSize.heightPixels;

        getWindow().setLayout((int)(width*.85), (int)(height*.85));
    }
}
