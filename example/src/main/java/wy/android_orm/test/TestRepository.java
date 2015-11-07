package wy.android_orm.test;

import com.xydroid.dbutils.persistence.sqlite.query.annotation.Query;
import com.xydroid.dbutils.persistence.repository.CrudRepository;


public interface TestRepository extends CrudRepository<TestBean, Long> {
    @Query
    void call(String name, String Value);
}
