package sniffer;

import http.DeliveryException;
import http.HTTPRequestHandler;

import java.util.ArrayList;
import java.util.List;

/******************************************************************'
 *
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */


public class Sniffer {


    private String url = null;
    private List<ParserInterface> parsers = new ArrayList<ParserInterface>();

    public Sniffer(){

    }


    public Sniffer withURL( String url ){

        this.url = url;
        return this;
    }

    public Sniffer withParser( ParserInterface parser ){

        parsers.add(parser);
        return this;
    }


    public ParseResult execute(){

        ParseResult result = new ParseResult();
        String content;

        if( url == null )
            throw new RuntimeException("No URL Given");

        if( parsers.size() == 0 )
            throw new RuntimeException("No Parsers");

        try {

            HTTPRequestHandler requestHandler = new HTTPRequestHandler(url);
            content = requestHandler.executeGet("");

            System.out.println("Got content size: " + content.length());
            System.out.println("Got content:\n\n" + content);

        } catch (DeliveryException e) {

            e.printStackTrace();
            throw new RuntimeException("Error getting page");

        }

        for (ParserInterface parser : parsers) {

            parser.execute(url, content, "", result);

        }

        return result;

    }


}
