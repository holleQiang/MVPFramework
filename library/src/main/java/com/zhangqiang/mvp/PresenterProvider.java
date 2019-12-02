package com.zhangqiang.mvp;

import com.zhangqiang.lifecycle.MLifecycle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PresenterProvider {

    private final PresenterStore mPresenterStore;

    PresenterProvider(PresenterStore presenterStore) {
        this.mPresenterStore = presenterStore;
    }

    public <P extends BackgroundPresenter> P get(Class<P> pClass) {
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

    public <P extends BackgroundPresenter> P get(String key, Class<P> pClass, Factory<P> mFactory) {

        P target;
        BackgroundPresenter backgroundPresenter = mPresenterStore.get(key);
        if (pClass.isInstance(backgroundPresenter)) {
            target = (P) backgroundPresenter;
        } else {
            target = mFactory.create(pClass);
            if (target != null) {
                mPresenterStore.put(key, target);
            }
        }
        return target;
    }

    public interface Factory<P extends BackgroundPresenter> {

        P create(Class<? extends P> pClass);
    }

    private static String getPresenterKey(Class<? extends BackgroundPresenter> presenterClass) {
        String canonicalName = presenterClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return "presenter_key_" + canonicalName;
    }


    @SuppressWarnings("unchecked")
    public <V extends IView, P extends Presenter> P get(Class<P> pClass, final V view) {

        final P p = get(pClass);
        if (p.isCleared()) {
            return p;
        }
        IView attachedView = p.getAttachedView();
        if (attachedView == null || attachedView != view) {
            p.attachView(view);
            final MLifecycle mLifecycle = view.getMLifecycle();
            mLifecycle.registerObserver(new MLifecycle.Observer() {
                @Override
                public void onCreate() {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onResume() {

                }

                @Override
                public void onPause() {

                }

                @Override
                public void onStop() {

                }

                @Override
                public void onDestroy() {
                    p.detachView();
                    mLifecycle.unregisterObserver(this);
                }
            });
        }
        return p;
    }
}
