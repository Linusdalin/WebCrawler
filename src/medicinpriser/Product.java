package medicinpriser;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created with IntelliJ IDEA.
 * User: Linus
 * Date: 2017-04-14
 * Time: 16:34
 * To change this template use File | Settings | File Templates.
 */
public class Product {

    private final String name;
    private final String size;
    private final int productId;
    private final String packageId;
    private final Category category;

    public Product(String name, String size, int productId, String packageId, Category category){

        this.name = name;
        this.size = size;
        this.productId = productId;
        this.packageId = packageId;
        this.category = category;
    }

    public String getName(){
        return name;
    }
    public String getSize(){
        return size;
    }
    public int getProductId(){
        return productId;
    }
    public String getPackageId(){
        return packageId;
    }

    public Category getCategory(){
        return category;
    }

    public String toString(){

        return name + " (" +size +"): " + productId + "/" +packageId + "\n";
    }

}
