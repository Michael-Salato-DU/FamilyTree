package familytree.a188project1.du.bestdamtree;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Tree extends RealmObject{
    RealmList<Person> people;

    public RealmList<Person> getPeople() {
        return people;
    }

    public void setPeople(RealmList<Person> people) {
        this.people = people;
    }
}
