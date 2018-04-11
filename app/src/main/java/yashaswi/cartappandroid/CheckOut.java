package yashaswi.cartappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CheckOut extends AppCompatActivity {

    Button onSubmitLogin;
    EditText uname,upwd;
    SharedPreferences sharingItem;
    ShoppingCart shpcrt;
    SharedPreferences.Editor editor;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        shpcrt = (ShoppingCart) getIntent().getSerializableExtra("ShoppingCartObj");
        sharingItem = getSharedPreferences("Cart_items_object", Context.MODE_PRIVATE);
        editor = sharingItem.edit();
        setSupportActionBar(toolbar);
        onSubmitLogin = (Button)this.findViewById(R.id.submit);
        uname = (EditText)this.findViewById(R.id.uname);
        upwd = (EditText)this.findViewById(R.id.pwd);
        msg = (TextView)this.findViewById(R.id.msg);


    }

    public void register(View view){
        Intent callNewActvty = new Intent(this,Register.class);
        callNewActvty.putExtra("ShoppingCartObj",shpcrt);
        startActivity(callNewActvty);
    }

    public void verifyUser(View view){
        MyDB dbHandler = new MyDB(this);
        String name = uname.getText().toString();
        String pwd = upwd.getText().toString();
        String UFname = dbHandler.validateUser(name, pwd).toString();
        System.out.println("AAAAAAAAAAAaaaaaaaaaaaa" + UFname);
        editor.putString("UserName", UFname);
        editor.putString("userId",name);
        editor.commit();
        Intent callNewActvty = new Intent(this,Confirmation.class);
        callNewActvty.putExtra("ShoppingCartObj", shpcrt);
        if(UFname!=""&&UFname!=null&&!UFname.isEmpty())
        startActivity(callNewActvty);
        else
        {
            msg.setTextColor(Color.RED);
            msg.setText("Invalid Credentials!!!!!!!!!");

        }

    }

}
