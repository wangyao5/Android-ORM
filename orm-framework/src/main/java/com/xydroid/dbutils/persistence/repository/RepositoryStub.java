package com.xydroid.dbutils.persistence.repository;

/**
 * Created by wangyao5 on 15/11/5.
 */
public class RepositoryStub implements Repository{
    private Class<? extends Repository> mStubClazz;
    public RepositoryStub(Class<? extends Repository> clazz){
        mStubClazz = clazz;
    }

    public Class<? extends Repository> getStubClazz() {
        return mStubClazz;
    }
}
