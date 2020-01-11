package com.zhangqiang.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PresenterProvider {

    private static final Map<LifecycleOwner, LifecycleObserver> mLifecycleMap = new HashMap<>();
    private static final Map<DestroyableOwner, OnDestroyListener> mDestroyableMap = new HashMap<>();

    private final PresenterStore mPresenterStore;

    PresenterProvider(PresenterStore presenterStore) {
        this.mPresenterStore = presenterStore;
    }

    @SuppressWarnings("unchecked")
    public <P extends Presenter> P get(String key, Class<P> pClass, Factory<P> mFactory) {

        if (!isMainThread()) {
            throw new IllegalThreadStateException("cannot run within a not main thread" + Thread.currentThread().getName());
        }
        P target;
        Presenter presenter = mPresenterStore.get(key);
        if (pClass.isInstance(presenter)) {
            target = (P) presenter;
        } else {
            target = mFactory.create(pClass);
            if (target != null) {
                mPresenterStore.put(key, target);
            }
        }
        return target;
    }

    public <P extends Presenter> P get(Class<P> pClass) {
        return get(getPresenterKey(pClass), pClass, new Factory<P>() {
            @Override
            public P create(Class<? extends P> pClass) {
                try {
                    Constructor<? extends P> constructor = pClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    return constructor.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }


    public interface Factory<P extends Presenter> {

        P create(Class<? extends P> pClass);
    }

    private static String getPresenterKey(Class<? extends Presenter> presenterClass) {
        String canonicalName = presenterClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be Presenter");
        }
        return "presenter_key_" + canonicalName;
    }


    @SuppressWarnings("unchecked")
    public <V extends IView & LifecycleOwner, P extends Presenter> P get(Class<P> pClass, final V view) {

        final Lifecycle mLifecycle = view.getLifecycle();
        Lifecycle.State currentState = mLifecycle.getCurrentState();
        if (currentState == Lifecycle.State.DESTROYED) {
            throw new RuntimeException("view is destroyed");
        }
        final P p = get(pClass);
        IView attachedView = p.getAttachedView();
        if (attachedView == null || attachedView != view) {
            if (attachedView instanceof LifecycleOwner) {
                LifecycleOwner lifecycleOwner = (LifecycleOwner) attachedView;
                LifecycleObserver observer = mLifecycleMap.get(lifecycleOwner);
                if (observer != null) {
                    lifecycleOwner.getLifecycle().removeObserver(observer);
                }
            }
            p.attachView(view);
            FullLifecycleObserver observer = new FullLifecycleObserver() {

                @Override
                public void onDestroy(@NonNull LifecycleOwner owner) {
                    super.onDestroy(owner);
                    p.detachView();
                    owner.getLifecycle().removeObserver(this);
                    mLifecycleMap.remove(owner);
                }
            };
            mLifecycle.addObserver(observer);
            mLifecycleMap.put(view,observer);
        }
        return p;
    }


    @SuppressWarnings("unchecked")
    public <V extends IView & DestroyableOwner, P extends Presenter> P get2(Class<P> pClass, final V view) {

        Destroyable destroyable = view.getDestroyable();
        if (destroyable.isDestroyed()) {
            throw new RuntimeException("view is destroyed");
        }
        final P p = get(pClass);
        IView attachedView = p.getAttachedView();
        if (attachedView == null || attachedView != view) {
            if (attachedView instanceof DestroyableOwner) {
                DestroyableOwner destroyableOwner = (DestroyableOwner) attachedView;
                OnDestroyListener destroyListener = mDestroyableMap.get(destroyableOwner);
                if (destroyListener != null) {
                    destroyableOwner.getDestroyable().removeOnDestroyListener(destroyListener);
                }
            }
            p.attachView(view);
            OnDestroyListener onDestroyListener = new OnDestroyListener() {
                @Override
                public void onDestroy() {
                    p.detachView();
                    view.getDestroyable().removeOnDestroyListener(this);
                    mDestroyableMap.remove(view);
                }
            };
            view.getDestroyable().addOnDestroyListener(onDestroyListener);
            mDestroyableMap.put(view,onDestroyListener);
        }
        return p;
    }


    private static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }



}
