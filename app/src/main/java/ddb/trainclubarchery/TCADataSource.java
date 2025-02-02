package ddb.trainclubarchery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class TCADataSource {
    SQLiteDatabase database;
    DatabaseHelper dbHelper;
    int mode;
    public static final String TAG = "TCADataSource";

    public TCADataSource(Context context)
    {
        dbHelper = new DatabaseHelper(context,
                DatabaseHelper.DATABASE_NAME,
                null,
                DatabaseHelper.DATABASE_VERSION);
        mode = 0;
    }
    public TCADataSource(Context context, int mode2){
        dbHelper = new DatabaseHelper(context,
                DatabaseHelper.DATABASE_NAME,
                null,
                DatabaseHelper.DATABASE_VERSION);
        mode = mode2;
    }

    public void open() throws SQLException
    {
        open(false);
    }

    public void open(boolean refresh) throws SQLException
    {
        database = dbHelper.getWritableDatabase();
        Log.d(TAG, "open: " + database.isOpen());
        if(refresh) refreshData();
    }
    public void close()
    {
        dbHelper.close();
    }

    public void refreshData()
    {
        Log.d(TAG, "refreshData: Start");

        // Delete and
        int rowsDeleted = deleteAll();
        int results = 0;

        // Pre-load names if mode == 1, if not do not pre-load names
        if(mode==1){
            ArrayList<String> names = new ArrayList<String>();
            names.add("Dean");
            names.add("Gary");
            names.add("Tanner");

            Log.d(TAG, "refreshData: names count: " + names.size());
            for(String name : names)
            {
                results += insertArcher(name);
            }

        }
        Log.d(TAG, "refreshData: End: "+ rowsDeleted + " rows Removed. " + results + " rows Inserted...");
    }

    public String getOneArcher(String name){
        Log.d(TAG, "get: start");
        try{
            String sql = "select * from tblAcher where Name=" + name;
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
        }
        catch(Exception ex)
        {
            Log.d(TAG, "get: Error");
        }
        return name;
    }

    public ArrayList<String> getArchers()
    {
        Log.d(TAG, "get: start");
        ArrayList<String> archers = new ArrayList<String>();

        try
        {
            String sql = "Select * from tblArcher";
            Cursor cursor = database.rawQuery(sql,null);
            String name;
            Log.d(TAG, "get: number of names: " + cursor.getCount());

            while (cursor.moveToNext()) {
                name = cursor.getString(0);
                archers.add(name);
                Log.d(TAG, "get: " + name);
            }
        }
        catch(Exception ex)
        {
            Log.d(TAG, "get: " + ex.getMessage());
            ex.printStackTrace();
        }

        Log.d(TAG, "get: " + archers.size());
        return archers;
    }

    public int deleteAll()
    {
        int rows = 0;
        Log.d(TAG, "deleteAll: start");

        try
        {
            String sql = "select * from tblGame";
            Cursor cursor = database.rawQuery(sql, null);
            rows = cursor.getCount();

            sql = "delete from tblGame";
            database.rawQuery(sql,null);

            sql = "Select * from tblArcher";
            cursor = database.rawQuery(sql,null);
            rows += cursor.getCount();

            sql = "delete from tblArcher";
            database.rawQuery(sql,null);

        }
        catch(Exception ex)
        {
            Log.d(TAG, "deleteAll: Error");
        }
        Log.d(TAG, "deleteAll: end. deleted " + rows + " rows");
        return rows;
    }

    public int deleteArcher(String name)
    {
        int rows = 0;
        Log.d(TAG, "deleteArcher: Start name=" + name);

        try
        {
            String where = "name=" +name;
            database.delete("tblArcher",where,null);
            rows = 1;
        }
        catch(Exception ex)
        {
            Log.d(TAG, "deleteArcher: Error" + ex.getMessage());
        }
        Log.d(TAG, "deleteArcher: End rows=" + rows);
        return rows;
    }

    public int insertArcher(String name)
    {
        try
        {
            Log.d(TAG, "insertArcher: start");
            if(database != null)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put("Name", name);

                database.insert("tblArcher",null, contentValues);
                Log.d(TAG, "insertArcher: " + name);

                contentValues.clear();
            }
        }
        catch(Exception ex)
        {
            Log.d(TAG, "insertArcher: " + ex.getMessage());
        }

        return 1;
    }


}
