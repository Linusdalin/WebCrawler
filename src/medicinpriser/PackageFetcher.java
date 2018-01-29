package medicinpriser;

import http.DeliveryException;
import http.HTTPRequestHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-14
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public class PackageFetcher {

    private static final String templateURL = "https://api.medicinpriser.se/articles/PRODUCT_ID/packages";

    public PackageFetcher(){

    }

    public JSONArray getPackageJSON(int productId){

        JSONArray response;
        String fetchURL = null;


        try {

                fetchURL = templateURL
                        .replace("PRODUCT_ID", String.valueOf(productId));

                HTTPRequestHandler requestHandler = new HTTPRequestHandler( fetchURL );
                String content = requestHandler.executeGet(null);

                response = new JSONArray( content );
                System.out.println("Got " + response.length() + "packages");
                return response;




        } catch (DeliveryException e) {

            e.printStackTrace();
            throw new RuntimeException("Error getting page");

        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not parse JSONArray from " + fetchURL);
        }


    }



}
