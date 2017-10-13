package com.shusir.plana.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by SuSir on  2017/7/9 0009
 * <br>Explanation:界面帮助类
 *
 */
public class UIHelper {


    /**
     * 显示登陆界面
     * @param context
     */
    public static void showLoginActivity(Context context){


    }

    public static void showSimpleBack(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    public static void showSimpleBack(Context context, SimpleBackPage page,
                                      Bundle args) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }



}
