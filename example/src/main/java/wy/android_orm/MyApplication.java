package wy.android_orm;

import android.app.Application;
import wy.android_orm.test.TestRepository;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppSQLiteConfiguration configuration = new AppSQLiteConfiguration(this);
        TestRepository testRepository = (TestRepository)configuration.getRepository(TestRepository.class);
//        testRepository.call("xxx","yy");
        testRepository.count();
    }
}
