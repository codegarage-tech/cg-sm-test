package tech.codegarage.apkbackup.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.reversecoder.library.util.AllSettingsManager;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import tech.codegarage.apkbackup.R;
import tech.codegarage.apkbackup.base.BaseActivity;
import tech.codegarage.apkbackup.base.BaseFragment;
import tech.codegarage.apkbackup.customizableactionbardrawertoggle.ActionBarDrawerToggle;
import tech.codegarage.apkbackup.fragment.AboutFragment;
import tech.codegarage.apkbackup.fragment.BackupFragment;
import tech.codegarage.apkbackup.fragment.HomeFragment;
import tech.codegarage.apkbackup.fragment.SettingsFragment;
import tech.codegarage.apkbackup.util.FragmentUtilsManager;
import yalantis.com.sidemenu.enumeration.MenuType;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

import static tech.codegarage.apkbackup.util.AllConstants.TEXT_KEY;
import static tech.codegarage.apkbackup.util.AppUtil.getStatusBarHeight;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class HomeActivity extends BaseActivity {

    //Side menu
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private BaseFragment contentFragment;
    private ViewAnimator viewAnimator;
    private LinearLayout leftDrawer;
    private View viewContentFrame, viewContentOverlay;

    //Jelly toolbar
    private JellyToolbar toolbar;
    private AppCompatEditText editText;
    private JellyListener jellyListener;

//    @Override
//    public String initActivityTag() {
//        return HomeActivity.class.getSimpleName();
//    }

    @Override
    public int initActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initIntentData(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    public void initActivityViews() {
        toolbar = (JellyToolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftDrawer = (LinearLayout) findViewById(R.id.left_drawer);
        viewContentFrame = findViewById(R.id.content_frame);
        viewContentOverlay = findViewById(R.id.content_overlay);
    }

    @Override
    public void initActivityViewsData(Bundle savedInstanceState) {
        //set default fragment while launching the screen
        if (savedInstanceState == null) {
            contentFragment = setFragment(MenuType.HOME);
            goFragmentScreen(MenuType.HOME.getValue(), contentFragment);
        }

        setActionBar();
        setMenuList();
        setDrawer();

        selectMenuItem(MenuType.HOME.getValue());
    }

    @Override
    public void initActivityActions(Bundle savedInstanceState) {
        leftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public void initActivityOnResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void initActivityBackPress() {
        if (isDrawerOpen()) {
            drawerLayout.closeDrawers();
        } else if (toolbar.isExpanded()) {
            toolbar.collapse();
        } else {
            finish();
        }
    }

    @Override
    public void initActivityPermissionResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    private boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    private BaseFragment setFragment(MenuType menuType) {
        switch (menuType) {
            case HOME:
                contentFragment = new HomeFragment();
                break;
            case BACKUP:
                contentFragment = new BackupFragment();
                break;
            case ABOUT:
                contentFragment = new AboutFragment();
                break;
            case SETTINGS:
                contentFragment = new SettingsFragment();
                break;
        }
        return contentFragment;
    }

    private void setDrawer() {
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        leftDrawer.setPadding(0, getStatusBarHeight(getActivity()), 0, 0);
        viewAnimator = new ViewAnimator<>(getActivity(), list, contentFragment, drawerLayout, new ViewAnimator.ViewAnimatorListener() {

            @Override
            public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
                switch (MenuType.fromValue(slideMenuItem.getName())) {
//                    case CLOSE:
//                        return screenShotable;
                    default:
                        return replaceFragment(slideMenuItem, screenShotable, position);
                }
            }

            @Override
            public void disableHomeButton() {
                getSupportActionBar().setHomeButtonEnabled(false);

            }

            @Override
            public void enableHomeButton() {
                getSupportActionBar().setHomeButtonEnabled(true);
                drawerLayout.closeDrawers();

            }

            @Override
            public void addViewToContainer(View view) {
//                LinearLayout subLayout = new LinearLayout(HomeActivity.this);
//                subLayout.setOrientation(LinearLayout.VERTICAL);
//                LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
//                subLayout.addView(view, subParams);
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                linearLayout.addView(subLayout, params);

                leftDrawer.addView(view);
            }
        });
    }

    private void setMenuList() {
        SlideMenuItem menuItem;
        for (int i = 0; i < MenuType.values().length; i++) {
            menuItem = new SlideMenuItem(MenuType.values()[i].getValue(), MenuType.values()[i].getImageResourceId());
            list.add(menuItem);
        }
    }

    private void setActionBar() {
        jellyListener = new JellyListener() {
            @Override
            public void onCancelIconClicked() {
                if (TextUtils.isEmpty(editText.getText())) {
                    toolbar.collapse();
                } else {
                    editText.getText().clear();
                }
            }
        };
        toolbar.getToolbar().setNavigationIcon(R.drawable.ic_menu);
        toolbar.setJellyListener(jellyListener);
        toolbar.getToolbar().setPadding(0, getStatusBarHeight(getActivity()), 0, 0);

        editText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.toolbar_edittext, null);
        editText.setBackgroundResource(R.color.colorTransparent);
        toolbar.setContentView(editText);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        toolbar.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAnimator.showMenuContent();
            }
        });

        setSupportActionBar(toolbar.getToolbar());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                leftDrawer.removeAllViews();
                leftDrawer.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && leftDrawer.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);

        // Disables toggle animation
//        drawerToggle.setDrawerSlideAnimationEnabled(false);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

//         switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(TEXT_KEY, editText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editText.setText(savedInstanceState.getString(TEXT_KEY));
        editText.setSelection(editText.getText().length());
    }

    private ScreenShotable replaceFragment(Resourceble slideMenuItem, ScreenShotable screenShotable, int topPosition) {
        int finalRadius = Math.max(viewContentFrame.getWidth(), viewContentFrame.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(viewContentFrame, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        viewContentOverlay.setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        switch (MenuType.fromValue(slideMenuItem.getName())) {
            case HOME:
                contentFragment = setFragment(MenuType.HOME);
                break;
            case BACKUP:
                contentFragment = setFragment(MenuType.BACKUP);
                break;
            case ABOUT:
                contentFragment = setFragment(MenuType.ABOUT);
                break;
            case SETTINGS:
                contentFragment = setFragment(MenuType.SETTINGS);
                break;
        }
        if (MenuType.fromValue(slideMenuItem.getName()) != MenuType.RATE) {
            goFragmentScreen(slideMenuItem.getName(), contentFragment);
        }
        return contentFragment;
    }

    private void selectMenuItem(String tag) {
        if (!AllSettingsManager.isNullOrEmpty(tag)) {
            View menuItem = viewAnimator.getMenuItemView(tag);
            if (menuItem != null) {
                menuItem.setBackgroundResource(R.drawable.selector_menu_item_selected);
            }
        }
    }

    private void goFragmentScreen(String currentTag, Fragment fragment) {
        FragmentUtilsManager.changeSupportFragment(getActivity(), R.id.content_frame, fragment, currentTag);
//        selectMenuItem(currentTag);
    }
}
