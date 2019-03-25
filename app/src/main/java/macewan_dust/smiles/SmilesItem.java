package macewan_dust.smiles;

import android.support.v4.app.Fragment;

/**
 * holding information for the smiles recycler view
 */
public class SmilesItem {

    private String mTitle;
    private int mTextID;

    //private Fragment mFragment;

    /**
     * holding information on exact details.
     * @param mTitle
     */
    public SmilesItem(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getTextID() {
        return mTextID;
    }

    public void setTextID(int textID) {
        mTextID = textID;
    }
}
