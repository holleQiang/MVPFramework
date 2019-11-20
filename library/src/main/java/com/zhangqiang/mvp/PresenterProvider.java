package com.zhangqiang.mvp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PresenterProvider {

    private final PresenterStore mPresenterStore;

    public PresenterProvider(PresenterStore presenterStore) {
        this.mPresenterStore = presenterStore;
    }

    public <P extends Presenter> P get(Class<P> pClass) {
        return get(getPresenterStoreKey(pClass), pClass, new Factory<P>() {
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

    public <P extends Presenter> P get(String key, Class<P> pClass, Factory< P> mFactory) {

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

    public interface Factory<P extends Presenter> {

        P create(Class<? extends P> pClass);
    }

    private static String getPresenterStoreKey(Class<? extends Presenter> presenterClass) {
        String canonicalName = presenterClass.getCanonicalName();
        if (canonicalName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return "presenter_key_" + canonicalName;
    }


}
