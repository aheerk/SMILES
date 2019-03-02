package macewan_dust.smiles;


import android.support.v4.app.Fragment;

public class InformationItem {

    private String mTitle;
    private String mSubtitle;
    private Fragment mFragment;

    public InformationItem(String title, String subtitle, Fragment fragment) {
        mTitle = title;
        mSubtitle = subtitle;
        mFragment = fragment;
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
