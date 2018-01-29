package sniffer;

import apotea.PageFetcher;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-23
 * Time: 10:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class GenericRule implements ParserRuleInterface{


    protected List<Extraction> extractions = new ArrayList();
    protected List<TagScope> scopeConstraint = new ArrayList<TagScope>();
    protected TagScope forEachElement = null;
    protected String name = "unknown";
    protected int scopeStart = 0;
    protected boolean firstElement = true;

    private static final String Separator = ";";

    @Override
    public ParserRuleInterface extractStatic(String text) {
        extractions.add(new Extraction(Extraction.Type.STATIC, null, text, null));
        return this;
    }

    @Override
    public ParserRuleInterface inDiv() {

        scopeConstraint.add(new TagScope("div", null, null));
        return this;
    }

    @Override
    public ParserRuleInterface inDiv(String idConstraint, String classConstraint) {
        scopeConstraint.add(new TagScope("div", idConstraint, classConstraint));
        return this;
    }

    @Override
    public ParserRuleInterface inSpan() {

        scopeConstraint.add(new TagScope("span", null, null));
        return this;
    }

    @Override
    public ParserRuleInterface inSpan(String idConstraint, String classConstraint) {
        scopeConstraint.add(new TagScope("span", idConstraint, classConstraint));
        return this;
    }


    @Override
    public ParserRuleInterface inList() {
        scopeConstraint.add(new TagScope("li", null, null));
        return this;
    }

    @Override
    public ParserRuleInterface inList(String idConstraint, String classConstraint) {
        scopeConstraint.add(new TagScope("li", idConstraint, classConstraint));
        return this;
    }

    @Override
    public ParserRuleInterface inBody() {
        scopeConstraint.add(new TagScope("body", null, null));
        return this;
    }

    @Override
    public ParserRuleInterface inHead() {
        scopeConstraint.add(new TagScope("head", null, null));
        return this;
    }

    @Override
    public ParserRuleInterface forEach(TagScope tagScope) {
        forEachElement = tagScope;
        return this;
    }


    @Override
    public String getName() {
        return name;
    }

    /***************************************************
     *
     *              Matching a rule against the current context
     *
     *              This is done by going through all the required tag scopes and
     *              try to find them (in order) in the context stack.
     *
     * @param context       - current context
     * @return              - true if the rule (all constraints) match the current context
     */


    @Override
    public boolean matches(HTMLContext context) {

        List<Element> stack = context.getStack();
        int cursor = stack.size() - 1;

        for (TagScope tagScope : scopeConstraint) {

            while(cursor >= 0 && !tagScope.matches(stack.get(cursor)))
                cursor--;

            if(cursor == -1)
                return false;
        }

        return true;
    }

    @Override
    public boolean matchesExact(HTMLContext context) {

        List<Element> stack = context.getStack();

        if(forEachElement == null || !forEachElement.matches(context.top()))
            return false;

        int cursor = stack.size() - 1;

        for (TagScope tagScope : scopeConstraint) {

            while(cursor >= 0 && !tagScope.matches(stack.get(cursor)))
                cursor--;

            if(cursor == -1)
                return false;
        }

        return true;
    }


    @Override
    public ParserRuleInterface extractText(TagScope tagScope, String regex) {

        extractions.add(new Extraction(Extraction.Type.TEXT, tagScope, null, regex));
        return this;
    }

    @Override
    public ParserRuleInterface extractNumber(TagScope tagScope, String regex) {

        extractions.add(new Extraction(Extraction.Type.NUMBER, tagScope, null, regex));
        return this;
    }

    @Override
    public ParserRuleInterface extractAll(TagScope tagScope, String regex) {

        extractions.add(new Extraction(Extraction.Type.ALL, tagScope, null, regex));
        return this;
    }


    @Override
    public ParserRuleInterface extractAttribute(TagScope tagScope, String attribute, String regex) {

        extractions.add(new Extraction(Extraction.Type.ATTRIBUTE, tagScope, attribute, regex));
        return this;
    }



    @Override
    public boolean execute(Element element, HTMLContext context, ExtractionResult result) {

        List<String> values = new ArrayList<>();
        int matches = 0;
        int extractionIndex = 0;

        for (Extraction extraction : extractions) {

            switch (extraction.getType()) {

                case TEXT:
                case NUMBER:
                case ALL:
                case STATIC:
                case ATTRIBUTE:
                case SEPARATOR:

                    if(extraction.getTag() == null || extraction.getTag().matches(element))   {

                        String value = extraction.getExtract(element);
                        result.add(value, extractionIndex);
                        System.out.println("Extraction " + extraction.getTag().toString() + " Matching pattern. Extracting value " + value);
                        matches++;

                    }
                    else
                        System.out.println(" -- Extraction "+ extraction.getTag().toString() +" NOT matching pattern");

                    break;
                case CATEGORY:
                    break;
                case SUB_PAGE:

                    if(extraction.getUrlExtraction().getTag().matches(element))   {

                        String url = extraction.getUrlExtraction().getExtract(element);
                        if(!url.startsWith("http"))
                            url = context.getDomain() + url;

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        url = url
                                .replaceAll("ä", "%C3%A4")
                                .replaceAll("ö", "%C3%B6")
                                .replaceAll("å", "%C3%A5");

                        System.out.println("Got URL: " + url + " for sub page");
                        PageFetcher pageFetcher = new PageFetcher(url);
                        String subPage = pageFetcher.getPage();

                        if(subPage == null){

                            result.add("Not found " + url, extractionIndex);

                        }
                        else{
                            ParseResult subResult = new ParseResult();

                            System.out.println("Got result: " + result.toString());

                            GenericParser newParser = new GenericParser();
                            newParser.init("sub page", extraction.getSubRule());
                            newParser.execute("https://www.apotea.se/", subPage, "", subResult);
                            System.out.println("Got result: " + subResult.toString());

                            //TODO: Decide what to get. Here the first value in the first extraction
                            String subRes = subResult.getExtractions().get(0).getExtraction( 0 );

                            result.add(subRes, extractionIndex);

                        }

                    }

                    break;

                default:

            }


            extractionIndex++;
        }

        return matches > 0;

    }

    private boolean inScope(HTMLContext context) {
        return  context.getStack().size() > scopeStart;
    }


    @Override
    public void setOutOfScope() {
        scopeStart = 0;
        firstElement = true;
    }

    @Override
    public ParserRuleInterface addSeparator(String separator) {

        extractions.add(new Extraction(Extraction.Type.SEPARATOR, separator));
        return this;
    }

    @Override
    public int getNumberOfExtractions() {
        return extractions.size();
    }

    @Override
    public ParserRuleInterface extractCategory(  ) {

        extractions.add(new Extraction(Extraction.Type.CATEGORY, "category"));
        return this;
    }

    @Override
    public List<Extraction> getExtractions() {
        return extractions;
    }

    @Override
    public ParserRuleInterface extractFromPage(ParserRuleInterface pageRule, Extraction urlExtraction) {
        extractions.add(new Extraction(Extraction.Type.SUB_PAGE, pageRule, urlExtraction));
        return this;
    }

}
