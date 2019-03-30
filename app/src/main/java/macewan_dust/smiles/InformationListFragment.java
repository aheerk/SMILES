package macewan_dust.smiles;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.List;

public class InformationListFragment extends Fragment {

    private static final String TAG = "InformationListFragment";

    private RecyclerView mInfoRecyclerView;
    private RecyclerView.Adapter mInfoRecyclerViewAdapter;
    private RecyclerView.LayoutManager mInfoRecyclerViewLayoutManager;
    private List<InformationItem> mInfoData = new LinkedList<>();

    // temp
    private Button mButtonNewUserTest;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mInfoData = new LinkedList<>();
        InformationItem temp4 = new InformationItem(getString(R.string.title_introduction), "Getting started information", new IntroductionFragment());
        InformationItem temp1 = new InformationItem(getString(R.string.title_smiles), "Categories", new SmilesListFragment());
        InformationItem temp2 = new InformationItem(getString(R.string.title_web_links), "Useful information", new WebListFragment());
        InformationItem temp3 = new InformationItem(getString(R.string.title_credits), getString(R.string.subtitle_credits), new CreditInfoFragment());

        mInfoData.add(temp4);
        mInfoData.add(temp1);
        mInfoData.add(temp2);
        mInfoData.add(temp3);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information_list, container, false);

        mInfoRecyclerView = v.findViewById(R.id.info_recycler_view);
        mInfoRecyclerViewLayoutManager = new LinearLayoutManager(this.getActivity());
        mInfoRecyclerView.setLayoutManager(mInfoRecyclerViewLayoutManager);
        mInfoRecyclerViewAdapter = new InformationListFragment.InfoAdapter(mInfoData);
        mInfoRecyclerView.setAdapter(mInfoRecyclerViewAdapter);
        mInfoRecyclerView.setHasFixedSize(true);


// temp
        mButtonNewUserTest = v.findViewById(R.id.button_new_user_test);
        mButtonNewUserTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){


                Intent newActivity = new Intent(getContext(), NewUserActivity.class);
                getContext().startActivity(newActivity);



                //   SplashFragment fragment = new SplashFragment();
                //  replaceFragment(fragment);
            }
        });

        getActivity().setTitle(R.string.title_information);
        mButtonNewUserTest.setVisibility(View.GONE);

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_information);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public class InfoAdapter extends RecyclerView.Adapter<InformationListFragment.InfoAdapter.InfoViewHolder> {

        private List<InformationItem> mInfoListData;

        /**
         * Reference for views for each data item.
         * Set the image and text for the recycler view list here.
         * <p>
         * Optional: implementing on click listener and onClick method
         */
        public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView mTitle;
            public TextView mSubtitle;

            public InfoViewHolder(View itemView) {
                super(itemView);
                mTitle = itemView.findViewById(R.id.list_item_info_title);
                mSubtitle = itemView.findViewById(R.id.list_item_info_subtitle);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick called");
                int position = getAdapterPosition(); // find out where this view is in the list
                // gets info item at views position, then gets the fragment out of it and loads it
                replaceFragment(mInfoData.get(position).getFragment());
            }
        }

        /*
         * Passes the list data for use by the system
         */
        public InfoAdapter(List<InformationItem> infoListData) {
            mInfoListData = infoListData;
        }

        /**
         * Creates new views for each list item.
         * Specify what view layout is used here.
         */

        public InformationListFragment.InfoAdapter.InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {              // ----- what is the role of viewType here?

            // create one new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_information, parent, false);

            InformationListFragment.InfoAdapter.InfoViewHolder vh = new InformationListFragment.InfoAdapter.InfoViewHolder(v);
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
        public void onBindViewHolder(@NonNull InformationListFragment.InfoAdapter.InfoViewHolder holder, int position) {
            holder.mTitle.setText(mInfoListData.get(position).getTitle());
            holder.mSubtitle.setText(mInfoListData.get(position).getSubtitle());
        }

        /**
         * Required by recycler view
         * * returns size of dataset
         */
        @Override
        public int getItemCount() {
            return mInfoListData.size();
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
        transaction.addToBackStack("infoList");
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }





}
