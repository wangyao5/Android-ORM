package com.xydroid.dbutils.persistence.proxy;

import android.database.sqlite.SQLiteOpenHelper;

import com.xydroid.dbutils.persistence.repository.CrudRepository;
import com.xydroid.dbutils.persistence.repository.Repository;
import com.xydroid.dbutils.persistence.repository.RepositoryStub;
import com.xydroid.dbutils.persistence.sqlite.Entity;
import com.xydroid.dbutils.persistence.sqlite.SQLExecutor;
import com.xydroid.dbutils.persistence.sqlite.query.annotation.Count;
import com.xydroid.dbutils.persistence.sqlite.query.annotation.Param;
import com.xydroid.dbutils.persistence.sqlite.query.annotation.Query;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Proxy implements Repository, InvocationHandler {
    private SQLExecutor mExec;
    private CrudRepository mStub;
    private DefaultRepository mDefaultRepository;
    private Entity mEntity;

    public Proxy(SQLiteOpenHelper helper, Class repository) {
        Type[] interfaceType = repository.getGenericInterfaces();
        Type[] typeArgs = ((ParameterizedType) interfaceType[0]).getActualTypeArguments();
        Class entityClass = (Class) typeArgs[0];
        Class idClass = (Class) typeArgs[1];
        mEntity = new Entity(entityClass, idClass);
        initSQLExecutor(helper, repository);
        mDefaultRepository = new DefaultRepository(helper, mEntity);
    }

    private void initSQLExecutor(SQLiteOpenHelper helper, Class repository) {
        mExec = new SQLExecutor(helper);
        mExec.initTable(mEntity.createTableSql());
        mStub = new RepositoryStub(mExec, helper, mEntity);
    }

    @Override
    public Object invoke(Object target, Method targetMethod, Object[] params)
            throws Throwable {
        if (isCrudRepositoryMethod(targetMethod)){
            return targetMethod.invoke(mStub, params);
        }

        //default
        Annotation[] annotations = targetMethod.getAnnotations();
        if (annotations[0] instanceof Query){
                String sql = ((Query)annotations[0]).value();

                Annotation[][] parameterAnnotations = targetMethod.getParameterAnnotations();
                int paramSize = params.length;
                for (int i =0 ; i < paramSize; i ++){
                    String key = String.format(":%s", ((Param) parameterAnnotations[i][0]).value());
                    if (params[i] instanceof String){
                        sql = sql.replace(key, String.format("'%s'", params[i].toString()));
                    }else {
                        sql = sql.replace(key, params[i].toString());
                    }
                }
                if (sql.toUpperCase().startsWith("SELECT")){
                    if (targetMethod.getReturnType().getClass().isInstance(List.class)) {
                        //do query
                        return mDefaultRepository.execQuery(sql);
                    }else {
                        //no query
                        throw new IllegalArgumentException("SELECT must result List Object");
                    }
                }
                if (sql.toUpperCase().startsWith("UPDATE")){
                    mDefaultRepository.execSql(sql);
                }
                if (sql.toUpperCase().startsWith("DELETE")){
                    mDefaultRepository.execSql(sql);
                }
        }

        if (annotations[0] instanceof Count){
            String sql = ((Count)annotations[0]).value();

            Annotation[][] parameterAnnotations = targetMethod.getParameterAnnotations();
            int paramSize = params.length;
            for (int i =0 ; i < paramSize; i ++){
                String key = String.format(":%s", ((Param) parameterAnnotations[i][0]).value());
                if (params[i] instanceof String){
                    sql = sql.replace(key, String.format("'%s'", params[i].toString()));
                }else {
                    sql = sql.replace(key, params[i].toString());
                }
            }
            if (sql.toUpperCase().startsWith("SELECT")){
                if (targetMethod.getReturnType().getClass().isInstance(Number.class)) {
                    //do query
                    return mDefaultRepository.execCount(sql);
                }else {
                    //no query
                    throw new IllegalArgumentException("SELECT must result Number Object");
                }
            }
        }
        return new Object();
    }

    private boolean isCrudRepositoryMethod(Method targetMethod){
        Method[] methods = CrudRepository.class.getMethods();
        for (Method method : methods){
            if (method.equals(targetMethod)){
                return true;
            }
        }
        return false;
    }
}