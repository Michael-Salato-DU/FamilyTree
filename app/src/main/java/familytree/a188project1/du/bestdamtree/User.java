package familytree.a188project1.du.bestdamtree;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject{
    @PrimaryKey
    private String email;
    private String first_name;
    private String last_name;
    private String password;
    private Person person;
    private RealmList<Tree> trees;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public RealmList<Tree> getTrees() {
        return trees;
    }

    public void setTrees(RealmList<Tree> trees) {
        this.trees = trees;
    }
}