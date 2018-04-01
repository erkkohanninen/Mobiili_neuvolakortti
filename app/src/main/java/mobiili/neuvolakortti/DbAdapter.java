package mobiili.neuvolakortti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tanja on 01/04/2018.
 */

public class DbAdapter {

    static final String DATABASE_NAME = "child_growth";

    //tables
    static final String TABLE_CHILD = "child";
    static final String TABLE_VACCINE = "vaccine";
    static final String TABLE_VACCINATION = "vaccination";
    static final String TABLE_MEASURES = "measures";
    static final String TABLE_DEVELOPMENTS = "developments";
    static final String TABLE_DEVELOPMENT_STEP = "development_step";


    //common column names
    static final String KEY_ID = "_id";
    static final String KEY_CHILD_ID = "child_id";

    //Table child column names
    static final String KEY_CHILD_NAME = "child_name";
    static final String KEY_DATE_OF_BIRTH = "date_of_birh";
    static final String KEY_BIRTH_WEIGHT = "birth_weight";
    static final String KEY_BIRTH_HEIGHT = "birth_height";
    static final String KEY_BIRH_HEAD = "birth_head";

    //Table vaccine column name

    static final String KEY_VACCINE_NAME = "vaccine_name";

    //Table vaccination column names
    static final String KEY_VACCINE_ID = "vaccine_id";
    static final String KEY_DATE_GIVEN = "date_given";

    //Table measures column names
    static final String KEY_WEIGHT = "weight";
    static final String KEY_HEIGHT = "height";
    static final String KEY_HEAD = "head";
    static final String KEY_DATE_MEASURED = "date_measured";

    //Table developments column names

    static final String KEY_DEVELOPMENT_NAME = "development_name";

    //Table development_step column names
    static final String KEY_DEVELOPMENTS_ID = "developments_id";
    static final String KEY_DATE_REACHED = "date_reached";


    static final int DATABASE_VERSION = 1;
    static final String TAG = "DBUserAdapter";


    private static final String CREATE_TABLE_CHILD = "CREATE TABLE "
            + TABLE_CHILD + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CHILD_NAME
            + " TEXT NOT NULL," + KEY_DATE_OF_BIRTH + " DATE NOT NULL," + KEY_BIRTH_WEIGHT + "FLOAT,"
            + KEY_BIRTH_HEIGHT + "FLOAT," + KEY_BIRH_HEAD + "FLOAT " + ")";

    private static final String CREATE_TABLE_VACCINE = "CREATE TABLE "
            + TABLE_VACCINE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_VACCINE_NAME + "TEXT NOT NULL" + ")";

    private static final String CREATE_TABLE_VACCINATION = "CREATE TABLE "
            + TABLE_VACCINATION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CHILD_ID
            + " INTEGER NOT NULL," + KEY_VACCINE_ID + " INTEGER NOT NULL," + KEY_DATE_GIVEN + "DATE NOT NULL" + ")";

    private static final String CREATE_TABLE_MEASURES = "CREATE TABLE "
            + TABLE_MEASURES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CHILD_ID
            + " INTEGER NOT NULL," + KEY_WEIGHT + " FLOAT, " + KEY_HEIGHT + " FLOAT," + KEY_HEAD
            + "FLOAT" + KEY_DATE_MEASURED + "DATE NOT NULL" + ")";

    private static final String CREATE_TABLE_DEVELOPMENTS = "CREATE TABLE "
            + TABLE_DEVELOPMENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DEVELOPMENT_NAME
            + " TEXT NOT NULL" + ")";

    private static final String CREATE_TABLE_DEVELOPMENT_STEP = "CREATE TABLE "
            + TABLE_DEVELOPMENT_STEP + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CHILD_ID
            + " INTEGER NOT NULL," + KEY_DEVELOPMENTS_ID + "INTEGER NOT NULL," + KEY_DATE_REACHED + "DATE NOT NULL" + ")";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DbAdapter(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper{


        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try{
                db.execSQL(CREATE_TABLE_CHILD);
                db.execSQL(CREATE_TABLE_VACCINE);
                db.execSQL(CREATE_TABLE_VACCINATION);
                db.execSQL(CREATE_TABLE_MEASURES);
                db.execSQL(CREATE_TABLE_DEVELOPMENTS);
                db.execSQL(CREATE_TABLE_DEVELOPMENT_STEP);

                //TÄHÄN SYÖTETÄÄN DEVELOPMENT_STEPS TAULUUN VALMIIKSI KEHITYSASKELEET JA VACCINE
                //TAULUUN SAMA HOMMA??

            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILD);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASURES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVELOPMENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVELOPMENT_STEP);
            onCreate(db);
        }
    }

    //----opens the database---
    public DbAdapter open() throws SQLException{

        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database----

    public void close(){

        DBHelper.close();
    }

    //-----inserts a child------

    public void addChild(Child child){

        ContentValues values = new ContentValues();
        values.put(KEY_CHILD_NAME, child.getName());
        values.put(KEY_DATE_OF_BIRTH, child.getDateOfBirth());
        values.put(KEY_BIRTH_WEIGHT, child.getWeight());
        values.put(KEY_BIRTH_HEIGHT, child. getHeight());
        values.put(KEY_BIRH_HEAD, child.getHead());

        // Inserting Row
        db.insert(TABLE_CHILD, null, values);

    }

}
