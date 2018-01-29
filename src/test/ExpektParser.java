package test;

import sniffer.*;

/*******************************************************************
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */


public class ExpektParser extends GenericParser implements ParserInterface {

    private static final ParserRuleInterface Rule =
            new ListExtractionRule("Odds")

                    .extractText(new TagScope("span", null, "oddLabel"), null)
                    .extractText(new TagScope("span", null, "oddValue"), null)

    ;


    public ExpektParser(){

        init(this.getClass().getSimpleName(), Rule);
    }
}
