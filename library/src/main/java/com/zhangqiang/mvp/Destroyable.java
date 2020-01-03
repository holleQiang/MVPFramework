package com.zhangqiang.mvp;

import java.util.ArrayList;
import java.util.List;

public abstract class Destroyable {

    private final List<OnDestroyListener> onDestroyListeners = new ArrayList<>();

    interface OnDestroyListener {

        void onDestroy();
    }

    public abstract boolean isDestroyed();

    public void addOnDestroyListener(OnDestroyListener onDestroyListener) {
        if (onDestroyListeners.contains(onDestroyListener)) {
            return;
        }
        onDestroyListeners.add(onDestroyListener);
    }

    public void removeOnDestroyListener(OnDestroyListener onDestroyListener) {
        onDestroyListeners.remove(onDestroyListener);
    }

    private void notifyOnDestroy(){
        for (int i = onDestroyListeners.size() - 1; i >= 0; i--) {
            onDestroyListeners.get(i).onDestroy();
        }
    }

    public void destroy(){
        notifyOnDestroy();
    }
}
