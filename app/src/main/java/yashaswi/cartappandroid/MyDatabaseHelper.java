package yashaswi.cartappandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by aakas on 4/28/2016.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "USERDETAILS";
    private static final int DATABASE_VERSION = 4;


    private static final String DATABASE_CREATE = "create table USER( _id integer primary key AUTOINCREMENT, full_name text NOT NULL, PHONE_NO text NOT NULL, " +
            "userid text NOT NULL, password text NOT NULL,gender text NOT NULL);";
    private static final String DATABASE_ORDER_CREATE = "create table orderdetails( _id integer primary key AUTOINCREMENT,userid text NOT NULL,item_name text NOT NULL," +
            "quantity text NOT NULL,FOREIGN KEY (userid) REFERENCES USER(userid) );";


    public MyDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_ORDER_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS USER");
        database.execSQL("DROP TABLE IF EXISTS orderdetails");
        onCreate(database);

    }


}
