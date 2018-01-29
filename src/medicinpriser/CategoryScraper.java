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
 */

public class CategoryScraper {


    private static final String[] competitors = { "Kronans Apotek", "Apotea"};              // Brand Order

    private static final Category[] categories = {

            new Category( 1, "Allergi"),
            new Category( 2, "Barn"),
            new Category( 3, "Djurvård"),
            new Category( 4, "Värk/Feber"),
            new Category( 5, "Händer/Fötter"),
            new Category( 6, "Hud & Hår"),
            new Category( 7, "Intim/Sex"),
            new Category( 8, "Kostillskott"),
            new Category( 9, "Mage"),
            new Category(10, "Motion/Hälsa"),
            new Category(11, "Mun/Tänder"),
            new Category(12, "Rökfi"),
            new Category(13, "Sår/Bett/Stick"),
            new Category(14, "Sömn"),
            new Category(15, "Ögon/Öron"),



    };
    private static final int categoryLimit = 2000;                      // How many products per category
    private static final String outputFile = "medicinpriser.xls";       // Destination file

    public static void main(String[] args)throws JSONException {

        CategoryFetcher fetcher = new CategoryFetcher();
        TableWriter tableWriter = new TableWriter( competitors );

        System.out.println("Scraping medicinpriser:");

        for (Category category : categories) {

            System.out.println(" -- Getting Category: " + category.toString());

            JSONArray categoryResponse = fetcher.getCategoryJSON( category.getId());

            //System.out.println("Category: "+ category.toString()+" Got:\n" + categoryResponse);

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
