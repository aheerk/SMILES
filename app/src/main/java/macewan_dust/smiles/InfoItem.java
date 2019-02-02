package macewan_dust.smiles;


import android.support.v4.app.Fragment;

public class InfoItem {

    private int mIconID;
    private String mTitle;
    private String mSubtitle;
    private Fragment mFragment;

    public InfoItem(int iconID, String title, String subtitle, Fragment fragment) {
        mIconID = iconID;
        mTitle = title;
        mSubtitle = subtitle;
        mFragment = fragment;
    }

    public int getIconID() {
        return mIconID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public Fragment getFragment() {
        return mFragment;
    }
}
