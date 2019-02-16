package macewan_dust.smiles;

import android.support.v4.app.Fragment;

import java.net.URL;

/*
*  WebItem is used to store information in a single item
*  in the Web Links list.
* */
public class WebItem {

    // Icon for later?
    private String mTitle;
    private String mSubtitle;
    private URL mUrl;

    /**
     * WebItem Constructor
     * @param mTitle
     * @param mSubtitle
     * @param mUrl
     */
    public WebItem(String mTitle, String mSubtitle, URL mUrl) {
        this.mTitle = mTitle;
        this.mSubtitle = mSubtitle;
        this.mUrl = mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public URL getUrl() {
        return mUrl;
    }


}
