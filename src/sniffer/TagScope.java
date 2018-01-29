package sniffer;

import org.jsoup.nodes.Element;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-23
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
public class TagScope {

    private final String tagConstraint;
    private final String idConstraint;
    private final String classConstraint;
    private AttributeConstraint attributeConstraint = null;

    public TagScope(String aTag, String anId, String aClass){

        this.idConstraint = anId;
        this.tagConstraint = aTag;
        this.classConstraint = aClass;
    }


    public String toString(){
        return "<"+ tagConstraint+" (#"+ idConstraint+" ."+classConstraint+") >";
    }

    /***********************************************************
     *
     *          Matching the scope
     *
     *
     * @param element
     * @return
     */

    public boolean matches(Element element) {

        boolean match = matchTag( element );
        if(!match){

            //System.out.println("NOT Matching tag " + element.tagName() + " with " + this.toString());
            return match;
        }

        match &= matchClass( element );
        if(!match){

            System.out.println("NOT Matching class " + truncate(10, element.className()) + " with " + this.toString());
            return match;
        }

        match &= matchAttribute(element);
        if(!match){

            System.out.println("NOT Matching attribute " + this.attributeConstraint.toString() + " with " + this.toString());
            return match;
        }

        match &= matchId( element );
        if(!match){

            System.out.println("NOT Matching id " + truncate(10, element.id()) + " with " + this.toString());
        }
        else
            System.out.println("Matching " + truncate(10, element.tagName()) + " with " + this.toString());

        return match;
    }

    private boolean matchAttribute(Element element) {

        return attributeConstraint == null || element.attr(attributeConstraint.name).contains(attributeConstraint.value);
    }

    private boolean matchClass(Element element) {

        Set<String> classNames = element.classNames();

        if(classConstraint == null)
            return true;

        if(classConstraint.equals("")){
            boolean classMatch = element.attributes().html().contains("class") && element.classNames().size() == 0;
            return classMatch;
        }


        System.out.println("Class: " + element.className());
        //System.out.println("Class: " + element.classNames());

        return  element.className().contains(classConstraint);
    }

    private boolean matchId(Element element) {
        return idConstraint == null || element.id().equals( idConstraint );
    }


    private boolean matchTag(Element element) {

        return element.tagName().equals( tagConstraint );          //TODO: Add regex here
    }

    private String truncate(int i, String s) {
        if(s.length() < i)
            return s;
        return s.substring(0, i) + "...";
    }

    public TagScope withAttribute(String name, String value) {

        this.attributeConstraint = new AttributeConstraint(name, value);
        return this;
    }
}
