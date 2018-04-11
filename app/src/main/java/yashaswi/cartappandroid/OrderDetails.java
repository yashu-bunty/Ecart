package yashaswi.cartappandroid;

/**
 * Created by madan on 4/29/2016.
 */
public class OrderDetails {

    private int _id;
    private String userid;
    private String item;
    private String Quantity;

    public OrderDetails() {
    }

    public OrderDetails(int _id, String userid, String item, String quantity) {
        this._id = _id;
        this.userid = userid;
        this.item = item;
        Quantity = quantity;
    }

    public OrderDetails(String userid, String item, String quantity) {
        this.userid = userid;
        this.item = item;
        Quantity = quantity;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
