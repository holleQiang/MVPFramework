package com.zhangqiang.mvp;

import java.util.HashMap;
import java.util.Map;


class PresenterStore {

    private final Map<String, BackgroundPresenter> presenters = new HashMap<>();


    void put(String key, BackgroundPresenter backgroundPresenter) {
        BackgroundPresenter oldBackgroundPresenter = presenters.put(key, backgroundPresenter);
        if (oldBackgroundPresenter != null) {
            oldBackgroundPresenter.onClear();
        }
    }

    BackgroundPresenter get(String key) {
        return presenters.get(key);
    }


    void clear() {
        for (BackgroundPresenter backgroundPresenter : presenters.values()) {
            backgroundPresenter.onClear();
        }
        presenters.clear();
    }


}
