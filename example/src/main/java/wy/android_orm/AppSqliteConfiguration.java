package wy.android_orm;

import android.content.Context;

import com.xydroid.dbutils.Configurable;
import com.xydroid.dbutils.SQLiteContext;
import com.xydroid.dbutils.test.TestRepository;

@Configurable(external = true, dbPath = "com.wy.test", dbName = "test.db", repository = {TestRepository.class})
public class AppSqliteConfiguration extends SQLiteContext {
    public AppSqliteConfiguration(Context context) {
        super(context);
    }
}
