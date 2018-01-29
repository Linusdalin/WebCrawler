package test;

import sniffer.*;

/*******************************************************************
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */


public class KronanSearchParser extends GenericParser implements ParserInterface {

    private static final ParserRuleInterface[] Rules = new ParserRuleInterface[] {

            new ListExtractionRule("Search Result")

                    .extractText(new TagScope("p", null, "price"), "([\\d[. ]]*\\d+([:,]\\d\\d)*)(:-)*")
                    .extractAttribute(new TagScope("a", null, null), "title", null)
                    .extractText(new TagScope("h3", null, "productBrand"), null)
                    .inList(null, "productListItem"),



            new ListExtractionRule("Not found")
                    .extractStatic("not found")
                    .inDiv(null, "searchSpellingSuggestionPrompt")

    };


    public KronanSearchParser(){

        //init(this.getClass().getSimpleName(), Rules);
    }
}
