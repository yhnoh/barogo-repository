package com.example.barogo.authorization;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorization {
    boolean required() default true;
}