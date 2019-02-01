package macewan_dust.smiles;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DailyListActivity extends Fragment {

    private static final String TAG = "DailyListActivity";

    private RecyclerView mDailyRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<DailyItem> mDailyData = new LinkedList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// generating some items for testing
        DailyItem temp1 = new DailyItem(R.drawable.icon_sleep, R.drawable.border_image, getString(R.string.quest_sleep_title), "Score your sleep");
        DailyItem temp2 = new DailyItem(R.drawable.icon_movement,  R.drawable.border_image_balanced, getString(R.string.quest_movement_title), "Measure your movement");
        DailyItem temp3 = new DailyItem(R.drawable.icon_imagination,  R.drawable.border_image_low, getString(R.string.quest_imagination_title), "Rate your imagination");
        DailyItem temp4 = new DailyItem(R.drawable.icon_laughter,  R.drawable.border_image_high, getString(R.string.quest_laughter_title), "Rank your laughter");
        DailyItem temp5 = new DailyItem(R.drawable.icon_eating,  R.drawable.border_image, getString(R.string.quest_eating_title), "Balance your eating");
        DailyItem temp6 = new DailyItem(R.drawable.icon_speaking,  R.drawable.border_image_balanced, getString(R.string.quest_speaking_title), "Score your speaking");

        mDailyData.add(temp1);
        mDailyData.add(temp2);
        mDailyData.add(temp3);
        mDailyData.add(temp4);
        mDailyData.add(temp5);
        mDailyData.add(temp6);





    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daily_list, container, false);

        mDailyRecyclerView = v.findViewById(R.id.daily_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mDailyRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DailyAdapter(mDailyData);
        mDailyRecyclerView.setAdapter(mAdapter);
        mDailyRecyclerView.setHasFixedSize(true);
        return v;

    }

    public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {

        private List<DailyItem> mDailyData;


      /**
       * reference for views for each data item
       */
      public class DailyViewHolder extends RecyclerView.ViewHolder {
            public ImageView mIcon;
            public TextView mTitle;
            public TextView mSubtitle;

            public DailyViewHolder(View itemView){
                super(itemView);
                mIcon = itemView.findViewById(R.id.list_item_daily_category);
                mTitle = itemView.findViewById(R.id.list_item_daily_title);
                mSubtitle = itemView.findViewById(R.id.list_item_daily_subtitle);
            }
        }

      public DailyAdapter(List<DailyItem> dailyData) {
          mDailyData = dailyData;

      }

      // creates new views
      public DailyAdapter.DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

          // create one new view
          View v = LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.list_item_daily, parent, false);

          DailyViewHolder vh = new DailyViewHolder(v);
          return vh;
      }

      // change contents of a view


      @Override
      public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
          holder.mIcon.setImageResource(mDailyData.get(position).getIconID());
          holder.mIcon.setBackground(getResources().getDrawable(mDailyData.get(position).getBackgroundID()));
          holder.mTitle.setText(mDailyData.get(position).getTitle());
          holder.mSubtitle.setText(mDailyData.get(position).getSubtitle());
      }

      // returns size of dataset

      @Override
      public int getItemCount() {
          return mDailyData.size();
      }
  }

}

