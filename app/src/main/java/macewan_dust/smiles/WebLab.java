package macewan_dust.smiles;

import java.util.LinkedList;
import android.content.Context;

// Referenced https://www.geeksforgeeks.org/singleton-class-java/

/**
 * This singleton holds a list of webItems for reference
 * throughout the app
 *
 * SAMPLE USAGE: mWebItems = WebLab.getWebList(this.getContext());
 */
public class WebLab {

    private LinkedList<WebItem> mWebItems;
    private static WebLab instance = null;

    /**
     * WebLab constructor is private as it is a singleton
     * @param context
     */
    private WebLab(Context context) {

        mWebItems = new LinkedList<>();
        String[] weblinks = context.getResources().getStringArray(R.array.weblinks_array);

        // Loop through the web links and add a new web item to the
        // linked list
        for (int i = 0; i < weblinks.length; i = i + 3) {
            String title = weblinks[i];
            String subtitle = weblinks[i + 1];
            String uri = weblinks[i + 2];

            mWebItems.add(new WebItem(title, subtitle, uri));
        }
    }

    /**
     * This method allows for the retrieval of the list of WebItems
     * @param context
     * @return Populated linked List of web items
     */
    public static LinkedList<WebItem> getWebList(Context context) {
        if (instance == null)
            instance = new WebLab(context);
        return instance.retrieveList();
    }

    /**
     * This method returns a copy of the linked list of web items
     * @return A Linked List of web items
     */
    private LinkedList<WebItem> retrieveList() {
        return new LinkedList<WebItem>(mWebItems);
    }
}
