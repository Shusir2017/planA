package com.shusir.plana;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.litesuits.common.utils.MD5Util;
import com.shusir.plana.base.BaseApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DXY on 2016/8/22.
 * <br>Explanation : APP 全局类
 */
public class AppContext extends BaseApplication {


    public static AppContext instance;
    private static final String FIRSTSTART = "FIRSTSTART";
    private String deviceToken = "";
    private int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 获取http请求的Header信息
     *
     * @return
     */
    public static Map<String, String> addHttpHeader() {
        Map<String, String> map = new HashMap<>();
        map.put("applyType", "1");//申请端类型 1,安卓；2,iOS；3,H5
        return map;
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        //        DataCleanManager.cleanDatabases(this);
        //        // 清除数据缓存
        //        DataCleanManager.cleanInternalCache(this);
    }


    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }


    /**
     * 存入sd卡 中并获取文件路径
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static String savePicToSdcard(Bitmap bitmap, String path) {

        if (bitmap == null) {
            return path;
        } else {
            File destFile = new File(path);
            File file = new File(Environment.getExternalStorageDirectory() + "/hc");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!destFile.exists()) {
                try {
                    destFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, os);
                os.flush();
                os.close();
            } catch (IOException e) {
                path = "";
            }


        }
        return path;
    }

    /**
     * 存入sd卡 中并获取文件路径
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static String savePicToSdcard(Bitmap bitmap, String path, boolean isCompress) {

        if (bitmap == null) {
            return path;
        } else {
            File destFile = new File(path);
            File file = new File(Environment.getExternalStorageDirectory() + "/hc");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!destFile.exists()) {
                try {
                    destFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (IOException e) {
                path = "";
            }


        }
        return path;
    }

    /**
     * 存入sd卡 中并获取文件路径
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static String savePicToSdcard(Bitmap bitmap, String path, int quality) {

        if (bitmap == null) {
            return path;
        } else {
            File destFile = new File(path);
            File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!destFile.exists()) {
                try {
                    destFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
                os.flush();
                os.close();

            } catch (IOException e) {
                path = "";
            }


        }
        return path;
    }

    /**
     * 从文件中获取bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmapToSD(String path) {
        File mFile = new File(path);
        //若该文件存在
        if (mFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        }
        return null;
    }


    /**
     * 获取bitmap大小
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }


    /**
     * uri 转换Bitmap
     * 已被修正  正式方法为
     * BitmapUtil.createBitmapThumbnail(MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri), true, 240, 240)
     *
     * @param uri
     * @return
     */
    public static Bitmap changeUri(Uri uri) {
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor c = context().getContentResolver().query(uri, filePathColumns, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String picturePath = c.getString(columnIndex);
        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
        c.close();
        return bitmap;
    }


    public static String MD5Utils(String md5) {
        byte[] md = MD5Util.md5(md5);

        StringBuilder hex = new StringBuilder(md.length * 2);
        for (byte b : md) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    /**
     * 根据byte数组，生成图片
     */
    public static String saveJPGFile(byte[] data, String jpgFileName) {
        if (data == null)
            return null;
        //        File mediaStorageDir = mContext
        //                .getExternalFilesDir(Constant.cacheImage);
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/hc/face");

        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mediaStorageDir + "/" + jpgFileName + ".jpg");
            bos = new BufferedOutputStream(fos);
            bos.write(data);
            return mediaStorageDir.getAbsolutePath() + "/" + jpgFileName + ".jpg";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 应用程序是否第一次启动
     */
    public static boolean isFirstStart() {

        try {
            return !get(FIRSTSTART, false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            set(FIRSTSTART, true);
        }
        return false;
    }


}
