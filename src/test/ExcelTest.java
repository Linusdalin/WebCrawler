package test;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-22
 * Time: 06:26
 * To change this template use File | Settings | File Templates.
 */
public class ExcelTest {

    @Test
    public void loadTest() {

        try {

            File file = new File("C:\\Users\\Linus\\Documents\\GitHub\\Kronan\\testFile.xls");
            Workbook template = Workbook.getWorkbook( file );
            WritableWorkbook workbook = Workbook.createWorkbook(new File("output.xls"), template);

            WritableSheet sheet = workbook.getSheet( 0 );

            Cell cell1 = sheet.getCell(0, 0);
            System.out.println(cell1.getContents());




            Label text = new Label(0,1, "Added text");
            sheet.addCell(text);

            workbook.write();
            workbook.close();
            template.close();


        } catch (BiffException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (WriteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
