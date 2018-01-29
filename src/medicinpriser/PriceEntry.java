package medicinpriser;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-14
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
public class PriceEntry {

    private final double price;

    public PriceEntry(double price){

        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String toString(){

        return  "Price: " + price;

    }

}
