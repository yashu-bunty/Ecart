package yashaswi.cartappandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.Map;

/**
 * Created by madan on 4/22/2016.
 */
public class AddCart extends AsyncTask<Context,Void,String> {

    public MainActivity mActvty;

    public AddCart(MainActivity a) {
        this.mActvty = a;
    }

    @Override
    protected String doInBackground(Context... params) {
        Context context=params[0];

        SharedPreferences sharingItem = context.getSharedPreferences("",0);
        double total = 0.0;

        for(Map.Entry<String,Integer> entry:mActvty.itemAndQuantity.entrySet()){

            String key = entry.getKey();
            int gtyvalue = entry.getValue();
            for (int i=0;i<sharingItem.getInt("CartListSize",0);i++){
                if(sharingItem.getString("cartItem"+i,"").toString().equals(key)){
                    int itemPrice = sharingItem.getInt("CartItemPrice"+i,0);
                    total = total + (double)(itemPrice * gtyvalue);
                }
            }
        }
        return String.valueOf(total);
    }
}
