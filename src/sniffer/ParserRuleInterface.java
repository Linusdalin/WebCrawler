package sniffer;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public interface ParserRuleInterface {

    ParserRuleInterface extractStatic(String message);

    ParserRuleInterface inDiv();
    ParserRuleInterface inDiv(String idConstraint, String classConstraint);

    ParserRuleInterface inList();
    ParserRuleInterface inList(String idConstraint, String classConstraint);

    ParserRuleInterface inBody();
    ParserRuleInterface inHead();

    ParserRuleInterface forEach(TagScope tagScope);

    boolean matches(HTMLContext context);
    boolean matchesExact(HTMLContext context);

    ParserRuleInterface extractAttribute( TagScope tagScope, String attributeName, String regex );
    ParserRuleInterface extractText( TagScope tagScope, String regex );
    ParserRuleInterface extractNumber( TagScope tagScope, String regex );
    ParserRuleInterface extractAll( TagScope tagScope, String regex );

    String getName();

    ParserRuleInterface inSpan();
    ParserRuleInterface inSpan(String idConstraint, String classConstraint);

    boolean execute(Element element, HTMLContext context, ExtractionResult result);


    void setOutOfScope();

    ParserRuleInterface addSeparator(String separator);

    int getNumberOfExtractions();

    ParserRuleInterface extractCategory(  );

    List<Extraction> getExtractions();

    ParserRuleInterface extractFromPage(ParserRuleInterface pageRule, Extraction urlExtraction);
}
