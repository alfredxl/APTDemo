package com.alfredxl.aptdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.alfredxl.aptdemo.bean.Bind;
import com.alfredxl.aptdemo.bean.Person;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Person mPerson = new Person("张三", 29);
//        try {
//            Bind mBind = (Bind) Class.forName(mPerson.getClass().getName() + "$$Bind").newInstance();
//            Toast.makeText(this, (String)mBind.getParam(mPerson, "name"), Toast.LENGTH_SHORT).show();
//            mBind.callMethod(mPerson, "toCallB");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            Class mToastUtil = Class.forName("com.alfredxl.ToastUtil");
            Method showToast = mToastUtil.getMethod("showToast", String.class, Context.class);
            showToast.invoke(null, "测试APT", MainActivity.this);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
