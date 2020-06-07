package macewan_dust.smiles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
    public static final String ARG_OPERATION_INDEX = "pass_smiles_index";

    private RecyclerView mSmilesRecyclerView;
    private RecyclerView.Adapter mSmilesRecyclerViewAdapter;
    private RecyclerView.LayoutManager mSmilesRecyclerViewLayoutManager;
    private List<SmilesItem> mSmilesData = new LinkedList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSmilesData = new LinkedList<>();
        SmilesItem temp1 = new SmilesItem(getString(R.string.title_sleep));
        SmilesItem temp2 = new SmilesItem(getString(R.string.title_movement));
        SmilesItem temp3 = new SmilesItem(getString(R.string.title_imagination));
        SmilesItem temp4 = new SmilesItem(getString(R.string.title_life_satisfaction));
        SmilesItem temp5 = new SmilesItem(getString(R.string.title_eating));
        SmilesItem temp6 = new SmilesItem(getString(R.string.title_speaking));

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
        View v = inflater.inflate(R.layout.fragment_smiles_descriptions_list, container, false);

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
            SmilesItem mSmilesItem; // for bundle information



            public SmilesViewHolder(View itemView) {
                super(itemView);

                mTitle = itemView.findViewById(R.id.text_smiles_category_heading);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick called");
               // int position = getAdapterPosition(); // find out where this view is in the list
                // gets Smiles item at views position, then gets the fragment out of it and loads it

                Fragment newFrag = new SmilesInformationFragment();
                Bundle opBundle = new Bundle();
                opBundle.putInt(ARG_OPERATION_INDEX, this.getAdapterPosition());
                newFrag.setArguments(opBundle);

                replaceFragment(newFrag);


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
                    .inflate(R.layout.list_item_smiles_descriptions, parent, false);

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
            holder.mTitle.setText(mSmilesListData.get(position).getTitle());
            holder.mSmilesItem = mSmilesListData.get(position);
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
