package yashaswi.cartappandroid;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Confirmation extends AppCompatActivity {

    ShoppingCart shpcrt;
    SharedPreferences sharingItem;
    ListView showAddCartList,userHistoryCart;
    MyDB mydb;
    String uid;
    String UserName;

    List<String> addCartList,historycartList;
    TextView userName,totalDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharingItem = getSharedPreferences("Cart_items_object", Context.MODE_PRIVATE);
        UserName = sharingItem.getString("UserName","").toUpperCase();
        mydb = new MyDB(this);


        setContentView(R.layout.activity_confirmation);
        userName = (TextView)this.findViewById(R.id.uname);

        showAddCartList  = (ListView)this.findViewById(R.id.ConfirmCart);
        userHistoryCart = (ListView) this.findViewById(R.id.historyList);

        totalDisplay = (TextView)this.findViewById(R.id.cartConfirmTotal);
        userName.setText("Welcome :" + UserName+"\nPlease Verify and Confirm!!!!!!!");
        shpcrt = (ShoppingCart) getIntent().getSerializableExtra("ShoppingCartObj");

        addCartList = new ArrayList<String>();
        historycartList = new ArrayList<String>();

        ArrayAdapter<String> addCartAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, addCartList);
        showAddCartList.setAdapter(addCartAdapter);

        ArrayAdapter<String> historyCartAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, historycartList);
        userHistoryCart.setAdapter(historyCartAdapter);

        shpcrt = (ShoppingCart) getIntent().getSerializableExtra("ShoppingCartObj");
        HashMap<String,Integer> itemAndQty = shpcrt.getItemsAndQuantities();

        for(String key:shpcrt.getItemsAndQuantities().keySet()){
            addCartList.add(key + "(Quantity : "+itemAndQty.get(key)+" )");
        }

        totalDisplay.setText("Total : $" + shpcrt.getToalAmount());

        addCartAdapter.notifyDataSetChanged();


        uid = sharingItem.getString("userId","");
        if(uid!=""){
            for(int i =0;i<mydb.selectOrderRecords(uid).size();i++){
                historycartList.add(mydb.selectOrderRecords(uid).get(i));
            }
            historyCartAdapter.notifyDataSetChanged();
        }


    }

    public void sendSMS(View view){
        ArrayList<OrderDetails> odList = new ArrayList<OrderDetails>();
        HashMap<String,Integer> itemAndQty = shpcrt.getItemsAndQuantities();
        OrderDetails od = new OrderDetails();

        for(String key:itemAndQty.keySet()){
            od.setUserid(uid);
            od.setItem(key);
            od.setQuantity(itemAndQty.get(key).toString());
            odList.add(od);
        }

        mydb.insertOrder(odList);
        String phoneNumber = mydb.getPhoneNumber(uid);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        String msgTxt = "Hi,"+UserName+" Thank you for Shopping,Total Bill Amount is $"+shpcrt.getToalAmount();

        try{
            SmsManager smsManager = SmsManager.getDefault();
            PendingIntent sentPI;
            String SENT = "SMS_SENT";

            sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);

            smsManager.sendTextMessage(phoneNumber, null, msgTxt, sentPI, null);
            Toast.makeText(getApplicationContext(), "Confirmation SMS Sent to "+phoneNumber,
                    Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"SMS Sending failed, please try again later!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



        callFirstActivity(view);
    }

    public void callFirstActivity(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}
