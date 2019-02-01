package macewan_dust.smiles;

public class DailyItem {

    private int mIconID;
    private String mTitle;
    private String mSubtitle;
    private int mBackgroundID;



    /**
     * constructor
     * @param iconID
     * @param title
     * @param subtitle
     */
    public DailyItem(int iconID, int backgroundID, String title, String subtitle) {
        mIconID = iconID;
        mTitle = title;
        mSubtitle = subtitle;
        mBackgroundID = backgroundID;
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

    public int getBackgroundID() {
        return mBackgroundID;
    }
}
