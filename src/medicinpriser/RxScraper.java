package medicinpriser;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**************************************************************************'
 *
 *
 *          main scraper for Medicinpriser
 *
 *          https://api.medicinpriser.se/articles/a-z/a?fields=id,slug,substances,data(tradeName,strength,registrationForm),selectedPackage,availablePackagesLength,price,image&limit=35&offset=0&sort=priceDiffPercentage:DESC
 *
 */

public class RxScraper {


    private static final String[] competitors = { "Kronans Apotek", "Apotea"};              // Brand Order

    private static final Category[] categories = {


            new Category( 1, "a"),
            new Category( 2, "b"),
            new Category( 3, "c"),
            new Category( 4, "d"),
            new Category( 5, "e"),
            new Category( 6, "f"),
            new Category( 7, "g"),
            new Category( 8, "h"),
            new Category( 9, "i"),
            new Category( 10, "j"),
            new Category( 11, "k"),
            new Category( 12, "l"),
            new Category( 13, "m"),
            new Category( 14, "n"),
            new Category( 15, "o"),
            new Category( 16, "p"),
            new Category( 17, "q"),
            new Category( 18, "r"),
            new Category( 19, "s"),
            new Category( 20, "t"),
            new Category( 21, "u"),
            new Category( 22, "v"),
            new Category( 23, "w"),
            new Category( 24, "x"),
            new Category( 25, "y"),
            new Category( 26, "z"),

    };
    private static final int categoryLimit = 2000;                      // How many products per category
    private static final String outputFile = "medicinpriser.xls";       // Destination file

    public static void main(String[] args)throws JSONException {

        ArticlesFetcher fetcher = new ArticlesFetcher();
        TableWriter tableWriter = new TableWriter( competitors );

        System.out.println("Scraping medicinpriser by articles:");

        for (Category category : categories) {

            System.out.println(" -- Getting Articles: " + category.toString());

            JSONArray categoryResponse = fetcher.getCategoryJSON( category );

            System.out.println("Category: "+ category.toString()+" Got:\n" + categoryResponse.toString(2));

            List<Product> productList = fetcher.getProductRequests(category, categoryResponse);

            System.out.print("     --- Got: " + productList.size() + " products");
            if(categoryLimit > -1 && categoryLimit < productList.size())
                System.out.println(" but limiting to " + categoryLimit + " products");
            else
                System.out.println("");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            ProductFetcher productFetcher = new ProductFetcher();

            int limit = categoryLimit;
            for (Product product : productList) {

                JSONArray prices = productFetcher.getProductJSON(product);
                tableWriter.addProductLine( product , prices);

                if(--limit == 0)
                    break;

            }


        }



        System.out.println("Exporting to excel file " + outputFile);

        WritableWorkbook excel = tableWriter.toExcel( outputFile );
        try {
            excel.write();
            excel.close();

        } catch (IOException | WriteException e) {
            e.printStackTrace();
            assertTrue(false);
        }


    }

}
