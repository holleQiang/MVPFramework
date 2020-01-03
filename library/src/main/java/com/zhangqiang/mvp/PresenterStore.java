package com.zhangqiang.mvp;

import java.util.HashMap;
import java.util.Map;


public class PresenterStore {

    private final Map<String, Presenter> presenters = new HashMap<>();

    public void put(String key, Presenter backgroundPresenter) {
        Presenter oldPresenter = presenters.put(key, backgroundPresenter);
        if (oldPresenter != null) {
            oldPresenter.onClear();
        }
    }

    public Presenter get(String key) {
        return presenters.get(key);
    }


    public void clear() {
        for (Presenter presenter : presenters.values()) {
            presenter.onClear();
        }
        presenters.clear();
    }


}
