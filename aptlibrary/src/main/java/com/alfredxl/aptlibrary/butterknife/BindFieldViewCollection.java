package com.alfredxl.aptlibrary.butterknife;

import com.alfredxl.aptnote.butterknife.BindFieldView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * <br> ClassName:   必须是处理同一个类的被注解的成员变量
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/8/9 17:28
 */
public class BindFieldViewCollection {
    /*** 类元素 ***/
    private TypeElement mClassElement;
    /*** 存放同一个类中的相同的成员变量 ***/
    private List<VariableElement> mFieldElements = new ArrayList<>();

    /**
     * <br> Description: 构造函数
     * <br> Author:      xwl
     * <br> Date:        2018/8/9 17:39
     *
     * @param mClassElement 被注解的成员变量所在的类元素
     */
    BindFieldViewCollection(TypeElement mClassElement) {
        this.mClassElement = mClassElement;
    }

    /**
     * <br> Description: 添加被注解的成员变量
     * <br> Author:      xwl
     * <br> Date:        2018/8/9 17:30
     *
     * @param FieldElement 注解成员变量节点
     */
    public void addBindFieldView(VariableElement FieldElement) {
        mFieldElements.add(FieldElement);
    }

    public TypeElement getClassElement() {
        return mClassElement;
    }

    public void play(ProcessingEnvironment processingEnvironment) {
        // 生成方法
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("init")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID);
        // 添加目标activity
        methodBuilder.addStatement("$T targetActivity = ($T)activity", mClassElement, mClassElement);
        for (VariableElement item : mFieldElements) {
            // 获取注解成员变量的注解
            BindFieldView mBindFieldView = item.getAnnotation(BindFieldView.class);
            // 获取控件ID
            int id = mBindFieldView.id();
            // 添加方法内容
            methodBuilder.addStatement("targetActivity.$N = targetActivity.findViewById($L)", item.getSimpleName(), id);
        }

        // 生成构造函数
        MethodSpec constructors = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("android.app", "Activity"), "activity")
                .addStatement("this.$N = $N", "activity", "activity")
                .build();

        // 生成成员变量
        FieldSpec fieldSpec = FieldSpec.builder(ClassName.get("android.app", "Activity"), "activity")
                .addModifiers(Modifier.PRIVATE)
                .build();

        // 生成类
        TypeSpec finderClass = TypeSpec.classBuilder(mClassElement.getSimpleName() + "$$Bind")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get("com.alfredxl.aptdemo.butterknife", "IBind"))
                .addField(fieldSpec)
                .addMethod(constructors)
                .addMethod(methodBuilder.build())
                .build();
        try {
            JavaFile.builder(ElementUtils.getPackageName(mClassElement), finderClass).build().writeTo(processingEnvironment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
