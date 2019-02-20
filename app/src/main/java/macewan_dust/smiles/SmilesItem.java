package macewan_dust.smiles;

import android.support.v4.app.Fragment;

/**
 * holding information for the smiles recycler view
 */
public class SmilesItem {

    private int mIconID;
    private String mTitleCapitalLetter;
    private String mTitle;
    private int mTextID;

    //private Fragment mFragment;

    /**
     * holding information on exact details.
     * @param mIconID
     * @param mTitleCapitalLetter
     * @param mTitle
     */
    public SmilesItem(int mIconID, String mTitleCapitalLetter, String mTitle) {
        this.mIconID = mIconID;
        this.mTitleCapitalLetter = mTitleCapitalLetter;
        this.mTitle = mTitle;
    }

    public int getIconID() {
        return mIconID;
    }


    public String getTitleCapitalLetter() {
        return mTitleCapitalLetter;
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
