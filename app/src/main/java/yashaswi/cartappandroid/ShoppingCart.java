package yashaswi.cartappandroid;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by yashaswi on 4/22/2016.

 */
public class ShoppingCart implements Serializable {
    HashMap<String,Integer> itemsAndQuantities = new HashMap<String,Integer>();
    String toalAmount = "";

    public ShoppingCart(String toalAmount, HashMap<String, Integer> itemsAndQuantities) {
        this.toalAmount = toalAmount;
        this.itemsAndQuantities = itemsAndQuantities;
    }

    public HashMap<String, Integer> getItemsAndQuantities() {
        return itemsAndQuantities;
    }

    public void setItemsAndQuantities(HashMap<String, Integer> itemsAndQuantities) {
        this.itemsAndQuantities = itemsAndQuantities;
    }

    public String getToalAmount() {
        return toalAmount;
    }

    public ShoppingCart() {
    }

    public void setToalAmount(String toalAmount) {
        this.toalAmount = toalAmount;
    }
}
