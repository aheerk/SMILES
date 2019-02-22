package macewan_dust.smiles;

/*
*  WebItem is used to store information in a single item
*  in the Web Links list.
* */
public class WebItem {

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

    /**
     * Retrieves the link title of the webItem
     * @return String title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Retrieves the link subtitle of the webItem
     * @return String subtitle
     */
    public String getSubtitle() {
        return mSubtitle;
    }

    /**
     * Retrieves the URI of the item
     * @return String URI
     */
    public String getUri() {
        return mUri;
    }


}
