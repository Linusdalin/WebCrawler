package sniffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class ParseResult {


    private List<String> parsers = new ArrayList<String>();
    private List<ExtractionResult> extractions = new ArrayList<>();
    private static final String separator = ", ";

    public void logParser(String name) {

        this.parsers.add(name);
    }

    public String toString(){

        StringBuffer s = new StringBuffer();
        s.append("Used the following parsers: ");

        for (String parser : parsers) {

            s.append(parser + ", ");
        }

        s.append("\n");

        s.append("Got the following "+ extractions.size()+" extractions:\n");

        for (ExtractionResult extraction : extractions) {

           s.append(extraction.getLIne());
        }

        s.append("\n");
        return s.toString();
    }

    public String getLIne(){

        StringBuffer s = new StringBuffer();
        for (ExtractionResult extraction : extractions) {

            s.append(extraction.getLIne());
        }

        s.append("\n");
        return s.toString();
    }




    private String trim(String text) {
        return text.replaceAll(",", "");
    }

    public List<ExtractionResult> getExtractions() {
        return extractions;
    }

    public void add(ExtractionResult extraction ){
        this.extractions.add(extraction);
    }

}
