package tech.codegarage.apkbackup.customizableactionbardrawertoggle;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * DrawerImageState
 *
 * @author Vyacheslav Shmakin
 * @version 18.04.2017
 */
@IntDef({DrawerImageState.TOGGLE_DRAWER_DEFAULT, DrawerImageState.TOGGLE_DRAWER_NORMAL, DrawerImageState.TOGGLE_DRAWER_MIRRORED})
@Retention(RetentionPolicy.SOURCE)
public @interface DrawerImageState {
    int TOGGLE_DRAWER_DEFAULT = 0;
    int TOGGLE_DRAWER_NORMAL = 1;
    int TOGGLE_DRAWER_MIRRORED = 2;
}
