package com.alfredxl.aptlibrary.butterknife;

import com.alfredxl.aptnote.butterknife.BindFieldView;
import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/8/9 17:20
 */
@AutoService(Processor.class)
public class BindFieldViewProcessor extends AbstractProcessor {


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindFieldView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Map<String, BindFieldViewCollection> map = new HashMap<>();

        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindFieldView.class)) {
            // 获取注解类所在的类元素
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            // 定义key
            String key = classElement.getQualifiedName().toString();
            if (!map.containsKey(key)) {
                map.put(key, new BindFieldViewCollection(classElement));
            }
            // 获取信息处理类
            BindFieldViewCollection mBindFieldViewCollection = map.get(key);
            // 添加被注解的成员变量元素
            mBindFieldViewCollection.addBindFieldView((VariableElement) element);
        }
        for (Map.Entry<String, BindFieldViewCollection> item : map.entrySet()) {
            // 按类处理注解
            item.getValue().play(processingEnv);
        }
        if (map.size() > 0) {
            // 添加统一管理类BindFieldViewService
            new BindFieldViewServiceCollection(map).play(processingEnv);
        }

        return true;
    }
}
