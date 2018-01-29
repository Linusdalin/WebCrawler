package apotea;

import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import medicinpriser.*;
import sniffer.ParseResult;
import sniffer.ParserInterface;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;

/**************************************************************************'
 *
 *
 *          main scraper for Apotea
 *
 *          https://api.medicinpriser.se/articles/a-z/a?fields=id,slug,substances,data(tradeName,strength,registrationForm),selectedPackage,availablePackagesLength,price,image&limit=35&offset=0&sort=priceDiffPercentage:DESC
 *
 */

public class ApoteaScraper extends TestData {


    private static final Category[] categories = {


            new Category( 1, "allergi"),
            new Category( 2, "ansikte"),
            new Category( 3, "bett-stick"),
            new Category( 4, "djur"),
            new Category( 5, "feber-vark"),


            new Category( 6, "forkylning"),
            new Category( 7, "mamma-barn"),
            new Category( 8, "hjalpmedel-tillbehor"),

            new Category( 9, "hud-har"),
            new Category( 10, "hander-fotter"),
            new Category( 11, "intim"),
            new Category( 12, "kosttillskott"),
            new Category( 13, "mage-tarm"),
            new Category( 14, "mat-dryck"),
            new Category( 15, "mun-tander"),
            new Category( 16, "resef%C3%B6rpackningar"),
            new Category( 17, "sex-lust"),
            new Category( 18, "rokfri"),
            new Category( 19, "smink"),
            new Category( 20, "sar"),
            new Category( 21, "traning-vikt"),
            new Category( 22, "tv%C3%A4tt-och-st%C3%A4d"),
            new Category( 23, "vitaminer-mineraler"),
            new Category( 24, "%C3%B6gon-%C3%B6ron"),

    };
    private static final int PageMax = 100;         // How many pages per category
    private static final int categoryLimit = 2000;                      // How many products per category
    private static final String outputFile = "apotea.xls";       // Destination file
    private static final String templateFile = "apotea-template.xls";       // template file
    //private static final String templateFile = "FacebookAccountUpdateHandler.java";

    public ApoteaScraper(){

        //TODO: Move the parser setup to here

    }

    public void execute(){

        ParserInterface parser = new ApoteaParser();
        ParseResult result = new ParseResult();


        for (Category category : categories) {


            for(int pageNo = 1; pageNo <= PageMax; pageNo++){

                int total = result.getExtractions().size();
                System.out.println("Checking page " + pageNo + " for category " + category.getName() + ". " + total + " extractions in list.");
                CategoryFetcher fetcher = new CategoryFetcher();
                String page = fetcher.getCategoryPage(category.getName(), pageNo);
                //System.out.println("Got: " + page);
                System.out.println("Now parsing...");
                parser.execute("https://www.apotea.se/", page, category.getName(), result);
                System.out.println("Got result: " + result.toString());
                if(total == result.getExtractions().size()){

                    System.out.println("No extractions found for page " + pageNo);
                    break;
                }


                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }


        System.out.println("All done! Found " + result.getExtractions().size() + " extractions");

        System.out.println("Exporting to excel file " + outputFile + " (with template " + templateFile + ")");

        TableWriter tableWriter = new TableWriter( templateFile );

        WritableWorkbook excel = tableWriter.toExcel( outputFile, result );
        try {
            excel.write();
            excel.close();

        } catch (IOException | WriteException e) {
            e.printStackTrace();
            assertTrue(false);
        }



    }


    public static void main(String[] args){

       ApoteaScraper scraper = new ApoteaScraper();
       scraper.execute();

    }

}
