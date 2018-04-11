package yashaswi.cartappandroid;

/**
 * Created by madan on 4/21/2016.
 */
public class CartItems {

    String itemName;
    String itemPrice;
    String imageUrl;

    public CartItems() {
    }

    public CartItems( String itemName, String itemPrice, String imageUrl) {

        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imageUrl = imageUrl;
    }





    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CartItems{" +
                ", itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
