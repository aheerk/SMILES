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

public class InfoListFragment extends Fragment {

    private static final String TAG = "InfoListFragment";

    private RecyclerView mInfoRecyclerView;
    private RecyclerView.Adapter mInfoRecyclerViewAdapter;
    private RecyclerView.LayoutManager mInfoRecyclerViewLayoutManager;
    private List<InfoItem> mInfoData = new LinkedList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// generating some items for testing                                                                    /////-------- refactor this
        InfoItem temp4 = new InfoItem(R.drawable.icon_one,
                "Introduction", "subtitle", new IntroductionFragment());
        InfoItem temp1 = new InfoItem(R.drawable.icon_one,
                "SMILES", "subtitle", new SmilesInfo());
        InfoItem temp2 = new InfoItem(R.drawable.icon_one,
                "Web links", "subtitle", new WebInfoFragment());
        InfoItem temp3 = new InfoItem(R.drawable.icon_one,
                "Credit", "subtitle", new CreditInfoFragment());

        mInfoData.add(temp4);
        mInfoData.add(temp1);
        mInfoData.add(temp2);
        mInfoData.add(temp3);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_list, container, false);

        mInfoRecyclerView = v.findViewById(R.id.info_recycler_view);
        mInfoRecyclerViewLayoutManager = new LinearLayoutManager(this.getActivity());
        mInfoRecyclerView.setLayoutManager(mInfoRecyclerViewLayoutManager);
        mInfoRecyclerViewAdapter = new InfoListFragment.InfoAdapter(mInfoData);
        mInfoRecyclerView.setAdapter(mInfoRecyclerViewAdapter);
        mInfoRecyclerView.setHasFixedSize(true);
        return v;
    }

    public class InfoAdapter extends RecyclerView.Adapter<InfoListFragment.InfoAdapter.InfoViewHolder> {

        private List<InfoItem> mInfoListData;

        /**
         * Reference for views for each data item.
         * Set the image and text for the recycler view list here.
         * <p>
         * Optional: implementing on click listener and onClick method
         */
        public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView mIcon;
            public TextView mTitle;
            public TextView mSubtitle;

            public InfoViewHolder(View itemView) {
                super(itemView);
                mIcon = itemView.findViewById(R.id.list_item_info_image);
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
        public InfoAdapter(List<InfoItem> infoListData) {
            mInfoListData = infoListData;
        }

        /**
         * Creates new views for each list item.
         * Specify what view layout is used here.
         */

        public InfoListFragment.InfoAdapter.InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {              // ----- what is the role of viewType here?

            // create one new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_info, parent, false);

            InfoListFragment.InfoAdapter.InfoViewHolder vh = new InfoListFragment.InfoAdapter.InfoViewHolder(v);
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
        public void onBindViewHolder(@NonNull InfoListFragment.InfoAdapter.InfoViewHolder holder, int position) {
            holder.mIcon.setImageResource(mInfoListData.get(position).getIconID());
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
