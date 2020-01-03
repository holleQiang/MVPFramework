package com.zhangqiang.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

public class FullLifecycleObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(@NonNull LifecycleOwner owner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onStart(@NonNull LifecycleOwner owner){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onResume(@NonNull LifecycleOwner owner){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(@NonNull LifecycleOwner owner){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(@NonNull LifecycleOwner owner){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(@NonNull LifecycleOwner owner) {
    }
}
