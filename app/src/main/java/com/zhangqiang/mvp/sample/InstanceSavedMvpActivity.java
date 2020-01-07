package com.zhangqiang.mvp.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zhangqiang.mvp.PresenterProviders;

public class InstanceSavedMvpActivity extends AppCompatActivity implements MVPTestView {

    private TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_test);
        findViewById(R.id.bt_send_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                PresenterProviders.of(InstanceSavedMvpActivity.this).get(MVPTestPresenter.class,InstanceSavedMvpActivity.this).sendRequest();
            }
        });
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void setResult(String s) {
        tvResult.setText(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
