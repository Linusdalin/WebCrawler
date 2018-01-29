package excel;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-22
 * Time: 05:58
 * To change this template use File | Settings | File Templates.
 */
public class ExcelDocument {

    private String name;
    private SpreadsheetMLPackage document = null;

    public ExcelDocument(String name, InputStream is) {

        this.name = name;

        try {

            document= (SpreadsheetMLPackage)SpreadsheetMLPackage.load(is);     // doc4j


        } catch (Docx4JException e) {

            e.printStackTrace();
        }

    }

    public boolean save(String target){

        try{

            File f = new File(target);
            document.save(f);
            return true;

        }catch(Docx4JException e){
            System.out.println("Could not save document! " + e.getMessage());
            return false;
        }

    }


    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SpreadsheetMLPackage getDoc4jRepresentation() {
        return document;
    }

}