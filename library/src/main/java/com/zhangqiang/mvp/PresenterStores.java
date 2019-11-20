package com.zhangqiang.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.zhangqiang.holderfragment.HolderFragment;
import com.zhangqiang.holderfragment.SimpleLifecycleCallback;

class PresenterStores {

    private static final String TAG_KEY_PRESENTER_STORE = "tag_key_presenter_store" + PresenterStores.class;
    private static final String TAG_KEY_LIFECYCLE_CALLBACK = "tag_key_lifecycle";

    static PresenterStore ofActivity(FragmentActivity activity){
        return getFromHolderFragment(HolderFragment.forActivity(activity));
    }

    static PresenterStore ofFragment(Fragment fragment){
        return getFromHolderFragment(HolderFragment.forFragment(fragment));
    }

    private static PresenterStore getFromHolderFragment(final HolderFragment holderFragment){

        Object tagLifecycle = holderFragment.getTag(TAG_KEY_LIFECYCLE_CALLBACK);
        if (tagLifecycle == null) {
            HolderFragment.LifecycleCallback lifecycleCallback = new SimpleLifecycleCallback(){
                @Override
                public void onDestroy() {
                    super.onDestroy();
                    holderFragment.unregisterLifecycleCallback(this);
                    holderFragment.setTag(TAG_KEY_LIFECYCLE_CALLBACK,null);
                    Object tag = holderFragment.getTag(TAG_KEY_PRESENTER_STORE);
                    if (tag instanceof PresenterStore) {
                        ((PresenterStore) tag).clear();
                    }
                }
            };
            holderFragment.registerLifecycleCallback(lifecycleCallback);
            tagLifecycle = lifecycleCallback;
            holderFragment.setTag(TAG_KEY_LIFECYCLE_CALLBACK,tagLifecycle);
        }
        Object tag = holderFragment.getTag(TAG_KEY_PRESENTER_STORE);
        PresenterStore presenterStore;
        if (tag == null) {
            tag =  presenterStore = new PresenterStore();
            holderFragment.setTag(TAG_KEY_PRESENTER_STORE,tag);
        }else if(tag instanceof PresenterStore){
            presenterStore = (PresenterStore) tag;
        }else {
            throw new IllegalArgumentException("unknown presenter for  " + PresenterStore.class);
        }
        return presenterStore;
    }
}
