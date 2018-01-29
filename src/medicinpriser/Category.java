package medicinpriser;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-23
 * Time: 06:09
 * To change this template use File | Settings | File Templates.
 */
public class Category {

    private final String name;
    private final int id;

    public Category(int id, String name){

        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString(){

        return name + "(" + id + ")";
    }

}
