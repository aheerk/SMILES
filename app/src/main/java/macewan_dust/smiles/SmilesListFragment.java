package macewan_dust.smiles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class SmilesListFragment extends Fragment {

    private static final String TAG = "SmilesListFragment";

    private RecyclerView mSmilesRecyclerView;
    private RecyclerView.Adapter mSmilesRecyclerViewAdapter;
    private RecyclerView.LayoutManager mSmilesRecyclerViewLayoutManager;
    private List<SmilesItem> mSmilesData = new LinkedList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// generating some items for testing                                                                    /////-------- refactor this
        SmilesItem temp1 = new SmilesItem(R.drawable.icon_sleep,"S","leep");
        SmilesItem temp2 = new SmilesItem(R.drawable.icon_sleep,"M","ovement");
        SmilesItem temp3 = new SmilesItem(R.drawable.icon_sleep,"I","magination");
        SmilesItem temp4 = new SmilesItem(R.drawable.icon_sleep,"L","aughter");
        SmilesItem temp5 = new SmilesItem(R.drawable.icon_sleep,"E","ating");
        SmilesItem temp6 = new SmilesItem(R.drawable.icon_sleep,"S","peaking");


        mSmilesData.add(temp1);
        mSmilesData.add(temp2);
        mSmilesData.add(temp3);
        mSmilesData.add(temp4);
        mSmilesData.add(temp5);
        mSmilesData.add(temp6);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_smiles_info_list, container, false);

        mSmilesRecyclerView = v.findViewById(R.id.smiles_recycler_view);
        mSmilesRecyclerViewLayoutManager = new LinearLayoutManager(this.getActivity());
        mSmilesRecyclerView.setLayoutManager(mSmilesRecyclerViewLayoutManager);
        mSmilesRecyclerViewAdapter = new SmilesListFragment.SmilesAdapter(mSmilesData);
        mSmilesRecyclerView.setAdapter(mSmilesRecyclerViewAdapter);
        mSmilesRecyclerView.setHasFixedSize(true);

        getActivity().setTitle(R.string.introduction_title_smiles);

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.introduction_title_smiles);

    }

    public class SmilesAdapter extends RecyclerView.Adapter<SmilesListFragment.SmilesAdapter.SmilesViewHolder> {

        private List<SmilesItem> mSmilesListData;

        /**
         * Reference for views for each data item.
         * Set the image and text for the recycler view list here.
         * <p>
         * Optional: implementing on click listener and onClick method
         */
        public class SmilesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView mIcon;
            public TextView mTitleCapitalLetter;
            public TextView mTitle;



            public SmilesViewHolder(View itemView) {
                super(itemView);
                mIcon = itemView.findViewById(R.id.icon_smiles_list);
                mTitle = itemView.findViewById(R.id.text_smiles_capital_letter);
                mTitleCapitalLetter = itemView.findViewById(R.id.text_smiles_lower_case_letter);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick called");
               // int position = getAdapterPosition(); // find out where this view is in the list
                // gets Smiles item at views position, then gets the fragment out of it and loads it
              //  replaceFragment(mSmilesData.get(position).getFragment());
            }
        }

        /*
         * Passes the list data for use by the system
         */
        public SmilesAdapter(List<SmilesItem> SmilesListData) {
            mSmilesListData = SmilesListData;
        }

        /**
         * Creates new views for each list item.
         * Specify what view layout is used here.
         */

        public SmilesListFragment.SmilesAdapter.SmilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {              // ----- what is the role of viewType here?

            // create one new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_smiles_info, parent, false);

            SmilesListFragment.SmilesAdapter.SmilesViewHolder vh = new SmilesListFragment.SmilesAdapter.SmilesViewHolder(v);
            return vh;
        }


        /**
         * Called by the system
         * Sets the contents of a view that has already been created.
         *
         * @param holder   - the individual list objects
         * @param position - index location in the list of data
         */
        @Override
        public void onBindViewHolder(@NonNull SmilesListFragment.SmilesAdapter.SmilesViewHolder holder, int position) {
            holder.mIcon.setImageResource(mSmilesListData.get(position).getIconID());
            holder.mTitleCapitalLetter.setText(mSmilesListData.get(position).getTitle());
            holder.mTitle.setText(mSmilesListData.get(position).getTitle());
        }

        /**
         * Required by recycler view
         * * returns size of dataset
         */
        @Override
        public int getItemCount() {
            return mSmilesListData.size();
        }
    }

    /**
     * replaceFragment - performs fragment transactions.
     *
     * @param newFragment
     */
    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        // the back button on phones will return to this item. name is optional
        transaction.addToBackStack("SmilesList");
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }





}
