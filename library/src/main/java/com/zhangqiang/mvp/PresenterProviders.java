package com.zhangqiang.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class PresenterProviders {


    public static PresenterProvider of(FragmentActivity activity) {

        return new PresenterProvider(PresenterStores.ofActivity(activity));
    }


    public static PresenterProvider of(Fragment fragment) {
        return new PresenterProvider(PresenterStores.ofFragment(fragment));
    }


    public static PresenterProvider of(PresenterStore presenterStore) {
        return new PresenterProvider(presenterStore);
    }

    public static PresenterProvider of(View view){
        Object tag = view.getTag(R.id.tag_key_presenter_store);
        PresenterStore presenterStore;
        if (tag instanceof PresenterStore) {
             presenterStore = (PresenterStore) tag;
        }else {
            presenterStore = new PresenterStore();
            view.setTag(R.id.tag_key_presenter_store,presenterStore);
        }
        return new PresenterProvider(presenterStore);
    }
}
