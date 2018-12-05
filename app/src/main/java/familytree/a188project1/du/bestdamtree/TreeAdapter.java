package familytree.a188project1.du.bestdamtree;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.realm.RealmList;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.TreeViewHolder> {

    // Declare variables
    private Context context;
    private RealmList<Tree> treeList;
    private RecyclerViewClickListener mListener;

    // Constructor for TreeAdapter. Sets the context and list of trees (families).
    public TreeAdapter(Context context, RealmList<Tree> dataSet, RecyclerViewClickListener clickListener) {
        this.context = context;
        this.treeList = dataSet;
        this.mListener = clickListener;
    }

    // TreeViewHolder class
    public static class TreeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Declare view variable
        public TextView treeNameView;
        private RecyclerViewClickListener mListener;

        // TreeViewHolder ties the view variable to the respective view in tree_name_cell.xml
        public TreeViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);
            treeNameView = v.findViewById(R.id.tree_name_view);
            mListener = listener;
            v.setOnClickListener(this);
        }

        // Set onClick function for recycler view
        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    // getItemCount() returns the number of Trees in the list
    @Override
    public int getItemCount() {
        return treeList.size();
    }

    @Override
    public TreeAdapter.TreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tree_name_cell, parent, false);
        // Call TreeViewHolder function
        TreeViewHolder vh = new TreeViewHolder(v, mListener);
        return vh;
    }

    // onBindViewHolder() sets the tree name
    @Override
    public void onBindViewHolder(TreeViewHolder holder, int position) {
        // set tree name
        holder.treeNameView.setText(treeList.get(position));
        }
}
