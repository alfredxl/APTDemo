package com.alfredxl.aptdemo.bean;

import android.util.Log;

import com.alfredxl.aptnote.AptField;
import com.alfredxl.aptnote.AptMethod;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/6/28 18:06
 */
public class Sun extends Person {

    @AptField(key = "address")
    String address;

    public Sun(String name, int age) {
        super(name, age);
    }

    @AptMethod(key = "toSayGood")
    void toSayGood() {
        Log.i("toSayGood", this.toString());
    }

}
