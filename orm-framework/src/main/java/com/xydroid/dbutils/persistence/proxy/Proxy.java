package com.xydroid.dbutils.persistence.proxy;

import android.database.sqlite.SQLiteOpenHelper;
import com.xydroid.dbutils.persistence.repository.CrudRepository;
import com.xydroid.dbutils.persistence.repository.Repository;
import com.xydroid.dbutils.persistence.sqlite.SQLExecutor;
import com.xydroid.dbutils.persistence.sqlite.query.CursorIterable;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Proxy implements Repository, InvocationHandler {
    private SQLExecutor mExec;
    private static Class mEntity;

    public Proxy(SQLiteOpenHelper helper, Class repository) {
        initSQLExecutor(helper, repository);
        mExec.createTable();
    }

    private void initSQLExecutor(SQLiteOpenHelper helper, Class repository){
        Type[] interfaceType = repository.getGenericInterfaces();
        Type[] typeArgs = ((ParameterizedType)interfaceType[0]).getActualTypeArguments();
        Class entityClass = (Class)typeArgs[0];
        Class idClass = (Class)typeArgs[1];
        mExec = new SQLExecutor(helper, entityClass, idClass);
        mExec.createTable();
    }

    @Override
    public Object invoke(Object target, Method targetMethod, Object[] params)
            throws Throwable {
        Class methodClass = targetMethod.getDeclaringClass();
        mExec.save(params[0]);
        Type[] gen = methodClass.getGenericInterfaces();
        for (Type type:gen){
            Class clazza = type.getClass();
        }

        return null;
    }

}