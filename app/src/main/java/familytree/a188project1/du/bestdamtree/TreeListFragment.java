//Tess Julien
package familytree.a188project1.du.bestdamtree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmList;

public class TreeListFragment extends Fragment {

    //declare variables
    private RecyclerView treeList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter treeAdapter;

    public TreeListFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tree_list, container, false);


        treeList = (RecyclerView)view.findViewById(R.id.tree_list);

        layoutManager = new LinearLayoutManager(getContext());
        treeList.setLayoutManager(layoutManager);
        refreshList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    //update list
    private void refreshList(){
        Realm realm = Realm.getDefaultInstance();
        TreeActivity treeActivity = (TreeActivity) this.getActivity();
        final RealmList<Person> family = treeActivity.testFam.getPeople();
        final TreeActivity activity = (TreeActivity) this.getActivity();

        //pass person and tree to person activity
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Person person = (Person)family.get(position);
                Intent intent = new Intent(view.getContext(), PersonActivity.class);
                intent.putExtra("person",person.getRealmID());
                intent.putExtra("family", treeActivity.testFam.getName());
                startActivity(intent);
            }
        };

        treeAdapter = new TreeListAdapter(getContext(), family, listener);
        treeList.setAdapter(treeAdapter);
    }
}


