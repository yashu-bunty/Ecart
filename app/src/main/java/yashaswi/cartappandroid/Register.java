package yashaswi.cartappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Register extends AppCompatActivity {

    EditText fname,pnumber,uname,pwd;
    RadioGroup gendergroup;
    RadioButton gender,selectOpt;
    Button submitRegister;
    SharedPreferences sharingItem;
    SharedPreferences.Editor editor;
    ShoppingCart shpcrt;
    MyDB mydb;

    //For Camera Use START
    static int TAKE_PICTURE = 1;
    ImageView ivThumbnailPhoto;

    //FOR CAMERA USE END


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharingItem = getSharedPreferences("Cart_items_object", Context.MODE_PRIVATE);
        shpcrt = (ShoppingCart) getIntent().getSerializableExtra("ShoppingCartObj");
        editor = sharingItem.edit();
        fname = (EditText) this.findViewById(R.id.fname);
        pnumber = (EditText) this.findViewById(R.id.pno);
        uname = (EditText) this.findViewById(R.id.uname);
        selectOpt = (RadioButton) this.findViewById(R.id.genderM);
        selectOpt.setChecked(true);
        pwd = (EditText) this.findViewById(R.id.pwd);
        gendergroup = (RadioGroup) this.findViewById(R.id.gender);
        submitRegister = (Button) this.findViewById(R.id.submitregister);
        ivThumbnailPhoto = (ImageView)this.findViewById(R.id.imageView);
        mydb = new MyDB(this);

    }

    public void register(View view) {
        if(validateFields()) {
            System.out.println("?????????????????????"+validateFields());
            gender = (RadioButton) this.findViewById(gendergroup.getCheckedRadioButtonId());
            User userDetails = new User(uname.getText().toString(), pwd.getText().toString(),
                    fname.getText().toString(), "+1"+pnumber.getText().toString(), gender.getText().toString());
            mydb.addUser(userDetails);
            editor.putString("UserName", fname.getText().toString());
            editor.putString("userId",uname.getText().toString());
            editor.commit();

            Intent callNewActvty = new Intent(this, Confirmation.class);
            callNewActvty.putExtra("ShoppingCartObj", shpcrt);
            startActivity(callNewActvty);
        }
    }

    public void addImage(View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        startActivityForResult(cameraIntent, TAKE_PICTURE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap)intent.getExtras().get("data");
            ivThumbnailPhoto.setImageBitmap(photo);
            ivThumbnailPhoto.setVisibility(View.VISIBLE);
            submitRegister.setClickable(true);

        }
    }

    public boolean validateFields(){
        boolean valid = true;
        if(uname.getText().toString() == ""){
            uname.setError("Please Select User Name");
            valid = false;
        }
        else if(pwd.getText().toString() == ""){
            pwd.setError("Please Enter Password");
            valid = false;
        }else if(fname.getText().toString()== ""){
            fname.setError("Please Enter Full Name");
            valid = false;
        }else if(pnumber.getText().toString() == ""){
            pnumber.setError("Please Enter Phone Number");
            valid = false;
        }else if(!mydb.checkUserIdAvialability(uname.getText().toString()).equals("")){
            valid = false;
            uname.setError("Please select Other Unser Name");

        }
        return valid;
    }
}
