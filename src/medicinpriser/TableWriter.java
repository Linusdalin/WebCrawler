package medicinpriser;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-14
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public class TableWriter {

    private static final int MAX_PRODUCTS = 30000;
    private static final int MAX_SITES = 50;

    List<String> sites = new ArrayList<>();
    List<Product> products = new ArrayList<>();

    PriceEntry entries[][] = new PriceEntry[MAX_SITES][MAX_PRODUCTS];

    public TableWriter(){


    }

    /*******************************************************************
     *
     *          Create with a prepopulated competitors and own brand
     *
     *
     * @param siteNames
     */

    public TableWriter(String[] siteNames) {

        for (String site : siteNames) {

            sites.add(site);
        }


    }


    public void addProductLine(Product product, JSONArray prices) throws JSONException {

        if(products.size() == MAX_PRODUCTS){

            System.out.println(" Max number of products () reached. Not adding any more");
            return;
        }

        products.add(product);
        int productIndex = products.size() - 1;

        for(int i = 0; i < prices.length();i++){

            JSONObject entry = prices.getJSONObject(i);
            System.out.println(i + ": " + entry );

            JSONObject shopItem = entry.getJSONObject("ShopItem");
            double price = entry.getDouble("price");
            JSONObject shop = shopItem.getJSONObject("Shop");

            int siteIndex = getIndexOf(shop);
            entries[siteIndex][productIndex] = new PriceEntry(price);

        }




    }

    private int getIndexOf(JSONObject entry) throws JSONException{

        int count = 0;
        String siteName =  entry.getString("name");
        for (String site : sites) {

            if(site.equals(siteName))
                return count;

            count++;
        }

        System.out.println(" - Found new Site. Adding as index " + count);
        sites.add(siteName);
        return count;

    }

    public String toString(){

        StringBuffer output = new StringBuffer();

        output.append("Product: ,Size:, ");

        for (String site : sites) {
            output.append(site + ", ");
        }
        output.append("\n");

        int p = 0;
        for (Product product : products) {

            output.append(product.getName() + ", " + product.getSize());

            for(int s = 0; s < sites.size(); s++){

                output.append((entries[s][p] != null ? entries[s][p].getPrice() : "") + ", ");

            }
            output.append("\n");
            p++;
        }




        return output.toString();

    }


    public WritableWorkbook toExcel(String targetFile) {

        String templateLocation = "C:\\Users\\Linus\\Documents\\GitHub\\Kronan\\template.xls";

        Workbook template = null;
        WritableWorkbook workbook = null;
        File file = new File(templateLocation);


        try {
            template = Workbook.getWorkbook(file);
        } catch (IOException | BiffException e) {
            System.out.println("Could not open template file: " + templateLocation + "("+ e.getMessage()+")");
        }
        try {
            workbook = Workbook.createWorkbook(new File(targetFile), template);
        } catch (IOException e) {
            System.out.println("Could not open target file: " + targetFile + "(" + e.getMessage() + ")");
        }

        WritableSheet sheet = workbook.getSheet( 0 );
        sheet.setName("Priser");

        Cell cell1 = sheet.getCell(0, 0);
        System.out.println(cell1.getContents());




        try {

            int p = 0;
            int headerRows = 2;
            int headerColumns = 4;

            int col = headerColumns;

            for (String site : sites) {

                Label text = new Label( col ,1, site);
                sheet.addCell(text);

                Label text2 = new Label( col+7 ,1, site);
                sheet.addCell(text2);

                col++;
            }


            for (Product product : products) {

                int row = p + headerRows;

                Label text = new Label(0,row, product.getName());
                sheet.addCell(text);

                text = new Label(1,row, product.getSize());
                sheet.addCell(text);

                text = new Label(2,row, product.getPackageId());
                sheet.addCell(text);


                text = new Label(3,row, product.getCategory().getName());
                sheet.addCell(text);

                for(int s = 0; s < sites.size(); s++){

                    if( entries[s][p] != null){

                        Number number = new Number(s+headerColumns, row, entries[s][p].getPrice() );
                        sheet.addCell(number);
                    }

                }
                p++;
            }


        } catch (WriteException e) {

            System.out.println("Error writing to file " + e.getMessage());
        }

        return workbook;
    }
}
