package wy.android_orm;

import android.app.Application;
import com.xydroid.dbutils.AppSqliteConfiguration;
import com.xydroid.dbutils.persistence.repository.Repository;
import com.xydroid.dbutils.test.TestRepository;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppSqliteConfiguration configuration = new AppSqliteConfiguration(this);
        TestRepository testRepository = (TestRepository)configuration.getRepository(TestRepository.class);
//        testRepository.call("xxx","yy");
        testRepository.count();
    }
}
