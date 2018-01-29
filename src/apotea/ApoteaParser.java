package apotea;


import excel.AnalysisException;
import excel.FileHandler;
import sniffer.*;

import java.util.List;

/***********************************************************************************'
 *
 *      Parser to extract product and price information from the Apotea site
 *
 *
 */
public class ApoteaParser extends GenericParser implements ParserInterface {

    private static final ParserRuleInterface EANRule =  new ListExtractionRule("Product")
            .extractText(new TagScope("div", null, "product-facts-content-right"), null)
            .forEach(new TagScope("div", null, "product-facts-content-right"));

    private static final ParserRuleInterface ApoteaRule =  new ListExtractionRule("Product")

            //.extractAttribute(new TagScope("span", null, "btn-product-action"), "data-name", null)
            .extractAttribute(new TagScope("span", null, "btn-product-action"), "data-sku", null)
            .extractText(new TagScope("div", null, "name"), null)
            .extractCategory()
            .extractText(new TagScope("div", null, "price-tag"), null)
            .extractText(new TagScope("div", null, "list-price"), null)
            .extractFromPage(EANRule, new Extraction(Extraction.Type.ATTRIBUTE, new TagScope("div", null, "price-tag"), "onClick", "document.location='(.*)'"))
            //.extractAttribute(new TagScope("div", null, "price-tag"), "onClick", "document.location='(.*)'")
            .forEach(new TagScope("li", null, ""))


    ;

    public ApoteaParser(){

        init(this.getClass().getSimpleName(), ApoteaRule);

    }




    public static void main(String[] args){

        try {

            ParseResult result = new ParseResult();
            FileHandler fileHandler = new FileHandler();
            String testPage = fileHandler.loadFile("examples/apoteaCategoryPage.html");

            ApoteaParser parser = new ApoteaParser();
            parser.execute("local", testPage, "category", result);

            List<ExtractionResult> extractions = result.getExtractions();
            System.out.println("Got "+ extractions.size()+" extractions");


            for (ExtractionResult extraction : extractions) {

                System.out.println("Extraction: " + extraction.toString());

            }


        } catch (AnalysisException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
