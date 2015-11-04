package com.xydroid.dbutils.persistence.annotation;

import com.xydroid.dbutils.persistence.annotation.type.TemporalType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Temporal {

    /** The type used in mapping java.util.Date or java.util.Calendar. */
    TemporalType value();
}
