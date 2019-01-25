package macewan_dust.smiles;


import android.support.v7.widget.RecyclerView;
import android.support.v4.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class DailyFragment extends Fragment {

    private static final String TAG = "DailyFragment";

    private RecyclerView mDailyRecyclerView;
    private List<DailyItem> mDailyItems = new ArrayList<DailyItem>();

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }



}
