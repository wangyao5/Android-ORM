package com.xydroid.dbutils.env.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import com.xydroid.dbutils.SQLiteContext;

import java.io.File;
import java.io.IOException;

public class SQLiteDatabaseContext extends ContextWrapper {
    private static final String TAG = "DatabaseContext";
    private SQLiteContext mSqliteContext;
    private Context mContext;

    public SQLiteDatabaseContext(SQLiteContext sqliteContext) {
        super(sqliteContext.getContext());
        mSqliteContext = sqliteContext;
        mContext = sqliteContext.getContext();
    }

    /**
     * get external storage database File
     *
     * @param name
     */
    @Override
    public File getDatabasePath(String name) {
        String dbPath;
        if (mSqliteContext.getSQLiteEnv().isExternal()) {
            boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
            if (!sdExist) {
                dbPath = mContext.getFilesDir().getAbsolutePath() + File.separatorChar + mSqliteContext.getSQLiteEnv().getDbPath();
            } else {
                dbPath = android.os.Environment.getExternalStorageDirectory().toString() +
                        File.separatorChar + mSqliteContext.getSQLiteEnv().getDbPath();
            }
        } else {
            dbPath = mContext.getFilesDir().getAbsolutePath() + File.separatorChar + mSqliteContext.getSQLiteEnv().getDbPath();
        }

        File pathFile = new File(dbPath);
        if (!pathFile.exists())
            pathFile.mkdirs();
        boolean isFileCreateSuccess = false;
        File dbFile = new File(dbPath + File.separatorChar + name);
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            isFileCreateSuccess = true;

        if (isFileCreateSuccess)
            return dbFile;
        else
            return null;
    }

    /**
     * android 2.3 and bellow used to open database
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }

    /**
     * Android 4.0 used to open database
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     * @see android.content.ContextWrapper#openOrCreateDatabase(String, int,
     * android.database.sqlite.SQLiteDatabase.CursorFactory,
     * android.database.DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory,
                                               DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }
}