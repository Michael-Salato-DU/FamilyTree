//source: https://youtu.be/fn5OlqQuOCk

package familytree.a188project1.du.bestdamtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class PersonActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person);

        DisplayMetrics screenSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenSize);
        int width = screenSize.widthPixels;
        int height = screenSize.heightPixels;

        getWindow().setLayout((int)(width*.85), (int)(height*.85));

        Button addConnection = (Button) findViewById(R.id.add_connection);
        addConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewPersonActivity.class);
                startActivity(intent);
            }
        });
    }
}
