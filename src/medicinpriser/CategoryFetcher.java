package medicinpriser;

import http.DeliveryException;
import http.HTTPRequestHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-14
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
public class CategoryFetcher {

    private static final int categoryLimit = 300;
    private static final String templateURL = "https://api.medicinpriser.se/articles/category/CATEGORY_ID?fields=id,productId,slug,substances,data(tradeName,strength,registrationForm),selectedPackage,availablePackagesLength,price,image&limit="+categoryLimit+"&offset=0&sort=priceDiffPercentage:DESC";

    public CategoryFetcher(){

    }

    public JSONArray getCategoryJSON(int categoryId){

        String fetchURL = templateURL
                .replaceAll("CATEGORY_ID", String.valueOf(categoryId));


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

    public List<Product> getProductRequests(Category category, JSONArray products) throws JSONException{

        List<Product> productList = new ArrayList<>();

        for(int i = 0; i < products.length(); i++){

            JSONObject entry = products.getJSONObject(i);

            System.out.println("Entry: " + entry);

            int id = entry.getInt("id");
            String name = entry.getString("slug");

            JSONObject selectedPackage = entry.getJSONObject("selectedPackage");
            String size = selectedPackage.getJSONObject("size").getString("text");
            String packageId = selectedPackage.getString("productNumber");

            productList.add( new Product(name, size, id, packageId, category));

        }

        return productList;
    }
}
