package com.softwarevax.returnresult.config;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreWrap {
}
