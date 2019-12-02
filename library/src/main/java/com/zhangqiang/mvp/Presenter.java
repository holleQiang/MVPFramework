package com.zhangqiang.mvp;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Presenter<V extends IView> extends BackgroundPresenter {

    private V mAttachedView;
    private boolean cleared;

    @Nullable
    public V getAttachedView() {
        return mAttachedView;
    }

    public final void attachView(V view) {
        if (mAttachedView != null && mAttachedView != view) {
            detachView();
        }
        if (view == null) {
            throw new IllegalArgumentException("cannot attach a null view");
        }
        mAttachedView = view;
        onViewAttached(view);
    }

    protected void onViewAttached(@NonNull V view) {

    }

    public final void detachView() {
        final V detachedView = mAttachedView;
        mAttachedView = null;
        onViewDetached(detachedView);
    }


    protected void onViewDetached(@NonNull V view) {

    }

    @CallSuper
    @Override
    protected void onClear() {
        super.onClear();
        cleared = true;
    }

    public boolean isCleared() {
        return cleared;
    }
}
