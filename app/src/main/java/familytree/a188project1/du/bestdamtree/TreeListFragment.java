//Tess Julien
package familytree.a188project1.du.bestdamtree;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private void refreshList(){
        Realm realm = Realm.getDefaultInstance();
        TreeActivity treeActivity = (TreeActivity) this.getActivity();
        final RealmList<Person> family = treeActivity.testFam.getPeople();
        final TreeActivity activity = (TreeActivity) this.getActivity();
        Log.d("refresh", "in RefreshList");
        for (int i = 0; i<family.size(); i++){
            Log.d("person", family.get(i).getFirstName());
        }

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


