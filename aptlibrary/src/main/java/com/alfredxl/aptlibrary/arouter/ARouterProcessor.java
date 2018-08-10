package com.alfredxl.aptlibrary.arouter;

import com.alfredxl.aptnote.arouter.ArouterPath;
import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/8/10 10:34
 */
@AutoService(Processor.class)
public class ARouterProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ArouterPath.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> mARouterSet = roundEnvironment.getElementsAnnotatedWith(ArouterPath.class);
        if (mARouterSet != null && mARouterSet.size() > 0) {
            ARouterCollection mARouterCollection = new ARouterCollection();
            for (Element element : mARouterSet) {
                if (element instanceof TypeElement) {
                    // 添加被注解的类元素
                    mARouterCollection.addTypeElement((TypeElement) element);
                }
            }
            try {
                mARouterCollection.play(processingEnv);
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return true;
    }


}
