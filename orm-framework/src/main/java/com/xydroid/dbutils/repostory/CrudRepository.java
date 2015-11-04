package com.xydroid.dbutils.repostory;

import java.io.Serializable;

public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> {

    /**
     * Saves a given entity. Use the returned instance for further operations as
     * the save operation might have changed the entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    T save(T entity);


    /**
     * Saves all given entities.
     *
     * @param entities
     * @return
     */
    Iterable<T> save(Iterable<? extends T> entities);


    /**
     * Retrives an entity by its primary key.
     *
     * @param id
     * @return the entity with the given primary key or {@code null} if none
     *         found
     * @throws IllegalArgumentException if primaryKey is {@code null}
     */
    T findOne(ID id);


    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id
     * @return true if an entity with the given id exists, alse otherwise
     * @throws IllegalArgumentException if primaryKey is {@code null}
     */
    boolean exists(ID id);


    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();


    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();


    /**
     * Deletes the entity with the given id.
     *
     * @param id
     */
    void delete(ID id);


    /**
     * Deletes a given entity.
     *
     * @param entity
     */
    void delete(T entity);


    /**
     * Deletes the given entities.
     *
     * @param entities
     */
    void delete(Iterable<? extends T> entities);


    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();
}
