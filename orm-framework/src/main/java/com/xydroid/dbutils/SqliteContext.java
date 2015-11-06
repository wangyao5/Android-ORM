package com.xydroid.dbutils;

import android.content.Context;
import com.xydroid.dbutils.env.RepositoryManager;
import com.xydroid.dbutils.persistence.SQLiteEnv;
import com.xydroid.dbutils.persistence.repository.Repository;
import java.lang.annotation.Annotation;

public class SQLiteContext {
    private SQLiteEnv mSQLiteEnv;
    private Context mContext;
    private RepositoryManager mRepositoryManager;
    public SQLiteContext(Context context){
        mContext = context;
        Annotation[] annotations = this.getClass().getAnnotations();
        for (Annotation annotation : annotations){
            if (annotation instanceof Configurable){
                Configurable config = (Configurable) annotation;
                mSQLiteEnv = new SQLiteEnv(config);
                break;
            }
        }
        initRepositoryManager();
    }

    public Repository getRepository(Class<? extends Repository> clazz){
        if (!containsRepository(clazz)){
            throw new IllegalArgumentException("SQLiteContext not have Repository named : " + clazz.getName());
        }
        return mRepositoryManager.getRepostory(clazz);
    }

    private void initRepositoryManager(){
        mRepositoryManager = new RepositoryManager(this);
    }

    private boolean containsRepository(Class<? extends Repository> clazz){
        return mSQLiteEnv.getRepositorys().contains(clazz);
    }

    public SQLiteEnv getSQLiteEnv() {
        return mSQLiteEnv;
    }

    public Context getContext() {
        return mContext;
    }
}
