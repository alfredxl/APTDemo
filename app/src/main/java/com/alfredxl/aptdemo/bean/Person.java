package com.alfredxl.aptdemo.bean;


import android.util.Log;

import com.alfredxl.aptnote.AptField;
import com.alfredxl.aptnote.AptMethod;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/6/28 16:16
 */
public class Person {
    public Person(String name, int age){
       this.name = name;
       this.age = age;
    }

    @AptField(key = "name")
    String name;
    @AptField(key = "age")
    int age;

    @AptMethod(key = "toCallB")
    void toCallB() {
        Log.i("toCallB", this.toString());
    }
}
