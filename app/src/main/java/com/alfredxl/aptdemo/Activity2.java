package com.alfredxl.aptdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alfredxl.aptdemo.butterknife.BindFieldViewUtil;
import com.alfredxl.aptnote.arouter.ArouterPath;
import com.alfredxl.aptnote.butterknife.BindFieldView;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/8/10 10:31
 */
@ArouterPath(path = "Activity2")
public class Activity2 extends AppCompatActivity {
    @BindFieldView(id = "R.id.textView")
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindFieldViewUtil.getInstance().bind(this);
        mTextView.setText("Activity2");
    }
}
