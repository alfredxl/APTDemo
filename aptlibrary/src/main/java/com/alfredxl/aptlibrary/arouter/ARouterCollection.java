package com.alfredxl.aptlibrary.arouter;

import com.alfredxl.aptnote.arouter.ArouterPath;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/8/10 10:36
 */
public class ARouterCollection {
    /*** 收集所有的被标注的类***/
    private List<TypeElement> mClassElements = new ArrayList<>();

    /**
     * <br> Description: 构造函数
     * <br> Author:      xwl
     * <br> Date:        2018/8/9 17:39
     */
    ARouterCollection() {
    }

    /**
     * <br> Description: 添加被注解的类元素
     * <br> Author:      xwl
     * <br> Date:        2018/8/9 17:30
     *
     * @param mTypeElement 被注解的类元素
     */
    public void addTypeElement(TypeElement mTypeElement) {
        mClassElements.add(mTypeElement);
    }

    public void play(ProcessingEnvironment processingEnvironment) throws ClassNotFoundException {
        // 生成方法
        MethodSpec method = MethodSpec.methodBuilder("getActivityClass")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(String.class, "path")
                .returns(Class.class)
                .addStatement("return map.get(path)")
                .build();

        // 生成构造函数
        MethodSpec.Builder constructors = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("map = new $T()", HashMap.class);
        for (TypeElement item : mClassElements) {
            // 获取注解成员变量的注解
            ArouterPath mArouterPath = item.getAnnotation(ArouterPath.class);
            // 获取控件ID
            String path = mArouterPath.path();
            // 添加方法内容
            constructors.addStatement("map.put($S, $T.class)", path, item);
        }

        // 生成成员变量
        FieldSpec fieldSpec = FieldSpec.builder(ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ClassName.get(Class.class)), "map")
                .addModifiers(Modifier.PRIVATE)
                .build();

        // 生成类
        TypeSpec finderClass = TypeSpec.classBuilder("ARouterService")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get("com.alfredxl.aptdemo.arouter", "IARouterService"))
                .addField(fieldSpec)
                .addMethod(constructors.build())
                .addMethod(method)
                .build();
        try {
            JavaFile.builder("com.alfredxl.aptdemo",
                    finderClass).build().writeTo(processingEnvironment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
