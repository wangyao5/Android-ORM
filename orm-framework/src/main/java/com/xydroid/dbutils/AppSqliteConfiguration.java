package com.xydroid.dbutils;

import android.content.Context;

import com.xydroid.dbutils.test.TestRepository;

@Configurable(external = true,dbPath = "com.wy.test", dbName = "test.db", repository = {TestRepository.class})
public class AppSqliteConfiguration extends SqliteContext {
    public AppSqliteConfiguration(Context context) {
        super(context);
    }

}
