package familytree.a188project1.du.bestdamtree;

import android.app.Application;

import io.realm.Realm;

public class FamilyTreeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}