package com.cqmas.library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基类Fragment，程序中所有Fragment必需继承此Fragment。
 */
public class BaseFragment extends Fragment {

    protected BaseApplication mApplication;
    protected Context mContext;
    protected Activity mActivity;
    protected ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getActivity().getApplication();
        mContext = getActivity();
    }

    protected void showProgressDialog(String message, boolean cancelAble) {
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

    protected void showProgressDialog(int message, boolean cancelAble) {
        showProgressDialog(getString(message), cancelAble);
    }

    protected void showProgressDialog(String message) {
        showProgressDialog(message, true);
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 返回键点击
     *
     * @return
     */
    public boolean onBackPressed() {
        return false;
    }
}
