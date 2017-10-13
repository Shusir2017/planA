package com.shusir.plana.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mingyuans.smoke.Smoke;
import com.shusir.plana.AppContext;
import com.shusir.plana.AppManager;
import com.shusir.plana.util.FileUtils;

import java.io.File;
import java.io.Serializable;

import butterknife.ButterKnife;


/**
 * Created by Dylan Shen on 2017/6/19.
 * <br>Explanation :基类
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private boolean mIsWindowFeature = true;
    private static Intent mIntent;
    private boolean _isVisible;
    private ProgressDialog _waitDialog;
    protected LayoutInflater mInflater;
    protected ActionBar mActionBar;
    private final String packageName = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setTheme(R.style.App_Theme_Main);
        //解决ScrollView键盘弹出问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (mIsWindowFeature) {
            // 取消标题
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        mInflater = getLayoutInflater();

        if (setLayoutId() != 0) {
            setContentView(setLayoutId());
        }
        AppManager.getAppManager().addActivity(this);
        mIntent = new Intent();
        _isVisible = true;
        ButterKnife.bind(this);
        init(savedInstanceState);
        initView();
        //屏幕常量可关闭
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AppContext.bus.register(this);
    }

    protected void init() {
    }

    protected void init(Bundle savedInstanceState) {
    }

    protected void initView() {
    }

    /**
     * 获取字符串string
     *
     * @param text
     * @return
     */
    protected String getTextStr(TextView text) {
        return text.getText().toString().trim();
    }

    /**
     * 字符是否存在
     *
     * @param sequence
     * @return
     */
    protected boolean isEmpty(CharSequence sequence) {

        return TextUtils.isEmpty(sequence);
    }


    /**
     * 简化FindViewById
     *
     * @param id 控件id
     */
    public final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }


    public void intent(Context context, Class c, boolean isFinish) {
        mIntent.setClass(context, c);
        startActivity(mIntent);
        if (isFinish) {
            finish();
        }

    }

    public void intent(Context context, Class c, String name, String value, boolean isFinish) {
        mIntent.setClass(context, c);
        mIntent.putExtra(name, value);
        startActivity(mIntent);
        if (isFinish) {
            finish();
        }
    }

    public void intent(Context context, Class c, String name, int value, boolean isFinish) {
        mIntent.setClass(context, c);
        mIntent.putExtra(name, value);
        startActivity(mIntent);
        if (isFinish) {
            finish();
        }
    }

    public void intent(Context context, Class c, String name, boolean value, boolean isFinish) {
        mIntent.setClass(context, c);
        mIntent.putExtra(name, value);
        startActivity(mIntent);
        if (isFinish) {
            finish();
        }
    }

    public void intent(Context context, Class c, Bundle bundle, boolean isFinish) {
        mIntent.setClass(context, c);
        mIntent.putExtras(bundle);
        startActivity(mIntent);
        if (isFinish) {
            finish();
        }
    }

    public void intentForResult(Context context, Class c, String name, Serializable ser, int requestCode, boolean isFinish) {
        mIntent.setClass(context, c);
        mIntent.putExtra(name, ser);
        startActivityForResult(mIntent, requestCode);
        if (isFinish) {
            finish();
        }
    }

    public void intentForResult(Context context, Class c, String name, String ser, int requestCode, boolean isFinish) {
        mIntent.setClass(context, c);
        mIntent.putExtra(name, ser);
        startActivityForResult(mIntent, requestCode);
        if (isFinish) {
            finish();
        }
    }

    public void intentForResult(Context context, String action, int requestCode, boolean isFinish) {
        mIntent.setAction(action);
        startActivityForResult(mIntent, requestCode);
        if (isFinish) {
            finish();
        }
    }

    public Intent getMyIntent() {
        return mIntent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 设置布局
     */
    protected int setLayoutId() {
        return 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.bus.unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    /**
     * 计算缓存的大小
     *
     * @return String
     */
    protected String calculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = this.getFilesDir();
        File cacheDir = this.getCacheDir();

        fileSize += FileUtils.getDirSize(filesDir);
        fileSize += FileUtils.getDirSize(cacheDir);
        if (fileSize > 0)
            cacheSize = FileUtils.formatFileSize(fileSize);
        Smoke.info("缓存大小>>>>" + cacheSize);
        return cacheSize;
    }


}

