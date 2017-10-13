package com.shusir.plana.ui;


import com.shusir.plana.R;
import com.shusir.plana.fragment.SettingFragment;

/**
 * Created by SuSir on  2017/7/9 0009
 * <br>Explanation: 页面配置
 */
public enum SimpleBackPage {

    //设置
    SETTING(1, R.string.setting_data, SettingFragment.class);
//
//
//    SHOW_CAR(10,R.string.find_showcar, NewCarSourceFragment.class);
//



    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }

}
