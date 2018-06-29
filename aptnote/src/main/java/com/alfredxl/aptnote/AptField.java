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
 * <br> Date:        2018/6/28 16:17
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface AptField {
    String key();
}