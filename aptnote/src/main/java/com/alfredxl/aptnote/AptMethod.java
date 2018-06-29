package com.alfredxl.aptnote;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/6/28 16:18
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface AptMethod {
    String key();
}
