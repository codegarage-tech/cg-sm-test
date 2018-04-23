package tech.codegarage.apkbackup.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatDelegate;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class ApkBackupApp extends Application {

    private static Context mContext;
    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        //For using vector drawable
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //Initialize font
        initTypeface();
    }

    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);
    }

    public static Context getGlobalContext() {
        return mContext;
    }
}