package com.shusir.plana.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.shusir.plana.R;
import com.shusir.plana.base.BaseActivity;
import com.shusir.plana.base.BaseFragment;

import java.lang.ref.WeakReference;


/**
 * Created by SuSir on  2017/7/9 0009
 * <br>Explanation:界面显示类
 */
public class SimpleBackActivity extends BaseActivity {

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    private static final String TAG = "FLAG_TAG";
    protected WeakReference<Fragment> mFragment;
    protected int mPageValue = -1;

    @Override
    public void startActivity(Intent intent) {
        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        super.startActivity(intent);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_simple_fragment;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        Intent intent = getIntent();
        if (mPageValue == -1) {
            mPageValue = intent.getIntExtra(BUNDLE_KEY_PAGE, 0);
        }
        initFromIntent(mPageValue, getIntent());
    }

    protected void initFromIntent(int pageValue, Intent data) {
        if (data == null) {
            throw new RuntimeException("you must provide a page info to display");
        }
        SimpleBackPage page = SimpleBackPage.getPageByValue(pageValue);
        if (page == null) {
            throw new IllegalArgumentException("can not find page by value:" + pageValue);
        }

//        setActionBarTitle(page.getTitle());

        try {
            Fragment fragment = (Fragment) page.getClz().newInstance();

            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
            if (args != null) {
                fragment.setArguments(args);
            }

            FragmentTransaction trans = getSupportFragmentManager()
                    .beginTransaction();
            trans.replace(R.id.container, fragment, TAG);
            trans.commitAllowingStateLoss();

            mFragment = new WeakReference<>(fragment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "generate fragment error. by value:" + pageValue);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null && mFragment.get() != null
                && mFragment.get() instanceof BaseFragment) {
            BaseFragment bf = (BaseFragment) mFragment.get();
            if (!bf.onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN
                && mFragment.get() instanceof BaseFragment) {
            ((BaseFragment) mFragment.get()).onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void initView() {
    }

}
