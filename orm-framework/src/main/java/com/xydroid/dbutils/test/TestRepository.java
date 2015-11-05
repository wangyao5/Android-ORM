package com.xydroid.dbutils.test;

import com.xydroid.dbutils.persistence.query.Query;
import com.xydroid.dbutils.persistence.repository.CrudRepository;


public interface TestRepository extends CrudRepository<TestBean, Long> {
    @Query
    void call(String name, String Value);
}
