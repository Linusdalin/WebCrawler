package apotea;

import http.DeliveryException;
import http.HTTPRequestHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-14
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public class CategoryFetcher {

    private static final int categoryLimit = 300;

    private static final String templateURL = "https://www.apotea.se/CATEGORY_NAME/?p=PAGE_NO&ppp=150&sort=name%20asc";

    public CategoryFetcher(){

    }

    public String getCategoryPage(String category, int pageNo){

        String fetchURL = templateURL
                .replaceAll("CATEGORY_NAME", category)
                .replaceAll("PAGE_NO", String.valueOf(pageNo));

        try {

            HTTPRequestHandler requestHandler = new HTTPRequestHandler( fetchURL );
            return requestHandler.executeGet("");

        } catch (DeliveryException e) {

            e.printStackTrace();
            throw new RuntimeException("Error getting page " + fetchURL);

        }
    }

}
