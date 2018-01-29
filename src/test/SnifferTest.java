package test;

import org.junit.Test;
import sniffer.ExtractionResult;
import sniffer.ParseResult;
import sniffer.ParserInterface;
import sniffer.Sniffer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class SnifferTest {


    @Test
    public void basicTest(){

        ParserInterface testParser = new TestParser();
        Sniffer sniffer = new Sniffer()
                .withURL("http://www.kristinabergman.se")
                .withParser(testParser);
        ParseResult result = sniffer.execute();

        System.out.println("Gor Result: " + result.toString());

    }

    @Test
    public void apoteaTest(){

        ParserInterface testParser = new TestParser();
        Sniffer sniffer = new Sniffer()
                .withURL("http://www.apotea.se/kampanjvaror")
                .withParser(testParser);
        ParseResult result = sniffer.execute();

        System.out.println("Gor Result: " + result.toString());

    }



    @Test
    public void kronanTest(){

        ParserInterface parser = new KronanSearchParser();
        Sniffer sniffer = new Sniffer()
                .withURL("https://www.kronansapotek.se/search/?text=ACO+Sun+Body+Spray+Active+SPF+20+125+ml")
                .withParser(parser);
        ParseResult result = sniffer.execute();

        System.out.println("Gor Result: " + result.toString());

    }

    @Test
    public void kronanTest2(){

        ParserInterface parser = new KronanSearchParser();
        Sniffer sniffer = new Sniffer()
                .withURL("https://www.kronansapotek.se/search/?text=Anthon+Berg+Dark+Milk+Fairtrade+100+g")
                .withParser(parser);
        ParseResult result = sniffer.execute();

        System.out.println("Got Result: " + result.toString());

    }

    @Test
    public void comboTest(){

        int max = 500;

        ParserInterface testParser = new TestParser();
        Sniffer sniffer = new Sniffer()
                .withURL("http://www.apotea.se/kampanjvaror")
                .withParser(testParser);
        ParseResult result = sniffer.execute();

        System.out.println("Got Result: " + result.toString());
        ParserInterface parser = new KronanSearchParser();

        //System.out.println("Got Extractions: " + result.getExtractions().size());

        int count = 0;

        try {

            PrintWriter writer = new PrintWriter("output.csv", "UTF-8");

            for (ExtractionResult extractionResult : result.getExtractions()) {

                for (String value : extractionResult.getExtractions()) {

                    if(count++ < max)
                        writer.println(appendKronan(value, parser));

                }


            }

            writer.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    private String appendKronan(String extraction, ParserInterface parser) {

        String product = extraction.substring(0, extraction.indexOf(",")).replaceAll(" ", "-");
        String url = "https://www.kronansapotek.se/search/?text="+product;


        Sniffer sniffer = new Sniffer()
                .withURL(url)
                .withParser(parser);

        System.out.println("Sniffing Kronan with URL: " + url);

        ParseResult result = sniffer.execute();
        String appendix;

        if(result.getExtractions().size() == 0)
            appendix = "Not Found, ??";
        else
            appendix = result.getExtractions().get(0).getExtractions()[0];

        return extraction + appendix;

    }


}
