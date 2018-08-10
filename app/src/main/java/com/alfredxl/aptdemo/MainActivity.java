package com.alfredxl.aptdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfredxl.aptdemo.arouter.ARouter;
import com.alfredxl.aptdemo.butterknife.BindFieldViewUtil;
import com.alfredxl.aptnote.butterknife.BindFieldView;

public class MainActivity extends AppCompatActivity {
    @BindFieldView(id = R.id.textView)
    TextView mTextView;
    @BindFieldView(id = R.id.iamgeView)
    ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindFieldViewUtil.getInstance().bind(this);
        mTextView.setText("启动Activity2");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().startActivity("Activity2", MainActivity.this);
            }
        });
    }
}
