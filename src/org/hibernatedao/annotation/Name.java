package org.hibernatedao.annotation;

import java.lang.annotation.*;

/**
 * Created by Nesson on 12/19/13.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface Name {
    boolean casesensitive() default true;
}
