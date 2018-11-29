package familytree.a188project1.du.bestdamtree;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class TreeListFragment extends Fragment {

    private RecyclerView treeList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter treeAdapter;

    public TreeListFragment() {
        //Required empty public constructor
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tree_list, container, false);

        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Person> family = realm.where(Person.class).findAll();
        treeList = (RecyclerView)view.findViewById(R.id.tree_list);

//        Person person1 = new Person();
//        person1.setRealmID(1);
//        person1.setFirstName("Walt");
//        person1.setLastName("Disney");
//        Person person2 = new Person();
//        person2.setRealmID(2);
//        person2.setFirstName("Queen of");
//        person2.setLastName("Arendelle");
////        RealmList<Person> grandparents = new RealmList<>();
////        grandparents.add(person1);
////        person2.setParents(grandparents);
//        Person person3 = new Person();
//        person3.setRealmID(3);
//        person3.setFirstName("Queen");
//        person3.setLastName("of Corona");
//        //person3.setParents(grandparents);
//        Person person4 = new Person();
//        person4.setRealmID(4);
//        person4.setFirstName("King of");
//        person4.setLastName("Arendelle");
////        person4.setSignificantOther(person2);
////        person2.setSignificantOther(person4);
//        Person person5 = new Person();
//        person5.setRealmID(5);
//        person5.setFirstName("King");
//        person5.setLastName("of Corona");
////        person5.setSignificantOther(person3);
////        person3.setSignificantOther(person5);
//        Person person6 = new Person();
//        person6.setRealmID(6);
//        person6.setFirstName("Elsa");
//        person6.setLastName("Arendelle");
//        Person person7 = new Person();
//        person7.setRealmID(7);
//        person7.setFirstName("Anna");
//        person7.setLastName("Arendelle");
////        RealmList<Person> arendelleParents = new RealmList<>();
////        arendelleParents.add(person2);
////        arendelleParents.add(person4);
////        RealmList<Person> coronaParents = new RealmList<>();
////        coronaParents.add(person3);
////        coronaParents.add(person5);
////        person6.setParents(arendelleParents);
////        person7.setParents(arendelleParents);
//        Person person8 = new Person();
//        person8.setRealmID(8);
//        person8.setFirstName("Rapunzel");
//        person8.setLastName("of Corona");
//        //person8.setParents(coronaParents);
//        family.add(person1);
//        family.add(person2);
//        family.add(person3);
//        family.add(person4);
//        family.add(person5);
//        family.add(person6);
//        family.add(person7);
//        family.add(person8);

        layoutManager = new LinearLayoutManager(getContext());
        treeList.setLayoutManager(layoutManager);

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Person person = (Person)family.get(position);
                Intent intent = new Intent(view.getContext(), PersonActivity.class);
                intent.putExtra("person", person.getRealmID());
                startActivity(intent);
            }
        };

        treeAdapter = new TreeListAdapter(getContext(), family, listener);
        treeList.setAdapter(treeAdapter);

        return view;
    }
}


