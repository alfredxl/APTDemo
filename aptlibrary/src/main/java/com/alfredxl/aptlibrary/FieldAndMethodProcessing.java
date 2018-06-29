package com.alfredxl.aptlibrary;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/6/28 17:15
 */
public class FieldAndMethodProcessing {

    private List<AptFieldInfo> mAptFieldInfoList;
    private List<AptMethodInfo> mAptMethodInfoList;
    private TypeElement mClassElement;
    private Elements mElementUtils;

    private static final ClassName STRING =  ClassName.get("java.lang", "String");
    private static final ClassName BIND = ClassName.get("com.alfredxl.aptdemo.bean", "Bind");

    public FieldAndMethodProcessing(TypeElement classElement, Elements elementUtils) {
        mAptFieldInfoList = new ArrayList<>();
        mAptMethodInfoList = new ArrayList<>();
        this.mClassElement = classElement;
        this.mElementUtils = elementUtils;
    }

    public TypeElement getClassElement() {
        return mClassElement;
    }

    public void addAptFieldInfos(AptFieldInfo mAptFieldInfo) {
        mAptFieldInfoList.add(mAptFieldInfo);
    }

    public void addAptMethodInfos(AptMethodInfo mAptMethodInfo) {
        mAptMethodInfoList.add(mAptMethodInfo);
    }

    public JavaFile generateFinder(){
        MethodSpec.Builder injectFieldBuilder = MethodSpec.methodBuilder("getParam")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(TypeName.OBJECT)
                .addParameter(TypeName.get(mClassElement.asType()), "t", Modifier.FINAL)
                .addParameter(STRING, "paramName");
        for (int i = 0; i < mAptFieldInfoList.size(); i++) {
            AptFieldInfo mAptFieldInfo = mAptFieldInfoList.get(i);
            if (i == 0) {
                injectFieldBuilder.beginControlFlow("if(paramName.equals($S))", mAptFieldInfo.key());
            } else {
                injectFieldBuilder.beginControlFlow("else if(paramName.equals($S))", mAptFieldInfo.key());
            }
            injectFieldBuilder.addStatement("return t.$N", mAptFieldInfo.getFieldName());
            injectFieldBuilder.endControlFlow();
        }
        injectFieldBuilder.addStatement("return null");


        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("callMethod")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(TypeName.VOID)
                .addParameter(TypeName.get(mClassElement.asType()), "t", Modifier.FINAL)
                .addParameter(STRING, "methodName");
        for (int i = 0; i < mAptMethodInfoList.size(); i++) {
            AptMethodInfo mAptMethodInfo = mAptMethodInfoList.get(i);
            if (i == 0) {
                injectMethodBuilder.beginControlFlow("if(methodName.equals($S))", mAptMethodInfo.key());
            } else {
                injectMethodBuilder.beginControlFlow("else if(methodName.equals($S))", mAptMethodInfo.key());
            }
            injectMethodBuilder.addStatement("t.$N()", mAptMethodInfo.getMethodName());
            injectMethodBuilder.endControlFlow();
        }

        TypeSpec finderClass = TypeSpec.classBuilder(mClassElement.getSimpleName() + "$$Bind")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(BIND, TypeName.get(mClassElement.asType())))
                .addMethod(injectFieldBuilder.build())
                .addMethod(injectMethodBuilder.build())
                .build();

        String packageName = mElementUtils.getPackageOf(mClassElement).getQualifiedName().toString();


        return JavaFile.builder(packageName, finderClass).build();
    }
}
