package com.xydroid.dbutils;

import com.xydroid.dbutils.persistence.repository.Repository;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configurable {
    boolean external() default false;
    String dbPath() default "";
    String dbName() default "wy.db";
    int version() default 1;
    Class<? extends Repository>[] repository() default {};
}
