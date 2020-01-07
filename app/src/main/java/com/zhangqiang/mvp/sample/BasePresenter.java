package com.zhangqiang.mvp.sample;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.zhangqiang.mvp.FullLifecycleObserver;
import com.zhangqiang.mvp.Presenter;

public class BasePresenter<V extends BaseView> extends Presenter<V> {


    public void test(){
        V attachedView = getAttachedView();
        if (attachedView == null) {
            return;
        }
        attachedView.getLifecycle().addObserver(new FullLifecycleObserver(){
            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                super.onDestroy(owner);

            }
        });
    }
}
