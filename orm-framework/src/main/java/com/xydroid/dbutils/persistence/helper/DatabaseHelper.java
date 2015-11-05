package com.xydroid.dbutils.persistence.helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final int VERSION = 1;
    public static Map<String, String[]> TABLES;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DatabaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "create database table");
        for (String tableName : TABLES.keySet()) {
            db.execSQL(createSql(tableName));
        }
    }

    private String createSql(String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(tableName);
        sb.append(" (");
        String[] comluns = TABLES.get(tableName);
        int len = comluns.length - 1;
        for (int i = 0; i < len; i++) {
            sb.append(comluns[i]);
            sb.append(" TEXT");
            sb.append(", ");
        }
        sb.append(comluns[len]);
        sb.append(" TEXT");
        sb.append(");");
        return sb.toString();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String tableName : TABLES.keySet()) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }
        onCreate(db);
    }

}
