package sniffer;

import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-26
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public class Extraction {


    public ParserRuleInterface getSubRule() {
        return subRule;
    }

    public enum Type {
        TEXT, NUMBER, ALL, STATIC, ATTRIBUTE,
        SEPARATOR,
        CATEGORY,
        SUB_PAGE

    }

    private final TagScope tag;
    private final String parameter;
    private final String regex;
    private final Type type;
    private final Extraction urlExtraction;
    private final ParserRuleInterface subRule;

    public Extraction(Type type, TagScope tag, String parameter, String regex){

        this.type = type;
        this.tag = tag;
        this.parameter = parameter;
        this.regex = regex;
        this.subRule = null;
        this.urlExtraction = null;
    }

    public Extraction(Type type, String text){

        this.type = type;
        this.tag = null;
        this.parameter = text;
        this.regex = null;
        this.subRule = null;
        this.urlExtraction = null;
    }

    public Extraction(Type type, ParserRuleInterface subRule, Extraction urlExtraction) {

        this.type = type;
        this.tag = null;
        this.parameter = null;
        this.regex = null;
        this.subRule = subRule;
        this.urlExtraction = urlExtraction;
    }


    public String getExtract(Element element) {

        switch (type) {

            case ATTRIBUTE:
                return regMatch(element.attr(parameter), regex);
            case STATIC:
                return parameter;
            case TEXT:
                return regMatch(element.text(), regex);
            case NUMBER:
                return cleanNumber(regMatch(element.text(), regex));
            case ALL:
                return regMatch(element.toString(), regex);
            case SEPARATOR:
                return parameter;

            case SUB_PAGE:
                return "Not Implemented Yet";

        }

        return "unknown";
    }

    private String cleanNumber(String s) {
        return s.replaceAll("\\.", " ");
    }

    private String regMatch(String text, String patternString) {

        if(regex == null)
            return text;
        Pattern pattern = Pattern.compile( patternString );
        Matcher matcher = pattern.matcher(text);

        boolean matches = matcher.matches();

        if(matches){
            return matcher.group(1);
        }
        else
            return "no match: " + text;
    }

    public TagScope getTag() {
        return tag;
    }

    public Type getType() {
        return type;
    }

    public Extraction getUrlExtraction() {
        return urlExtraction;
    }

}
