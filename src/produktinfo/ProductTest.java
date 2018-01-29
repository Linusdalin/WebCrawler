package produktinfo;

import http.DeliveryException;
import http.HTTPRequestHandler;

import java.util.ArrayList;
import java.util.List;

/*************************************************************************************
 *
 *          Given a list of product codes ," check which exist on the site and which don't.
 *
 *
 */
public class ProductTest extends DataFile{

    private static String TemplateURL = "https://www.kronansapotek.se/p/PRODUCT_ID";
    private static List<String> fails = new ArrayList<>();


    private static final String[] productsTest = {

            "123456",
            "206015",         //Vichy

    };



    public static void main(String[] args){

        String[] items = products;    // Select data set to look for

        System.out.println("Checking "+ items.length+" products:");
        int count = 0;

        for (String productId : items) {

            try {

                System.out.println(" -- Testing product " + productId);

                String fetchURL = TemplateURL
                    .replaceAll("PRODUCT_ID", String.valueOf(productId));

                count++;
                HTTPRequestHandler requestHandler = new HTTPRequestHandler( fetchURL );
                String content = requestHandler.executeGet("");
                System.out.println(count + ": Product " + productId + " EXISTS!");

            } catch (DeliveryException e) {

                if(e.getErrorCode() == 404){

                    System.out.println(count + ": Product " + productId + " not found!");
                    fails.add(productId);
                }

            }

            try {

                Thread.sleep(300);                      // Short break

            } catch (InterruptedException e) {

                e.printStackTrace();
            }



        }

        System.out.println(" Checked " + count + " products. Found " + fails.size() + " missing: ");
        for (String fail : fails) {

            System.out.print(fail + ", ");
        }

        System.out.println("");

    }

}
