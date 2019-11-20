package com.zhangqiang.mvp.sample;

import android.os.AsyncTask;

import com.zhangqiang.mvp.Presenter;

public class MVPTestPresenter extends Presenter<MVPTestView> {

    public void sendRequest(){


        new AsyncTask<String,String,String>(){


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
            }
        }.execute();
    }
}
