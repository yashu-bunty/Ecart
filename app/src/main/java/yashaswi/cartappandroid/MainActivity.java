package yashaswi.cartappandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Spinner cartspinner;
    ArrayList<String> cartList;
    ArrayList<String> cartItemPriceList;
    ImageView imageView;
    TextView pricetext,hintText,showCart;
    List<String> addcartItems;
    HashMap<String,Integer> itemAndQuantity;
    Button addcartbtn,removebtn;
    String totalAmount;
    SharedPreferences sharingItem;
    SharedPreferences.Editor editor;
    AddCart getTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cartspinner = (Spinner) this.findViewById(R.id.CartItems);

        BufferedReader in = null;

        sharingItem = getSharedPreferences("",0);

        editor = sharingItem.edit();
        cartList = new ArrayList<String>();
        cartItemPriceList = new ArrayList<String>();
        addcartItems = new ArrayList<String>();
        itemAndQuantity = new HashMap<String,Integer>();
        addcartbtn = (Button) this.findViewById(R.id.IdAddCart);
        removebtn = (Button) this.findViewById(R.id.IdremoveCart);
        hintText = (TextView) this.findViewById(R.id.hintText);
        imageView = (ImageView) this.findViewById(R.id.itemImageID);
        showCart = (TextView) this.findViewById(R.id.showCart);
        getTotal = new AddCart(this);
        final InputStream[] stream1 = {null};
        try {
            stream1[0] = getAssets().open("shoppingcart.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d1 = Drawable.createFromStream(stream1[0], null);
        imageView.setImageDrawable(d1);
        imageView.setVisibility(View.VISIBLE);

        editor.clear();

        try {
            in = new BufferedReader(new InputStreamReader(getAssets().open("CartDetails")));
            String line = in.readLine();
            cartList.add("-Please Select an Item-");

            while (line!=null) {
                int ind1 = line.indexOf(',');

                cartList.add(line.substring(0, ind1));
                cartItemPriceList.add(line.substring(ind1 + 1, line.length()));

                line =in.readLine();
            }
            editor.putInt("CartListSize", cartItemPriceList.size());
            for(int i = 1 ; i<cartList.size() ;i++) {
                editor.putString("cartItem" + (i-1), cartList.get(i));
                editor.putInt("CartItemPrice" + (i-1), Integer.parseInt(cartItemPriceList.get((i-1))));
            }
            editor.commit();

            ArrayAdapter<String> cartAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, cartList);
            cartspinner.setAdapter(cartAdapter);

             pricetext = (TextView) this.findViewById(R.id.Idprice);



            cartspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    pricetext.setVisibility(View.INVISIBLE);
//                    imageView.setVisibility(View.INVISIBLE);
                    addcartbtn.setVisibility(View.INVISIBLE);
                    hintText.setVisibility(View.VISIBLE);
                    removebtn.setVisibility(View.INVISIBLE);

                    if(cartspinner.getSelectedItemId()!=0){
                        pricetext.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        addcartbtn.setVisibility(View.VISIBLE);
                        hintText.setVisibility(View.INVISIBLE);
                        InputStream stream = null;
                        String selectedItem = cartspinner.getSelectedItem().toString();
                        try {
                            stream = getAssets().open(selectedItem.toLowerCase()+".jpg");
                            } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Drawable d = Drawable.createFromStream(stream, null);
                        imageView.setImageDrawable(d);

                        for(int i=0;i<cartList.size();i++){

                            if(cartList.get(i).toString() == selectedItem)
                                pricetext.setText("Price : $"+cartItemPriceList.get(i - 1));
                        }
                        if(addcartItems.contains(selectedItem)){
                            removebtn.setVisibility(View.VISIBLE);
                        }
                        /*if(addcartItems.size() > 0 && addcartItems.contains(selectedItem)){
                            addcartbtn.setText("Item Have Already added to Cart");
                            addcartbtn.setClickable(false);
                        }*/
                    }else{
                        try {
                            stream1[0] = getAssets().open("shoppingcart.jpg");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Drawable d1 = Drawable.createFromStream(stream1[0], null);
                        imageView.setImageDrawable(d1);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addItemToCart(View view){

        String item = cartspinner.getSelectedItem().toString();
        int quantity = 1;

        if(addcartItems.contains(item)){
            quantity= itemAndQuantity.get(item);
            itemAndQuantity.put(item, ++quantity);
        }else{
            removebtn.setVisibility(View.VISIBLE);
            addcartItems.add(item);
            itemAndQuantity.put(item,quantity);
        }
        setShowcartVisibility();

        totalAmount = getTotal.doInBackground(getApplicationContext());
    }

    public void removeItemofCart(View view){
        String item = cartspinner.getSelectedItem().toString();
        int quantity = 1;
        if(addcartItems.contains(item)){
            quantity = itemAndQuantity.get(item);
            if(--quantity > 0){
                itemAndQuantity.put(item, quantity);
            }else{
                itemAndQuantity.remove(item);
                addcartItems.remove(item);
                removebtn.setVisibility(View.INVISIBLE);
            }
        }
        setShowcartVisibility();
        totalAmount = getTotal.doInBackground(getApplicationContext());
    }

    public void setShowcartVisibility(){
        if(addcartItems.size()!= 0){
            showCart.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Item Added to Cart\nTotal Items : "+addcartItems.size(),
                    Toast.LENGTH_LONG).show();
        }else{
            showCart.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Item Removed from Cart\nTotal Items : "+addcartItems.size(),
                    Toast.LENGTH_LONG).show();
        }

    }

    public void showCart(View view){
        ShoppingCart shpcrt = new ShoppingCart(totalAmount,itemAndQuantity);
        Intent callNewActvty = new Intent(this,ShowCart.class);
        callNewActvty.putExtra("ShoppingCartObj",shpcrt);
        startActivity(callNewActvty);
    }
}
