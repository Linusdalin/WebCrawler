package excel;

import org.apache.commons.io.IOUtils;
import java.io.*;



/**************************************************************************
 *
 *          Common file functionality
 *
 */

public class FileHandler {


    /***********************************************************
     *
     *              Get file
     *
     *
     * @param fileName    - full file with path
     * @return            - inputStream
     */

    public InputStream getFile(String fileName) throws AnalysisException{

        try {

            InputStream is = new FileInputStream(fileName);
            return is;

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            throw new AnalysisException("Could not load file " + fileName + "(" + e.getMessage() +")");
        }

    }

    public boolean storeFile(ExcelDocument document){

        return document.save(document.getName() + ".docx");

    }

    /*********************************************************'
     *
     *          Store framework
     *
     *
     * @param framework        - a framework file
     * @return                 - success or not
     */


    /***********************************************************
     *
     * @param fileName         - the file name
     * @param content          - the file content
     */

    public void storeFile(String fileName, String content) throws AnalysisException{

        if(fileName == null)
            throw new AnalysisException("Could not store. No filename given");

        try{

            PrintWriter out = new PrintWriter(fileName);
            out.write(content);
            out.close();

        }catch(IOException e){
            throw new AnalysisException("Could not store." + e.getMessage());

        }

    }

    public String loadFile(String fileName) throws AnalysisException{

        try {

            InputStream is = getFile(fileName);
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer);
            return writer.toString();

        } catch (IOException e) {

            throw new AnalysisException("Could not read file");
        }

    }


    public static void main(String[] args){

        String file = "C:\\Users\\Linus\\Documents\\GitHub\\Kronan\\" + "FacebookAccountUpdateHandler.java";

        try {

            FileHandler fh = new FileHandler();
            String content = fh.loadFile( file );
            System.out.println("Content:" + content);

        } catch (AnalysisException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
