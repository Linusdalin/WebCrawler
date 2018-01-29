package sniffer;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public interface ParserInterface {

    void execute(String url, String content, String group, ParseResult result);
}
