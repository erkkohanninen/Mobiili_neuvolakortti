package mobiili.neuvolakortti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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


    static final int DATABASE_VERSION = 11;
    static final String TAG = "DBUserAdapter";


    private static final String CREATE_TABLE_CHILD = "CREATE TABLE "
            + TABLE_CHILD + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CHILD_NAME
            + " TEXT NOT NULL," + KEY_DATE_OF_BIRTH + " DATE NOT NULL " + ")";

    private static final String CREATE_TABLE_VACCINE = "CREATE TABLE "
            + TABLE_VACCINE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_VACCINE_NAME + " TEXT NOT NULL" + ")";

    private static final String CREATE_TABLE_VACCINATION = "CREATE TABLE "
            + TABLE_VACCINATION + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CHILD_ID
            + " INTEGER NOT NULL," + KEY_VACCINE_ID + " INTEGER NOT NULL," + KEY_DATE_GIVEN + " DATE NOT NULL" + ")";

    private static final String CREATE_TABLE_MEASURES = "CREATE TABLE "
            + TABLE_MEASURES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CHILD_ID
            + " INTEGER NOT NULL," + KEY_WEIGHT + " REAL, " + KEY_HEIGHT + " REAL," + KEY_HEAD
            + " REAL," + KEY_DATE_MEASURED + " DATE NOT NULL" + ")";

    private static final String CREATE_TABLE_DEVELOPMENTS = "CREATE TABLE "
            + TABLE_DEVELOPMENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DEVELOPMENT_NAME
            + " TEXT NOT NULL" + ")";

    private static final String CREATE_TABLE_DEVELOPMENT_STEP = "CREATE TABLE "
            + TABLE_DEVELOPMENT_STEP + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CHILD_ID
            + " INTEGER NOT NULL," + KEY_DEVELOPMENTS_ID + " INTEGER NOT NULL," + KEY_DATE_REACHED + " DATE NOT NULL" + ")";

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


                // Insert vaccine names to table_vaccine

                db.execSQL("INSERT INTO 'vaccine' ('vaccine_name') VALUES ('Rotavirus')");
                db.execSQL("INSERT INTO 'vaccine' ('vaccine_name') VALUES ('Vesirokko')");
                db.execSQL("INSERT INTO 'vaccine' ('vaccine_name') VALUES ('PCV')");
                db.execSQL("INSERT INTO 'vaccine' ('vaccine_name') VALUES ('DTaP-IPV-Hib')");
                db.execSQL("INSERT INTO 'vaccine' ('vaccine_name') VALUES ('MPR')");
                db.execSQL("INSERT INTO 'vaccine' ('vaccine_name') VALUES ('Influenssa')");



                //Insert developments to table_developments
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Jokin merkittävä sana')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Kävelee tuetta')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Seisoo tuetta')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Kävelee tukien')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Nousee pystyyn')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Peukalo-etusormiote')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Nousee istumaan')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Ensimmäinen hammas')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Kääntyy vatsalleen')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Tarttuu esineeseen')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Kannattaa päätään käsistä kohottaessa')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Jokeltaa')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Konttaa')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Ryömii')");
                db.execSQL("INSERT INTO 'developments' ('development_name') VALUES ('Nauraa')");



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

        //add values in order to put in table_child
        ContentValues values = new ContentValues();
        values.put(KEY_CHILD_NAME, child.getName());
        values.put(KEY_DATE_OF_BIRTH, child.getDateOfBirth());

        // insert Row to table_child
        db.insert(TABLE_CHILD, null, values);

        //Get Child´s ID from database in order to insert birth measures to table_measures
        String getChildId = "SELECT " + KEY_ID + " FROM " + TABLE_CHILD + " WHERE " + KEY_CHILD_NAME + " = '" + child.getName() + "'";

        Cursor cursor = db.rawQuery(getChildId, null);
        if(cursor != null){
            cursor.moveToFirst();
            String child_id_fetched = cursor.getString(0);
            cursor.close();
            Log.d("id: ", child_id_fetched);

            // add values in order to put in table_measures
            ContentValues values1 = new ContentValues();
            values1.put(KEY_CHILD_ID, child_id_fetched);
            values1.put(KEY_WEIGHT, child.getWeight());
            values1.put(KEY_HEIGHT, child.getHeight());
            values1.put(KEY_HEAD, child.getHead());
            values1.put(KEY_DATE_MEASURED, child.getDateOfBirth());
            //insert row to table_measures
            db.insert(TABLE_MEASURES, null, values1);
        }
        else {
            Log.d(TAG,"notfound");
        }
    }


    // Getting All Children
    public List<Child> getAllChildren() {
        List<Child> listOfChildren = new ArrayList<Child>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHILD;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Child child = new Child();
                child.setId(Integer.parseInt(cursor.getString(0)));
                child.setName(cursor.getString(1));
                child.setDateOfBirth(cursor.getString(2));

                // Adding child to list
                listOfChildren.add(child);
            } while (cursor.moveToNext());
        }

        // return contact list
        return listOfChildren;
    }

    public List<String> getAllDevelopments(){
        List<String> listOfDevelopments = new ArrayList<>();
        //Selecet all -query
        String selectQuery = "SELECT " + KEY_DEVELOPMENT_NAME + " FROM " + TABLE_DEVELOPMENTS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                listOfDevelopments.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return listOfDevelopments;
    }

}
