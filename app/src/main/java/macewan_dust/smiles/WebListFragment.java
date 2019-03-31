package macewan_dust.smiles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This class is used to display the list of web links in the Information section
 */
public class WebListFragment extends Fragment {

    public static final String TAG = "Weblinks";
    private RecyclerView mWebRecyclerView;
    private RecyclerView.Adapter mWebRecyclerViewAdapter;
    private RecyclerView.LayoutManager mWebRecyclerViewLayoutManager;
    private WebLab mWebLab;
    private List<WebItem> mWebItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.

        mWebLab = WebLab.getWebLab(getContext());
        mWebItems = mWebLab.getWebList(this.getContext());

        ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weblinks_list, container, false);

        mWebRecyclerView = v.findViewById(R.id.web_recycler_view);
        mWebRecyclerViewLayoutManager = new LinearLayoutManager(this.getActivity());
        mWebRecyclerView.setLayoutManager(mWebRecyclerViewLayoutManager);
        mWebRecyclerViewAdapter = new WebListFragment.WebAdapter(mWebItems);
        mWebRecyclerView.setAdapter(mWebRecyclerViewAdapter);
        mWebRecyclerView.setHasFixedSize(true);

        getActivity().setTitle(R.string.title_web_links);

        DividerItemDecoration itemDecor = new DividerItemDecoration(mWebRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mWebRecyclerView.addItemDecoration(itemDecor);

        return v;
    }


    public static WebListFragment newInstance() {
        return new WebListFragment();
    }

    public class WebAdapter extends RecyclerView.Adapter<WebListFragment.WebAdapter.WebViewHolder> {

        private List<WebItem> mWebListData;

        /**
         * Reference for views for each data item.
         * Set the image and text for the recycler view list here.
         * <p>
         * Optional: implementing on click listener and onClick method
         */
        public class WebViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView mTitle;
            public TextView mSubtitle;

            public WebViewHolder(View itemView) {
                super(itemView);
                mTitle = itemView.findViewById(R.id.list_item_web_title);
                mSubtitle = itemView.findViewById(R.id.list_item_web_subtitle);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick called");

                // Open the link of the selected item
                int position = getAdapterPosition();
                MainActivity activity = (MainActivity) getActivity();
                activity.openLink(mWebListData.get(position).getUri());
            }
        }

        /*
         * Passes the list data for use by the system
         */
        public WebAdapter(List<WebItem> webListData) {
            mWebListData = webListData;
        }

        /**
         * Creates new views for each list item.
         * Specify what view layout is used here.
         */
        public WebListFragment.WebAdapter.WebViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {              // ----- what is the role of viewType here?

            // create one new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_weblinks, parent, false);

            WebListFragment.WebAdapter.WebViewHolder vh = new WebListFragment.WebAdapter.WebViewHolder(v);
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
        public void onBindViewHolder(@NonNull WebListFragment.WebAdapter.WebViewHolder holder, int position) {
            holder.mTitle.setText(mWebListData.get(position).getTitle());
            holder.mSubtitle.setText(mWebListData.get(position).getSubtitle());
        }

        /**
         * Required by recycler view
         * * returns size of dataset
         */
        @Override
        public int getItemCount() {
            return mWebListData.size();
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
        transaction.addToBackStack("webList");
        transaction.commit();
        Log.d(TAG, "replacing fragment");
    }

}
