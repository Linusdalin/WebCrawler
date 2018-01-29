package sniffer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**********************************************
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public class GenericParser implements ParserInterface{


    private  String name;
    protected  ParserRuleInterface rule;
    public static final String ALL = "(*)";


    public GenericParser( ) {

    }

    protected void init(String name, ParserRuleInterface rule) {

        this.name = name;
        this.rule = rule;
    }


    @Override
    public void execute(String url, String content, String group, ParseResult result) {

        List<String> extractions = new ArrayList<>();
        System.out.println("Parsing");
        result.logParser(name);

        Document doc = Jsoup.parse(content);
        Elements allElements = doc.body().select("*");
        HTMLContext context = new HTMLContext(group, url);
        int stackSize = 0;
        int extractionCount = 0;
        int forEachStackStart = 0;
        boolean isInExtraction = false;
        ExtractionResult extraction = new ExtractionResult(rule, context);

        for (Element element : allElements) {

            System.out.println("Processing element: " + display( element, context ));
            context.checkPush(element);
            stackSize = context.getStack().size();     // Remember last to check if we are deeper or not

            if(isInExtraction){

                System.out.println("Stack level: " + stackSize + " context end at: " + forEachStackStart);

                if(stackSize <= forEachStackStart){

                  isInExtraction = false;
                  if(extraction.isComplete())
                      result.add(extraction);

                  extraction.addContextElements();

                  System.out.println("Element " + display(element, context) + " closed foreach ( Extractions: " + extractions.size() +")");

                }
            }


            if(!isInExtraction && rule.matchesExact(context)){

                System.out.println("Element " + truncate(100,element.toString()) + " opens new foreach (" + extractionCount +"). Stack:\n" + context.toString());
                extractionCount++;
                isInExtraction = true;
                extraction = new ExtractionResult(rule, context);
                forEachStackStart = stackSize;

            }


            if(rule.matches(context) && isInExtraction){

                //System.out.println("Rule "+ rule.getName() +" Matching context");
                boolean match = rule.execute(element, context, extraction);
                if(match)
                    System.out.println("Matched extraction " + extraction.getLIne());

            }
            else{
                //System.out.println("Rule " + rule.getName() + " not matching");
                //rule.setOutOfScope();
            }


        }

    }

    private String truncate(int i, String s) {
        if(s.length() < i)
            return s;
        return s.substring(0, i) + "...";
    }


    private String display(Element element, HTMLContext context){

        return adjustLeft(context.indent() + "<" + element.tagName() + ">", 30) +" id(" + element.attr("id") + ")" + " class(" + element.attr("class") + ")" + " text(" + truncate(50, element.text()) + ")";

    }
    public static String adjustLeft(String str, int leng) {
        for (int i = str.length(); i <= leng; i++)
            str += " ";
        return str;
    }
}
