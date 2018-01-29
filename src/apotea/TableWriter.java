package apotea;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import sniffer.ExtractionResult;
import sniffer.ParseResult;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-14
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public class TableWriter {


    private final String templateFile;

    public TableWriter(String templateFile){

        this.templateFile = templateFile;
    }


    public WritableWorkbook toExcel(String targetFile, ParseResult parseResult) {

        String templateLocation = "C:\\Users\\Linus\\Documents\\GitHub\\Pukka\\WebCrawler\\templates\\" + templateFile;

        Workbook template = null;
        WritableWorkbook workbook = null;
        File file = new File(templateLocation);


        try {
            template = Workbook.getWorkbook(file);
        } catch (IOException | BiffException e) {
            System.out.println("Could not open template file: " + templateLocation + "("+ e.getMessage()+")");
        }
        try {
            workbook = Workbook.createWorkbook(new File(targetFile), template);
        } catch (IOException e) {
            System.out.println("Could not open target file: " + targetFile + "(" + e.getMessage() + ")");
        }

        WritableSheet sheet = workbook.getSheet( 0 );
        sheet.setName("Produkter");

        Cell cell1 = sheet.getCell(0, 0);
        System.out.println(cell1.getContents());
        int row = 2;

        for (ExtractionResult extraction : parseResult.getExtractions()) {

            for (int i = 0; i < extraction.getExtractions().length; i++){

                try {

                    Label text = new Label( i ,row, extraction.getExtractions()[i]);
                    sheet.addCell(text);

                } catch (WriteException e) {

                    System.out.println("Error writing to file " + e.getMessage());

                }

            }
            row++;

        }

        return workbook;
    }
}
