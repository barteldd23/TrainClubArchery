package ddb.trainclubarchery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "tca.db";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_ARCHER =
            "create table tblArcher(Name text primary key)";
    public static final String CREATE_GAME =
            "create table tblGame(Name text, "
            + "Date datetime, "
            + "Season text, "
            + "ScoreText text, "
            + "ScoreInt int, "
            + "IsFinished bit,"
            + "Id integer autoincrement,"
            + "Primary Key (Name, Date) )";
    public DatabaseHelper(@Nullable Context context,
                          @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        Log.d(TAG, "DatabaseHelper: Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: " + CREATE_ARCHER);
        db.execSQL(CREATE_ARCHER);

        //Log.d(TAG, "onCreate: " + CREATE_GAME);
        //db.execSQL(CREATE_GAME);

        Log.d(TAG, "onCreate: End");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d(TAG, "onUpgrade: ");
        db.execSQL("DROP TABLE IF EXISTS tblGame");
        db.execSQL("DROP TABLE IF EXISTS tblArcher");
        onCreate(db);
    }
}
