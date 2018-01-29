package sniffer;


import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**********************************************************************
 *
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-22
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */

public class HTMLContext {

    private List<Element> stack = new ArrayList<Element>();
    int lastMatch = 0;
    private final String group;
    private String domain;

    public HTMLContext(String group, String domain) {

        this.group = group;
        this.domain = domain;
    }

    public void push(Element tag){

        stack.add(tag);
    }

    public Element pop(){

        if(stack.size() == lastMatch){

            System.out.println(" - Leaving match tag");
            lastMatch = 0;
        }

        return stack.remove(stack.size()-1);


    }

    public Element top(){

        return stack.get(stack.size() - 1);

    }


    public String toString(){

        StringBuffer display = new StringBuffer();

        display.append("Stack:\n");

        int depth = stack.size();

        for (Element element : stack) {

            display.append( " -- " + (depth--) + " Element <" + element.tagName() + "> id(" + element.attr("id") + ")" + " class(" + element.attr("class") + ")" /* + " text(" + element.text() + ")" */ + "\n" );
        }

        return display.toString();

    }


    public void checkPush(Element element) {

        if(stack.size() == 0){

            push(element);
        }
        else{

            Element last = top();

            while(element.parent() != last){
                pop();
                last = top();
            }

            push(element);

        }

    }

    public List<Element> getStack(){

        return stack;
    }

    public void setMatch() {

        lastMatch = stack.size();       // Note when we last matched

    }

    public boolean isInMatch(){

        return lastMatch > 0;
    }

    public String indent() {
        String indentation = "";
        for (int i = 0; i <= stack.size(); i++)
            indentation += " ";
        return indentation;

    }

    public String getGroup(){
        return group;
    }

    public String getDomain() {
        return domain;
    }
}
