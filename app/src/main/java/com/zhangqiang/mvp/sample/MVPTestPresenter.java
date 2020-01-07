package com.zhangqiang.mvp.sample;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.zhangqiang.mvp.FullLifecycleObserver;
import com.zhangqiang.mvp.Presenter;

public class MVPTestPresenter extends BasePresenter<MVPTestView> {

    public void sendRequest(){

        final AsyncTask<String, String, String> asyncTask = new AsyncTask<String, String, String>() {

            final FullLifecycleObserver observer = new FullLifecycleObserver() {
                @Override
                public void onDestroy(@NonNull LifecycleOwner owner) {
                    super.onDestroy(owner);
                    cancel(true);
                }
            };

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                final Lifecycle lifecycle = getAttachedView().getLifecycle();

                lifecycle.addObserver(observer);
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "success";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                MVPTestView mvpTestView = getAttachedView();
                if (mvpTestView != null) {
                    mvpTestView.setResult(s);
                }
                getAttachedView().getLifecycle().removeObserver(observer);
            }
        };
        asyncTask.execute();




    }
}
