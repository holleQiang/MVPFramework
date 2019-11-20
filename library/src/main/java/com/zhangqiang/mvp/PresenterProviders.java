package com.zhangqiang.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class PresenterProviders {


    public static PresenterProvider of(FragmentActivity activity) {

        return new PresenterProvider(PresenterStores.ofActivity(activity));
    }


    public static PresenterProvider of(Fragment fragment) {
        return new PresenterProvider(PresenterStores.ofFragment(fragment));
    }


}
