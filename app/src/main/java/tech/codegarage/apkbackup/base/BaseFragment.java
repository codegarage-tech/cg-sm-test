package tech.codegarage.apkbackup.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.codegarage.apkbackup.R;
import tech.codegarage.apkbackup.interfaces.OnFragmentBackPressedListener;
import tech.codegarage.apkbackup.interfaces.OnFragmentResultListener;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public abstract class BaseFragment extends Fragment implements OnFragmentBackPressedListener, OnFragmentResultListener, ScreenShotable {

    //Local variable
    public View rootView, containerView;
    public Bitmap bitmap;

    //Abstract declaration
    public abstract int initFragmentLayout();

    public abstract void initFragmentViews(View parentView);

    public abstract void initFragmentViewsData();

    public abstract void initFragmentActions();

    public abstract void initFragmentBackPress();

    public abstract void initFragmentOnResult(int requestCode, int resultCode, Intent data);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(initFragmentLayout(), container, false);
        } else {
            ((ViewGroup) rootView).removeAllViews();
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

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


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containerView = view.findViewById(R.id.container);
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap mBitmap = Bitmap.createBitmap(containerView.getWidth(), containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(mBitmap);
                containerView.draw(canvas);
                bitmap = mBitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}