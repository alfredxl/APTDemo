package com.alfredxl.aptlibrary;

import com.alfredxl.aptnote.AptField;
import com.alfredxl.aptnote.AptMethod;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/8/9 16:01
 */
@AutoService(Processor.class)
public class AptUtilProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(AptField.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        List<Element> data = new ArrayList<>();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(AptField.class)) {
            data.add(element);
        }
        if (data.size() == 0) {
            return false;
        }
        // 生成方法
        MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("showToast")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .returns(TypeName.VOID)
                .addParameter(TypeName.get(String.class), "message", Modifier.FINAL)
                .addParameter(ClassName.get("android.content", "Context"), "context", Modifier.FINAL)
                .addStatement("$T.makeText(context, message, $T.LENGTH_SHORT).show()",
                        ClassName.get("android.widget", "Toast"), ClassName.get("android.widget", "Toast"));

        TypeSpec finderClass = TypeSpec.classBuilder("ToastUtil")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(injectMethodBuilder.build())
                .build();

        try {
            JavaFile.builder("com.alfredxl", finderClass).build().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
