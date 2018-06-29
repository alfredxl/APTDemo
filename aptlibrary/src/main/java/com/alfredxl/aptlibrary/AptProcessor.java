package com.alfredxl.aptlibrary;

import com.alfredxl.aptnote.AptField;
import com.alfredxl.aptnote.AptMethod;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/6/28 16:32
 */
@AutoService(Processor.class)
public class AptProcessor extends AbstractProcessor {

    /***文件相关的辅助类***/
    private Filer mFiler;
    /***元素相关的辅助类***/
    private Elements mElementUtils;
    /***日志相关的辅助类***/
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(AptField.class.getCanonicalName());
        types.add(AptMethod.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Map<String, FieldAndMethodProcessing> mAnnotatedClassMap = new HashMap<>();

        for(Element element : roundEnvironment.getElementsAnnotatedWith(AptField.class)){
            FieldAndMethodProcessing mFieldAndMethodProcessing = getAnnotatedClass(element, mAnnotatedClassMap);
            AptFieldInfo mAptFieldInfo = new AptFieldInfo(element);
            mFieldAndMethodProcessing.addAptFieldInfos(mAptFieldInfo);
        }
        for(Element element : roundEnvironment.getElementsAnnotatedWith(AptMethod.class)){
            FieldAndMethodProcessing mFieldAndMethodProcessing = getAnnotatedClass(element, mAnnotatedClassMap);
            AptMethodInfo mAptMethodInfo = new AptMethodInfo(element);
            mFieldAndMethodProcessing.addAptMethodInfos(mAptMethodInfo);
        }
        for (FieldAndMethodProcessing mFieldAndMethodProcessing : mAnnotatedClassMap.values()) {
            try {
                info("Generating file for %s", mFieldAndMethodProcessing.getClassElement().getQualifiedName().toString());
                mFieldAndMethodProcessing.generateFinder().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private FieldAndMethodProcessing getAnnotatedClass(Element element, Map<String, FieldAndMethodProcessing> mAnnotatedClassMap) {
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = classElement.getQualifiedName().toString();
        FieldAndMethodProcessing annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new FieldAndMethodProcessing(classElement, mElementUtils);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
