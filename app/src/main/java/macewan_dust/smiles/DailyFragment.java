package macewan_dust.smiles;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DailyFragment {

    private static final String TAG = "DailyFragment";

    private RecyclerView mDailyRecyclerView;
    private List<DailyItem> mDailyItems = new ArrayList<DailyItem>();

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }



}
