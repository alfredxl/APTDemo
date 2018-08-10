package com.alfredxl.aptlibrary.butterknife;

import javax.lang.model.element.TypeElement;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/8/10 14:28
 */
public class ElementUtils {
    public static String getPackageName(TypeElement classTypeElement) {
        // 注解所在的类的全民
        String className = classTypeElement.getQualifiedName().toString();
        // 注解类的简单名称
        String simpleName = classTypeElement.getSimpleName().toString();
        // 注解类所在包名
        return className.substring(0, className.length() - simpleName.length() - 1);
    }
}
