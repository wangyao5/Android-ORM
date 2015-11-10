package wy.android_orm;

import android.app.Application;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

import wy.android_orm.test.TestBean;
import wy.android_orm.test.TestRepository;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppSQLiteConfiguration configuration = new AppSQLiteConfiguration(this);
        TestRepository testRepository = (TestRepository)configuration.getRepository(TestRepository.class);
//        testRepository.call("xxx","yy");
//        testRepository.count();
        List<TestBean> list = new ArrayList<>();
        TestBean bean = new TestBean();
        bean.setName("wang");
        bean.setValue("yao");
        testRepository.save(bean);
        testRepository.save(list);
    }
}
