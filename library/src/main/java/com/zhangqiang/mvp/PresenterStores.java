package com.zhangqiang.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.zhangqiang.holderfragment.HolderFragment;
import com.zhangqiang.holderfragment.SimpleLifecycleCallback;

class PresenterStores {

    private static final String TAG_KEY_PRESENTER_STORE = "tag_presenter_store_com.zhangqiang.mvp.PresenterStores";

    static PresenterStore ofActivity(FragmentActivity activity){
        return getFromHolderFragment(HolderFragment.forActivity(activity));
    }

    static PresenterStore ofFragment(Fragment fragment){
        return getFromHolderFragment(HolderFragment.forFragment(fragment));
    }

    private static PresenterStore getFromHolderFragment(final HolderFragment holderFragment){

        Object tag = holderFragment.getTag(TAG_KEY_PRESENTER_STORE);
        PresenterStore presenterStore;
        if (tag == null) {
            tag =  presenterStore = new PresenterStore();
            holderFragment.setTag(TAG_KEY_PRESENTER_STORE,tag);
            holderFragment.registerLifecycleCallback(new SimpleLifecycleCallback(){
                @Override
                public void onDestroy() {
                    super.onDestroy();
                    Object tag = holderFragment.getTag(TAG_KEY_PRESENTER_STORE);
                    if (tag instanceof PresenterStore) {
                        ((PresenterStore) tag).clear();
                    }
                }
            });
        }else if(tag instanceof PresenterStore){
            presenterStore = (PresenterStore) tag;
        }else {
            throw new IllegalArgumentException("unknown presenter for  " + PresenterStore.class);
        }
        return presenterStore;
    }
}
