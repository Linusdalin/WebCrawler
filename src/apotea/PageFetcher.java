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
public class PageFetcher {


    private String url;

    public PageFetcher(String url){

        this.url = url;
    }

    public String getPage(){

        try {

            HTTPRequestHandler requestHandler = new HTTPRequestHandler( url );
            return requestHandler.executeGet("");

        } catch (DeliveryException e) {

            e.printStackTrace();
            //throw new RuntimeException("Error getting page " + url );
            return null;

        }
    }
}
