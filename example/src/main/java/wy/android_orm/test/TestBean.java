package wy.android_orm.test;

import com.xydroid.dbutils.persistence.annotation.Entity;
import com.xydroid.dbutils.persistence.annotation.Id;

@Entity(name = "test")
public class TestBean {
    @Id(column = "_id")
    private long id;
}
