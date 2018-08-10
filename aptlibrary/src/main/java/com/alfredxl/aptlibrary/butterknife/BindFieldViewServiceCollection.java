package com.alfredxl.aptlibrary.butterknife;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/8/10 11:48
 */
public class BindFieldViewServiceCollection {
    private Map<String, BindFieldViewCollection> map;

    public BindFieldViewServiceCollection(Map<String, BindFieldViewCollection> map) {
        this.map = map;
    }

    public void play(ProcessingEnvironment processingEnvironment) {
        // 生成方法
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get("com.alfredxl.aptdemo.butterknife", "IBind"))
                .addParameter(ClassName.get("android.app", "Activity"), "activity");
        // 生成方法体
        methodBuilder.addStatement("$T className = activity.getClass().getName()", String.class);
        // 方法体代码块
        CodeBlock.Builder caseBlock = CodeBlock.builder().beginControlFlow("switch (className)");
        for (Map.Entry<String, BindFieldViewCollection> item : map.entrySet()){
            // 注解所在的类
            TypeElement classTypeElement =item.getValue().getClassElement();
            caseBlock.add("case $S:\n", classTypeElement.getQualifiedName()).indent()
                    .addStatement("return new $T(activity)",
                            ClassName.get(ElementUtils.getPackageName(classTypeElement),
                                    classTypeElement.getSimpleName().toString() + "$$Bind")).unindent();
        }
        caseBlock.add("default:\n").indent().addStatement("return null").unindent();
        caseBlock.endControlFlow();
        methodBuilder.addCode(caseBlock.build());

        // 生成类
        TypeSpec finderClass = TypeSpec.classBuilder("BindFieldViewService")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get("com.alfredxl.aptdemo.butterknife", "IBindFieldViewService"))
                .addMethod(methodBuilder.build())
                .build();
        try {
            JavaFile.builder("com.alfredxl.aptdemo.butterknife", finderClass).build().writeTo(processingEnvironment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
