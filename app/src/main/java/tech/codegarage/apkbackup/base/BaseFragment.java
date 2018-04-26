package tech.codegarage.apkbackup.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.codetail.widget.RevealFrameLayout;
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
    public RevealFrameLayout containerView;
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
        container.removeAllViews();
        View parentView = inflater.inflate(R.layout.fragment_base, container, false);
        containerView.removeAllViews();
//        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View fragmentView = LayoutInflater.from(getContext()).inflate(initFragmentLayout(),null, false);
        containerView.addView(fragmentView);

        initFragmentViews(fragmentView);
        initFragmentViewsData();
        initFragmentActions();

        return parentView;
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
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(), containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}


