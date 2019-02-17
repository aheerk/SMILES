package macewan_dust.smiles;

/*
*  WebItem is used to store information in a single item
*  in the Web Links list.
* */
public class WebItem {

    // Icon for later?
    private String mTitle;
    private String mSubtitle;
    private String mUri;

    /**
     * WebItem Constructor
     * @param mTitle
     * @param mSubtitle
     * @param mUri
     */
    public WebItem(String mTitle, String mSubtitle, String mUri) {
        this.mTitle = mTitle;
        this.mSubtitle = mSubtitle;
        this.mUri = mUri;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public String getUri() {
        return mUri;
    }


}
