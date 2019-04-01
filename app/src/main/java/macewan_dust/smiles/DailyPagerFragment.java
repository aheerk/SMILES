package macewan_dust.smiles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Ref: https://stackoverflow.com/questions/19073541/how-to-set-a-viewpager-inside-a-fragment
 * Ref: https://stackoverflow.com/questions/29847194/setting-action-bar-title-in-viewpager
 * Ref: https://stackoverflow.com/questions/43675161/how-to-work-with-viewpager-onpagechangelistener
 */
public class DailyPagerFragment extends Fragment {

    private static final String TAG = "DailyPagerFragment";
    ViewPager mViewPager;
    ImageView mDot1;
    ImageView mDot2;
    ImageView mDot3;
    ImageView mDot4;
    ImageView mDot5;
    ImageView mDot6;

    public static final int EXIT_TIMER_MILLISECONDS = 7000;

    int mStartPage;
    Date mScoreDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStartPage = this.getArguments().getInt(DailyListFragment.DAILY_RECYCLER_VIEW_INDEX);
        mScoreDate = new Date(this.getArguments().getLong(DailyListFragment.DAILY_DATE));
        getActivity().setTitle(getString(R.string.title_sleep));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daily_pager, container, false);

        mDot1 = v.findViewById(R.id.pager_dot_1);
        mDot2 = v.findViewById(R.id.pager_dot_2);
        mDot3 = v.findViewById(R.id.pager_dot_3);
        mDot4 = v.findViewById(R.id.pager_dot_4);
        mDot5 = v.findViewById(R.id.pager_dot_5);
        mDot6 = v.findViewById(R.id.pager_dot_6);

        mDot1.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_current_page));

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = view.findViewById(R.id.daily_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);

        ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                getActivity().setTitle(mViewPager.getAdapter().getPageTitle(position));
//                Log.d(TAG, "position: " + position);

                resetDots(position);
                switch(position){

                    case 0:
                        mDot1.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_current_page));
                        break;
                    case 1:
                        mDot2.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_current_page));
                        break;
                    case 2:
                        mDot3.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_current_page));
                        break;
                    case 3:
                        mDot4.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_current_page));
                        break;
                    case 4:
                        mDot5.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_current_page));
                        break;
                    case 5:
                        mDot6.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_current_page));
                        break;
                }
            }

            private void resetDots(int position){

                if (position != 0) {
                    mDot1.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_other_page));
                }
                mDot2.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_other_page));
                mDot3.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_other_page));
                mDot4.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_other_page));
                mDot5.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_other_page));
                mDot6.setImageDrawable(getResources().getDrawable(R.drawable.dot_image_other_page));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
        mViewPager.addOnPageChangeListener(listener);

        // same bundle for all fragments.
        Bundle opBundle = new Bundle();
        opBundle.putLong(DailyListFragment.DAILY_DATE, mScoreDate.getTime());

        // date passing
        Fragment temp1 = new InputSleepFragment();
        temp1.setArguments(opBundle);

        Fragment temp2 = new InputMovementFragment();
        temp2.setArguments(opBundle);

        Fragment temp3 = new InputImaginationFragment();
        temp3.setArguments(opBundle);

        Fragment temp4 = new InputLaughterFragment();
        temp4.setArguments(opBundle);

        Fragment temp5 = new InputEatingFragment();
        temp5.setArguments(opBundle);

        Fragment temp6 = new InputSpeakingFragment();
        temp6.setArguments(opBundle);

        adapter.addFragment(temp1, getString(R.string.title_sleep));
        adapter.addFragment(temp2,getString(R.string.title_movement));
        adapter.addFragment(temp3,getString(R.string.title_imagination));
        adapter.addFragment(temp4,getString(R.string.title_laughter));
        adapter.addFragment(temp5,getString(R.string.title_eating));
        adapter.addFragment(temp6,getString(R.string.title_speaking));

        mViewPager.setCurrentItem(mStartPage);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mTitlesList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitlesList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
            mTitlesList.add("");
            notifyDataSetChanged();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mTitlesList.add(title);
            notifyDataSetChanged();
        }
    }
}
