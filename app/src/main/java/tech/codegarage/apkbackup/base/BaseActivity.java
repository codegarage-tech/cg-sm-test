package tech.codegarage.apkbackup.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tech.codegarage.apkbackup.interfaces.OnFragmentBackPressedListener;
import tech.codegarage.apkbackup.interfaces.OnFragmentResultListener;
import tech.codegarage.apkbackup.util.RuntimePermissionManager;

import static tech.codegarage.apkbackup.util.RuntimePermissionManager.REQUEST_CODE_PERMISSION;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Context mContext;
    public Bundle mSavedInstanceState;
    private String TAG = BaseActivity.class.getSimpleName();

    //Abstract declaration
    public abstract Context initActivityContext();

    public abstract int initActivityLayout();

    public abstract void initIntentData(Bundle savedInstanceState, Intent intent);

    public abstract void initActivityViews();

    public abstract void initActivityViewsData(Bundle savedInstanceState);

    public abstract void initActivityActions(Bundle savedInstanceState);

    public abstract void initActivityOnResult(int requestCode, int resultCode, Intent data);

    public abstract void initActivityBackPress();

    public abstract void initActivityPermissionResult(int requestCode, String permissions[], int[] grantResults);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initActivityLayout());

        mContext = initActivityContext();
        mSavedInstanceState = savedInstanceState;
        initIntentData(mSavedInstanceState, getIntent());
        initActivityViews();

        if (checkAndRequestPermissions()) {
            initActivityViewsData(mSavedInstanceState);
            initActivityActions(mSavedInstanceState);
        }
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mSavedInstanceState = savedInstanceState;
    }

    /**********************
     * Permission methods *
     **********************/
    private boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!RuntimePermissionManager.isAllPermissionsGranted(mContext)) {
                ArrayList<String> permissionNeeded = RuntimePermissionManager.getAllUnGrantedPermissions(mContext);
                for (int i = 0; i < permissionNeeded.size(); i++) {
                    Log.d(TAG, "Ungranted Permission: " + permissionNeeded.get(i));
                }
                ActivityCompat.requestPermissions(this, permissionNeeded.toArray(new String[permissionNeeded.size()]), REQUEST_CODE_PERMISSION);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        initActivityPermissionResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (RuntimePermissionManager.isAllPermissionsGranted(mContext, permissions)) {
                    initActivityViewsData(mSavedInstanceState);
                    initActivityActions(mSavedInstanceState);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initActivityOnResult(requestCode, resultCode, data);

        //send on activity result event to the fragment
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof OnFragmentResultListener) {
                    ((OnFragmentResultListener) fragment).onFragmentResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        initActivityBackPress();

        //send back press event to the fragment
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof OnFragmentBackPressedListener) {
                    ((OnFragmentBackPressedListener) fragment).onFragmentBackPressed();
                }
            }
        }
    }
}
