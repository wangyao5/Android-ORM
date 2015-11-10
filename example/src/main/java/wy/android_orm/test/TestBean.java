package wy.android_orm.test;

import com.xydroid.dbutils.persistence.annotation.Column;
import com.xydroid.dbutils.persistence.annotation.Entity;
import com.xydroid.dbutils.persistence.annotation.Id;

@Entity(name = "test")
public class TestBean {
    @Id
    @Column(name = "_id")
    private long id;
    @Column(name = "name", unique = true, insertable = true, updatable = false)
    private String name;
    @Column(name = "value", nullable = true)
    private String value;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
