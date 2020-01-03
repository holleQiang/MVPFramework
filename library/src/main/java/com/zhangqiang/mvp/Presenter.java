package com.zhangqiang.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Presenter<V extends IView> {

    private V mAttachedView;

    @Nullable
    public V getAttachedView() {
        return mAttachedView;
    }

    public final void attachView(V view) {
        if (view == null) {
            throw new IllegalArgumentException("cannot attach a null view");
        }
        final V oldView = mAttachedView;
        if (oldView != null && oldView != view) {
            detachView();
        }
        if (mAttachedView == null) {
            mAttachedView = view;
            onViewAttached(view);
        }
    }

    protected void onViewAttached(@NonNull V view) {

    }

    public final void detachView() {
        if (mAttachedView != null) {
            final V detachedView = mAttachedView;
            mAttachedView = null;
            onViewDetached(detachedView);
        }
    }


    protected void onViewDetached(@NonNull V view) {

    }


    protected void onClear() {
        detachView();
    }
}
