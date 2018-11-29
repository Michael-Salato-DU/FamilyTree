package familytree.a188project1.du.bestdamtree;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import io.realm.RealmList;
import io.realm.RealmResults;

public class TreeListAdapter extends RecyclerView.Adapter<TreeListAdapter.TreeViewHolder> {
    private Context context;
    private RealmResults<Person> family;
    private RecyclerViewClickListener mListener;

    public TreeListAdapter(Context context, RealmResults<Person> dataSet, RecyclerViewClickListener clickListener) {
        this.context = context;
        this.family = dataSet;
        this.mListener = clickListener;
    }

    public static class TreeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView firstNameView;
        public TextView lastNameView;
        private RecyclerViewClickListener mListener;
        public TreeViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            firstNameView = v.findViewById(R.id.first_name_view);
            lastNameView = v.findViewById(R.id.last_name_view);
            mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return family.size();
    }

    @Override
    public TreeListAdapter.TreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_cell, parent, false);
        TreeViewHolder vh = new TreeViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(TreeViewHolder holder, int position) {
        holder.firstNameView.setText(family.get(position).getFirstName());
        holder.lastNameView.setText(family.get(position).getLastName());
    }
}
