package com.alfredxl.aptlibrary;

import com.alfredxl.aptnote.AptField;
import com.alfredxl.aptnote.AptMethod;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/6/28 17:17
 */
public class AptMethodInfo {
    private ExecutableElement methodElement;
    private String key;
    public AptMethodInfo(Element element){
        if (element.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(
                    String.format("Only methods can be annotated with @%s", AptMethodInfo.class.getSimpleName()));
        }
        methodElement = (ExecutableElement) element;
        AptMethod mAptMethod = methodElement.getAnnotation(AptMethod.class);
        key = mAptMethod.key();
    }

    public ExecutableElement getMethodElement() {
        return methodElement;
    }

    public String key() {
        return key;
    }

    public Name getMethodName() {
        return methodElement.getSimpleName();
    }
}
