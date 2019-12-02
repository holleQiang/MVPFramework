package com.zhangqiang.mvp.sample;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zhangqiang.activitystart.ActivityStartHelper;
import com.zhangqiang.lifecycle.MLifecycle;
import com.zhangqiang.lifecycle.MLifecycleProvider;
import com.zhangqiang.mvp.Presenter;
import com.zhangqiang.mvp.PresenterProviders;
import com.zhangqiang.mvp.IView;
import com.zhangqiang.permissionrequest.PermissionRequestHelper;

public class MainActivity extends AppCompatActivity implements IView{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        for (int i = 0; i < 1000; i++) {
            PresenterA presenterA = PresenterProviders.of(this).get(PresenterA.class,this);
            IView view1 = presenterA.getAttachedView();
//            Log.i("Test", i+"========" + presenterA.hashCode() + "=======" + view1.hashCode());
        }

//        testPermissionRequest();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testActivityStartHelper();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPermissionRequest();
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),InstanceSavedMvpActivity.class));
            }
        });
    }

    private void testPermissionRequest() {

        PermissionRequestHelper.requestPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionRequestHelper.Callback() {
            @Override
            public void onPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
                Log.i("Test", "=============" + permissions[0]);
            }
        });
    }

    private void testActivityStartHelper() {
        for (int i = 0; i < 5; i++) {
            ActivityStartHelper.startActivityForResult(this,
                    new Intent(this, OpenedActivity.class),
                    new ActivityStartHelper.Callback() {
                        @Override
                        public void onActivityResult(int resultCode, Intent data) {
                            Log.i("Test", "==========" + resultCode);
                        }
                    });
        }

    }

    @Override
    public MLifecycle getMLifecycle() {
        return MLifecycleProvider.get(this);
    }


    private static class PresenterA extends Presenter<IView> {


        public PresenterA() {
        }

        @Override
        protected void onViewDetached(@NonNull IView view) {
            super.onViewDetached(view);
            Log.i("Test","=======onViewDetached======");
        }

        @Override
        protected void onViewAttached(@NonNull IView view) {
            super.onViewAttached(view);
            Log.i("Test","=====onViewAttached========");
        }
    }
}
