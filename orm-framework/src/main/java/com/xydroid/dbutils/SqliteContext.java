package com.xydroid.dbutils;

import android.content.Context;

import com.xydroid.dbutils.env.RepositoryManager;
import com.xydroid.dbutils.persistence.SqliteEnv;
import com.xydroid.dbutils.persistence.repository.Repository;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class SqliteContext {
    private SqliteEnv mSqliteEnv;
    private Map<Class, Repository> repositoryMap = new HashMap<>();
    public SqliteContext(Context context){
        Annotation[] annotations = this.getClass().getAnnotations();
        for (Annotation annotation : annotations){
            if (annotation instanceof Configurable){
                Configurable config = (Configurable) annotation;
                mSqliteEnv = new SqliteEnv(context, config);
                break;
            }
        }
    }

    public Repository getRepository(Class clazz){
        return RepositoryManager.getInstance().getRepostory(mSqliteEnv, clazz);
    }

    @Override
    protected void finalize() throws Throwable {
        RepositoryManager.getInstance().remove(mSqliteEnv);
    }
}
