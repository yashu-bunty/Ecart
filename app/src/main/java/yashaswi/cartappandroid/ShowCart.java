package yashaswi.cartappandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowCart extends AppCompatActivity {

    ArrayList<String> addCartList;
    String itemName;
    int itemQty;
    TextView totalDisplay;
    ArrayAdapter<String> addCartAdapter;
    ListView showAddCartList;
    ShoppingCart shpcrt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addCartList = new ArrayList<String>();
        showAddCartList  = (ListView)this.findViewById(R.id.cartListwithQty);
        totalDisplay = (TextView)this.findViewById(R.id.Idtotal);

        addCartAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, addCartList);
        showAddCartList.setAdapter(addCartAdapter);



        shpcrt = (ShoppingCart) getIntent().getSerializableExtra("ShoppingCartObj");
        HashMap<String,Integer> itemAndQty = shpcrt.getItemsAndQuantities();

        for(String key:shpcrt.getItemsAndQuantities().keySet()){
            itemName = key;
            itemQty= itemAndQty.get(key);

            addCartList.add(itemName + "(Quantity : "+itemQty+" )");

        }

        totalDisplay.setText("Total : $" + shpcrt.getToalAmount());


        addCartAdapter.notifyDataSetChanged();




    }

    public void checkout(View view){
        Intent callNewActvty = new Intent(this,CheckOut.class);
        callNewActvty.putExtra("ShoppingCartObj",shpcrt);
        startActivity(callNewActvty);
    }

    public void continueShopping(View view){
        Intent callNewActvty = new Intent(this,MainActivity.class);
        startActivity(callNewActvty);
    }


}
