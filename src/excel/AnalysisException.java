package excel;

/***********************************************************
 *
 *
 *          General exception from the analysis
 *
 */
public class AnalysisException extends Exception {

    private String description;

    public AnalysisException(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void display() {

        System.out.println("Exception: " + getDescription());
        this.printStackTrace();
    }
}
