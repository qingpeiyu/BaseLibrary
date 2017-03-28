package com.cqmas.library;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 * Created by qingpeiyu on 2016/8/26 0012.
 */
public class BaseActivity extends RxAppCompatActivity {

    protected Context mContext;
    protected BaseApplication mApplication;
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mApplication = (BaseApplication) getApplication();
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        setActionHomeButtonAsUpEnabled();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 启用actionBar home 按钮作为返回按钮
     */
    public void setActionHomeButtonAsUpEnabled() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showProgressDialog(String message, boolean cancelAble) {
        if (isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(cancelAble);
            mProgressDialog.setTitle(null);
        }
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    protected void setProgressDialogText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null) {
                    mProgressDialog.setMessage(text);
                }
            }
        });
    }

    protected void showProgressDialog(int message, boolean cancelAble) {
        showProgressDialog(getResources().getString(message), cancelAble);
    }

    protected void showProgressDialog(String message) {
        showProgressDialog(message, true);
    }

    protected void showProgressDialog(int message) {
        showProgressDialog(message, true);
    }

    protected void showProgressDialog() {
        showProgressDialog("加载中，请稍候...", true);
    }

    protected void dismissProgressDialog() {
        if (isFinishing()) {
            return;
        }
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public void collapseSoftInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
