package wy.android_orm;

import android.content.Context;

import com.xydroid.dbutils.Configurable;
import com.xydroid.dbutils.SQLiteContext;
import wy.android_orm.test.TestRepository;

@Configurable(external = true, dbPath = "com.wy.test", dbName = "wy.db", repository = {TestRepository.class})
public class AppSQLiteConfiguration extends SQLiteContext {
    public AppSQLiteConfiguration(Context context) {
        super(context);
    }
}
