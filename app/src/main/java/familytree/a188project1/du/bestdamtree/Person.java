package familytree.a188project1.du.bestdamtree;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject{
    @PrimaryKey
    private int RealmID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String optionalSuffix;
    private RealmList<Person> parents;
    private Person significantOther;
    private boolean married;
    private RealmList<Person> kids;
    private String birthday;
    private String job;
    private String employer;
    private String city;
    private String likes;
    private String dislikes;
    private String notes;
    private boolean alive;
    private byte[] image;


    public int getRealmID() {
        return RealmID;
    }

    public void setRealmID(int realmID) {
        RealmID = realmID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOptionalSuffix() {
        return optionalSuffix;
    }

    public void setOptionalSuffix(String optionalSuffix) {
        this.optionalSuffix = optionalSuffix;
    }

    public RealmList<Person> getParents() {
        return parents;
    }

    public void setParents(RealmList<Person> parents) {
        this.parents = parents;
    }

    public Person getSignificantOther() {
        return significantOther;
    }

    public void setSignificantOther(Person significantOther) {
        this.significantOther = significantOther;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public RealmList<Person> getKids() {
        return kids;
    }

    public void setKids(RealmList<Person> kids) {
        this.kids = kids;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
