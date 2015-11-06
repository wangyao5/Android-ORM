package com.xydroid.dbutils.persistence.proxy;

import android.database.sqlite.SQLiteOpenHelper;

import com.xydroid.dbutils.persistence.repository.Repository;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

public class Proxy implements Repository, InvocationHandler {
    private Class targetClazz;
    private SQLiteOpenHelper mSQLiteOpenHelper;

    private Class entity;

    public Proxy(SQLiteOpenHelper helper, Class clazz) {
        mSQLiteOpenHelper = helper;
        targetClazz = clazz;
    }

    private void init(){

    }

    @Override
    public Object invoke(Object target, Method targetMethod, Object[] params)
            throws Throwable {
        Class methodClass = targetMethod.getDeclaringClass();
        Type[] gen = methodClass.getGenericInterfaces();
        for (Type type:gen){
            Class clazza = type.getClass();
        }
//        Type type = targetClazz.getGenericSuperclass();
        Type[] typeVariables = targetClazz.getGenericInterfaces();
//        analyzeTypeVariableParts(typeVariables);
//        Class a = typeVariables[0].getAnnotation(targetClazz);
//        String name = typeVariables[0].getName();
        Type[] testType= targetClazz.getGenericInterfaces();


        Field[] fields = targetClazz.getFields();

        Type[] classGen = targetClazz.getGenericInterfaces();
        Annotation[] methodAnotation = targetMethod.getAnnotations();
        String methodName = targetMethod.getName();
        Type returnType = targetMethod.getGenericReturnType();
        Class[] paramType = targetMethod.getParameterTypes();
        // Proxy示例实现了提供的所有接口，并继承自Proxy

        return null;
    }

    public Class entityClass(Class clazz) {
        return (Class) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public static void analyzeTypeVariableParts(TypeVariable<?>[] genericParameterTypes)
            throws ClassNotFoundException,NoSuchMethodException{
        TypeVariable tVariable=null;
        Type[]bounds=null;
        GenericDeclaration genericDeclaration =null;
        String typeVariableName =null;

        for(int i=0; i<genericParameterTypes.length; i++){

            tVariable=(TypeVariable)genericParameterTypes[i];

            bounds=tVariable.getBounds();
            genericDeclaration=tVariable.getGenericDeclaration();
            typeVariableName=tVariable.getName();

            System.out.println("类型变量是："+typeVariableName);
            System.out.println("父边界是："+ Arrays.asList(bounds));
            System.out.println("类型变量声明的位置："+genericDeclaration);
            System.out.print("finish");
        }
    }
}