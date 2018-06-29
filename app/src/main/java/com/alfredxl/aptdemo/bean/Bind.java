package com.alfredxl.aptdemo.bean;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/6/28 16:27
 */
public interface Bind<T> {
    Object getParam(T t, String paramName);
    void callMethod(T t, String methodName);
}
