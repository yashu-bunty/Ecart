package yashaswi.cartappandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aakas on 4/28/2016.
 */
public class MyDB {

    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String USER_TABLE  = "USER"; // name of table

    public final static String USER_ID="_id"; // id value for employee
    public final static String USER_FULL_NAME="FULL_NAME";  // name of employee
    public final static String USER_NUMBER="PHONE_NO";
    public final static String USER_USERID="USERID";
    public final static String USER_PWD="PASSWORD";
    public final static String USER_GENDER="GENDER";

    //Oder Table

    public final static String ORDER_TABLE  = "orderdetails"; // name of table

    public final static String ORDER_ID="_id"; // id value for employee
    public final static String ORDER_USERID="userid";  // name of employee
    public final static String ORDER_ITEM="item_name";  // name of employee
    public final static String ORDER_QUANTITY="quantity";  // name of employee





    /**
     *
     * @param context
     */
    public MyDB(Context context){
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public MyDB() {

    }

    // _id  ,  first_name  ,  last_name  ,  email  ,  phone
    public void addUser(User user){
        ContentValues values = new ContentValues();
        //values.put(CNT_ID, id);
        values.put(USER_FULL_NAME, user.getFname());
        values.put(USER_NUMBER, user.getPhoneNumer());
        values.put(USER_USERID, user.getUname());
        values.put(USER_PWD, user.getPwd());
        values.put(USER_GENDER,user.getGender());
        System.out.println("createRecords in MyDB ");
        database.insert(USER_TABLE, null, values);
        selectRecords();
    }

    public Cursor selectRecords() {
        String[] cols = new String[] {USER_ID, USER_FULL_NAME, USER_NUMBER, USER_USERID, USER_PWD,USER_GENDER};
        Cursor mCursor = database.query(true, USER_TABLE,cols,null, null, null, null, null, null);
        /*if (mCursor != null) {
            mCursor.moveToFirst();

        }*/
        while(mCursor.moveToNext()){
            System.out.println(" USER ID "+Integer.parseInt(mCursor.getString(0)));
            System.out.println(" USER FULL NAME "+mCursor.getString(1));
            System.out.println(" USER EMAIL "+mCursor.getString(2));
            System.out.println(" USER USERID "+mCursor.getString(3));
            System.out.println(" USER PASSWORD "+mCursor.getString(4));
            System.out.println(" USER GENDER "+mCursor.getString(4));
        }
        return mCursor;
    }

    public String validateUser(String id,String pwd){

        String[] cols = new String[] {USER_FULL_NAME};
        String selection = "userid = \"" + id + "\"and password = \""+pwd+"\"";
        Cursor cursor = database.query(true, USER_TABLE,cols,selection, null, null, null, null, null);
        String uname = "";

        User user =new User();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            uname = cursor.getString(0);
            cursor.close();
        }
        return uname;
    }

    public String checkUserIdAvialability(String id){
        String[] cols = new String[] {USER_FULL_NAME};
        String selection = "userid = \""+id+"\"";
        String name = "";
        Cursor cursor = database.query(true, USER_TABLE,cols,selection, null, null, null, null, null);
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            name = cursor.getString(0);
        }
        return name;
    }

    public void insertOrder(ArrayList<OrderDetails> ordersList){
        ContentValues values = new ContentValues();
        for(OrderDetails od:ordersList){
            values.put(ORDER_USERID, od.getUserid());
            values.put(ORDER_ITEM, od.getItem());
            values.put(ORDER_QUANTITY,od.getQuantity());
            database.insert(ORDER_TABLE, null, values);

        }
    }

    public List<String> selectOrderRecords(String uid) {
        String[] cols = new String[] {ORDER_ID,ORDER_USERID,ORDER_ITEM,ORDER_QUANTITY};
        Cursor mCursor = database.query(true, ORDER_TABLE,cols,null, null, null, null, null, null);

        List<String> historyList = new ArrayList<String>();
        while(mCursor.moveToNext()){
            historyList.add(mCursor.getString(2));
        }
        return historyList;
    }
    public String getPhoneNumber(String uid) {
        String[] cols = new String[] {USER_NUMBER};
        String selection = "userid = \""+uid+"\"";
        Cursor cursor = database.query(true, USER_TABLE,cols,selection, null, null, null, null, null);

        String pno="";
        if(cursor.moveToFirst()){
            System.out.println(" ORDER ID "+cursor.getString(0));
            pno = cursor.getString(0);
        }
        return pno;
    }
}
