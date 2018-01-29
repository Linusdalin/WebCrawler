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
public class ArticlesFetcher {

    private static final int categoryLimit = 400;
    private static final String templateURL = "https://api.medicinpriser.se/articles/a-z/CATEGORY_ID?fields=id,productId,slug,substances,data(tradeName,strength,registrationForm),selectedPackage,availablePackagesLength,price,image&limit="+categoryLimit+"&offset=_OFFSET&sort=priceDiffPercentage:DESC";

    public ArticlesFetcher(){

    }

    public JSONArray getCategoryJSON(Category category){

        JSONArray result = new JSONArray();
        JSONArray response;
        int page = 0;
        String fetchURL = null;


        try {

            do{

                fetchURL = templateURL
                        .replace("CATEGORY_ID", category.getName())
                        .replace("_OFFSET", String.valueOf(page*categoryLimit));

                HTTPRequestHandler requestHandler = new HTTPRequestHandler( fetchURL );
                String content = requestHandler.executeGet("");

                response = new JSONArray( content );
                System.out.println("Got " + response.length() + "entries");
                append(response, result);
                page++;


            }while( response.length() == categoryLimit);




        } catch (DeliveryException e) {

            e.printStackTrace();
            throw new RuntimeException("Error getting page");

        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not parse JSONArray from " + fetchURL);
        }

        return result;

    }

    private void append(JSONArray response, JSONArray result) throws JSONException {

        for(int i = 0; i < response.length(); i++)
            result.put(response.getJSONObject( i ));


    }

    public List<Product> getProductRequests(Category category, JSONArray products) throws JSONException{

        List<Product> productList = new ArrayList<>();

        for(int i = 0; i < products.length(); i++){

            // Step1: Get all packages from the product

            JSONObject entry = products.getJSONObject(i);

            System.out.println("Entry: " + entry);

            int id = entry.getInt("id");
            String name = entry.getString("slug");

            PackageFetcher packageFetcher = new PackageFetcher();
            JSONArray packageList = packageFetcher.getPackageJSON( id );

            for(int p = 0; p < packageList.length(); p++){

                JSONObject packageJSON = packageList.getJSONObject( p );
                String size = packageJSON.getJSONObject("size").getString("text");
                String packageId = packageJSON.getString("productNumber");
                productList.add( new Product(name, size, id, packageId, category));

                System.out.println("-- Added package: " + packageId);

            }


            /*
            JSONObject selectedPackage = entry.getJSONObject("selectedPackage");
            String size = selectedPackage.getJSONObject("size").getString("text");
            String packageId = selectedPackage.getString("productNumber");
            productList.add( new Product(name, size, id, packageId, category));
              */
        }

        return productList;
    }

    public List<Product> getProductRequestsOld(Category category, JSONArray products) throws JSONException{

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
