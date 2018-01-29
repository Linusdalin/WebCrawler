package sniffer;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class HtmlTraverser {

    private final String html;
    private int cursor;
    private HTMLContext context;

    public HtmlTraverser(String html){

        this.html = html;
        this.cursor = 0;
    }


    public boolean next(){

        return false;

    }

    public HTMLContext getContext(){

        return context;
    }


}
