package oddsSniffer;

import org.junit.Test;
import sniffer.ParseResult;
import sniffer.ParserInterface;
import test.ExpektParser;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-05-14
 * Time: 22:25
 * To change this template use File | Settings | File Templates.
 */
public class ParseTestr extends ExampleFile{

    @Test
    public void expektParseTest(){

        ParserInterface parser = new ExpektParser();

        ParseResult result = new ParseResult();
        parser.execute("noUrl", expekt, "", result);

        System.out.println("Got result: " + result.toString());


    }

}
