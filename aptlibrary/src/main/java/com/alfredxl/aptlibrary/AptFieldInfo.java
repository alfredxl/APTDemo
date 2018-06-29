package com.alfredxl.aptlibrary;

import com.alfredxl.aptnote.AptField;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/6/28 17:17
 */
public class AptFieldInfo {
    private VariableElement mFieldElement;
    private String key;
    public AptFieldInfo(Element element){
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(
                    String.format("Only fields can be annotated with @%s", AptField.class.getSimpleName()));
        }
        mFieldElement = (VariableElement) element;
        AptField mAptField = mFieldElement.getAnnotation(AptField.class);
        key = mAptField.key();
    }

    public TypeMirror getFieldType() {
        return mFieldElement.asType();
    }

    public VariableElement getFieldElement() {
        return mFieldElement;
    }

    public String key() {
        return key;
    }

    public Name getFieldName() {
        return mFieldElement.getSimpleName();
    }
}
