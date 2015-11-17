package com.xydroid.dbutils.persistence.repository;

import android.content.ContentValues;

import com.xydroid.dbutils.persistence.sqlite.Entity;
import com.xydroid.dbutils.persistence.sqlite.SQLExecutor;
import com.xydroid.dbutils.persistence.sqlite.query.CursorIterable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepositoryStub implements CrudRepository {

    private SQLExecutor mExecutor;
    private Entity mEntity;
    private String table;

    public RepositoryStub(SQLExecutor executor, Entity entity) {
        mExecutor = executor;
        mEntity = entity;
        table = mEntity.getTableName();
    }

    @Override
    public void save(Object entity) {
        mExecutor.execSave(table, mEntity.convertToCV(entity));
    }

    @Override
    public void save(Iterable entities) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        for (Object entity : entities){
            contentValuesList.add(mEntity.convertToCV(entity));
        }
        mExecutor.execSave(table, contentValuesList);
    }

    @Override
    public Object findOne(Serializable serializable) {
        return mExecutor.findById(table, mEntity.getIdColumnName(), serializable);
    }

    @Override
    public boolean exists(Serializable serializable) {
        if (null != findOne(serializable)) return true;
        return false;
    }

    @Override
    public CursorIterable findAll() {
        return null;
    }

    @Override
    public long count() {
        return mExecutor.count(table);
    }

    @Override
    public void delete(Serializable serializable) {
        mExecutor.deleteById(table, mEntity.getIdColumnName(), serializable);
    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public void delete(Iterable entities) {

    }

    @Override
    public void deleteAll() {

    }
}
