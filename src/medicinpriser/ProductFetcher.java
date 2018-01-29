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
public class ProductFetcher {

    private static final String templateURL ="https://api.medicinpriser.se/articles/PRODUCT_ID/PACKAGE_ID/prices?fields=id,ArticleId,productNumber,price,retailPrice,priceDirection,lastChecked,retailLastChecked,ShopItem%2F(id,url,data%2FstockLevel,Shop%2F(id,name,url,slug,data%2F(store,shopItemDefaultUrl,registered)))";

    public ProductFetcher(){

    }

    public JSONArray getProductJSON(Product product){

        String fetchURL = templateURL
                .replaceAll("PACKAGE_ID", product.getPackageId())
                .replaceAll("PRODUCT_ID", String.valueOf(product.getProductId()));

        try {

            HTTPRequestHandler requestHandler = new HTTPRequestHandler( fetchURL );
            String content = requestHandler.executeGet("");

            //System.out.println("Got content size: " + content.length());
            //System.out.println("Got content:\n\n" + content);

            return new JSONArray( content );

        } catch (DeliveryException e) {

            e.printStackTrace();
            throw new RuntimeException("Error getting page");

        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not parse JSONArray from " + fetchURL);
        }


    }

}
