//Tess Julien
package familytree.a188project1.du.bestdamtree;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tree extends RealmObject{

    //declare variables
    @PrimaryKey
    private String name;
    private RealmList<Person> people;

    //getters and setters
    public RealmList<Person> getPeople() {
        return people;
    }

    public void setPeople(RealmList<Person> people) {
        this.people = people;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
