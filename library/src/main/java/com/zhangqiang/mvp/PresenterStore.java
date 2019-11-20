package com.zhangqiang.mvp;

import java.util.HashMap;
import java.util.Map;


class PresenterStore {

    private final Map<String, Presenter> presenters = new HashMap<>();


    void put(String key, Presenter presenter) {
        Presenter oldPresenter = presenters.put(key, presenter);
        if (oldPresenter != null) {
            oldPresenter.detachView();
        }
    }

    Presenter get(String key) {
        return presenters.get(key);
    }


    void clear() {
        for (Presenter presenter : presenters.values()) {
            if (presenter != null) {
                presenter.detachView();
            }
        }
        presenters.clear();
    }


}
