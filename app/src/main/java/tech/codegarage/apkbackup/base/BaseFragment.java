package tech.codegarage.apkbackup.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.codegarage.apkbackup.interfaces.OnFragmentBackPressedListener;
import tech.codegarage.apkbackup.interfaces.OnFragmentResultListener;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public abstract class BaseFragment extends Fragment implements OnFragmentBackPressedListener, OnFragmentResultListener {

    //Abstract declaration
    public abstract int initFragmentLayout();

    public abstract void initFragmentViews(View parentView);

    public abstract void initFragmentViewsData();

    public abstract void initFragmentActions();

    public abstract void initFragmentBackPress();

    public abstract void initFragmentOnResult(int requestCode, int resultCode, Intent data);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(initFragmentLayout(), container, false);

        initFragmentViews(rootView);
        initFragmentViewsData();
        initFragmentActions();

        return rootView;
    }

    @Override
    public void onFragmentBackPressed() {
        initFragmentBackPress();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        initFragmentOnResult(requestCode, resultCode, data);
    }
}


