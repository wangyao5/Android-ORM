package com.xydroid.dbutils.repostory;


import java.util.List;


public interface SqliteRepostory<T> {

    /*
     * (non-Javadoc)
     */
    List<T> findAll();


    /*
     * (non-Javadoc)
     */
    List<T> findAll(OrderBy orderBy);


    /*
     * (non-Javadoc)
     */
    List<T> save(Iterable<? extends T> entities);


    /**
     * Flushes all pending changes to the database.
     */
    void flush();


    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity
     * @return the saved entity
     */
    T saveAndFlush(T entity);


    /**
     * Deletes the given entities in a batch which means it will create a single
     * {@link Query}. Assume that we will clear the {@link EntityManager} after
     * the call.
     *
     * @param entities
     */
    void deleteInBatch(Iterable<T> entities);
}
