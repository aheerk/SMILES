package macewan_dust.smiles;

import android.support.v4.app.Fragment;

public class DailyItem {

    private int mIconID;
    private String mTitle;
    private int mSubtitleID;
    private int mBackgroundID;
    private Fragment mFragment;

    /**
     * constructor
     * @param iconID
     * @param title
     * @param fragment
     */
    public DailyItem(int iconID, String title, Fragment fragment) {
        mIconID = iconID;
        mTitle = title;
        mBackgroundID = R.drawable.border_image;
        updateSubtitle();
        mFragment = fragment;
    }

    public int getIconID() {
        return mIconID;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getSubtitleID() {
        return mSubtitleID;
    }

    public int getBackgroundID() {
        return mBackgroundID;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setBackgroundID(int backgroundID) {
        mBackgroundID = backgroundID;
        updateSubtitle();
    }

    public void updateSubtitle(){
        switch (mBackgroundID){
            case R.drawable.border_image_low:
                mSubtitleID = R.string.subtitle_score_low;
                break;

            case R.drawable.border_image_high:
                mSubtitleID = R.string.subtitle_score_high;
                break;
            case R.drawable.border_image_balanced:
                mSubtitleID = R.string.subtitle_score_balanced;
                break;
            case R.drawable.border_image_unbalanced:
                mSubtitleID = R.string.subtitle_score_unbalanced;
                break;

            default:
                mSubtitleID = R.string.subtitle_score_no_data;
        }
    }
}
