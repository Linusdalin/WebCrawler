package test;

import sniffer.*;

/*******************************************************************
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */


public class TestParser extends GenericParser implements ParserInterface {


    private static final ParserRuleInterface Rule =

            new ListExtractionRule("Test Price & Product")



                    .extractNumber(new TagScope("div", null, "store-price"), "[^\\d]*([\\d[. ]]*\\d+[[:,]\\d\\d]*)[^\\d]*" )
                    .extractAttribute(new TagScope("img", null, "").withAttribute("src", "/product-images/"), "alt", null)
                    .extractNumber(new TagScope("span", null, null), "([\\d[. ]]*\\d+[[:,]\\d\\d]*) .*")
                    .inList(null, "")
                    .inDiv("promotion-articles", null)
                    .inBody()


    ;


    public TestParser(){

        init(this.getClass().getSimpleName(), Rule);
    }
}
