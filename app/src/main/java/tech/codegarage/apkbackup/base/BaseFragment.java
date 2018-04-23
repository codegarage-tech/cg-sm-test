package tech.codegarage.apkbackup.base;

import android.content.Intent;
import android.support.v4.app.Fragment;

import tech.codegarage.apkbackup.interfaces.OnFragmentBackPressedListener;
import tech.codegarage.apkbackup.interfaces.OnFragmentResultListener;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public abstract class BaseFragment extends Fragment implements OnFragmentBackPressedListener, OnFragmentResultListener {

    //Abstract declaration
    public abstract void initFragmentBackPress();

    public abstract void initFragmentOnResult(int requestCode, int resultCode, Intent data);

    @Override
    public void onFragmentBackPressed() {
        initFragmentBackPress();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        initFragmentOnResult(requestCode, resultCode, data);
    }
}


