package sniffer;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-03-27
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class AttributeConstraint {

    public final String name;
    public final String value;

    AttributeConstraint(String name, String value){

        this.name = name;
        this.value = value;
    }

    public String toString(){

        return name + ": '" + value + "'";
    }

}
