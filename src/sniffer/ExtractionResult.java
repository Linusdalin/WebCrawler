package sniffer;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class ExtractionResult {


    private String[] extractions;
    private static final String separator = ", ";
    private final HTMLContext context;
    private final ParserRuleInterface rule;

    public ExtractionResult(ParserRuleInterface rule, HTMLContext context){

        this.rule = rule;
        int size = rule.getNumberOfExtractions();

        extractions = new String[size];
        for(int i = 0; i < size; i++)
            extractions[i] = null;

        this.context = context;

    }

    public String toString(){

        StringBuffer s = new StringBuffer();

        s.append("Got the following  extractions:\n");

        for (String extraction : extractions) {

           s.append(extraction + separator);
        }

        s.append("\n");
        return s.toString();
    }

    public String getLIne(){

        StringBuffer s = new StringBuffer();
        for (String extraction : extractions) {

            s.append(extraction + " ");
        }

        s.append("\n");
        return s.toString();
    }

    public void add(String extraction, int extractionIndex){

        extractions[extractionIndex] = extraction;
    }

    public String[] getExtractions(){
        return extractions;
    }


    private String trim(String text) {
        return text.replaceAll(",", "");
    }

    public boolean isComplete() {
        for(int i = 0; i < extractions.length; i++)
            if(extractions[i] != null)
                return true;

        return false;       // No extractions found
    }

    public void addContextElements( ) {

        for(int i = 0; i < extractions.length; i++)
            if(rule.getExtractions().get( i ).getType() == Extraction.Type.CATEGORY )
                extractions[i] = context.getGroup();

    }

    public String getExtraction( int i){

        return extractions[ i ];
    }

}
