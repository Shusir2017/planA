package com.shusir.plana.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.multidex.MultiDexApplication;

import com.litesuits.common.assist.Toastor;
import com.mingyuans.smoke.Smoke;
import com.mingyuans.smoke.android.AndroidProcesses;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;

/**
 * Created by DXY on 2016/8/22.
 * <br>Explanation : 程序入口
 */
public class BaseApplication extends MultiDexApplication {

    static Context context;
    static Resources resources;
    private static boolean sIsAtLeastGB;
    private static String PREF_NAME = "creativelocker";
    public Vibrator mVibrator;
    public static BaseApplication instance;
    public static Toastor mToast;


    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }
    }

    private static LocationManager locationManager;
    private static LocationListener locationListener;
    private static String ip = "";
    ArrayList<Activity> list = new ArrayList<Activity>();
    private String deviceToken="";
    private int count=0;
    private String deviceId="";
    public static Bus bus = new Bus(ThreadEnforcer.ANY);
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        mToast = new Toastor(this);
        context = getApplicationContext();
        resources = getResources();
        //图片加载框架初始化
//        Fresco.initialize(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        //初始化日志
        Smoke.install("SuSir", AndroidProcesses.androidDefault());
    }


    public static synchronized BaseApplication context() {
        return (BaseApplication) context;
    }
    public static Resources resources() {
        return resources;
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象*/
    public void removeActivity(Activity a){
        list.remove(a);
    }


    /**
     * 向Activity列表中添加Activity对象*/
    public void addActivity(Activity a){
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static BaseApplication getInstance() {
        return instance;
    }


    /**
     * @param text 要显示的文本
     *             <br>时间短的Toast
     */
    public static void showToast(String text) {
        mToast.showSingletonToast(text);
    }

    /**
     * @param text 要显示的文本
     *             <br>时间长的Toast
     */
    public static void showToastLong(String text) {
        mToast.showLongToast(text);
    }



    public static void apply(SharedPreferences.Editor editor) {
        if (sIsAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }


    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }

    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    public static SharedPreferences getPreferences() {
        SharedPreferences pre = context().getSharedPreferences(PREF_NAME,
                Context.MODE_MULTI_PROCESS);
        return pre;
    }

    public static SharedPreferences getPreferences(String prefName) {
        return context().getSharedPreferences(prefName,
                Context.MODE_MULTI_PROCESS);
    }
}
