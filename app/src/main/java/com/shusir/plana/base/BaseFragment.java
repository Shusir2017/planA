package com.shusir.plana.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.shusir.plana.util.ImageLoader;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * Created by SuSir on  2017/7/9 0009
 * <br>Explanation:fragment 基础类
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;
    private RequestManager mImgLoader;
    protected LayoutInflater mInflater;
    protected ViewGroup mContainer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
        //        AppContext.bus.register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            mContainer = container;
            // Do something
            onBindViewBefore(mRoot);
            // Bind view
            ButterKnife.bind(this, mRoot);
            // Get savedInstanceState
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            // Init
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }

    protected void onBindViewBefore(View root) {
        // ...
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mImgLoader = null;
        mBundle = null;
        mContainer = null;
        //        AppContext.bus.unregister(this);

    }

    protected abstract int getLayoutId();

    protected void initBundle(Bundle bundle) {

    }

    protected void initWidget(View root) {

    }

    @Override
    public void onStop() {
        super.onStop();
        //        AppContext.bus.unregister(this);

    }


    protected void initData() {

    }

    protected <T extends View> T findView(int viewId) {
        return (T) mRoot.findViewById(viewId);
    }

    protected <T extends Serializable> T getBundleSerializable(String key) {
        if (mBundle == null) {
            return null;
        }
        return (T) mBundle.getSerializable(key);
    }

    /**
     * 获取一个图片加载管理器
     *
     * @return RequestManager
     */
    public synchronized RequestManager getImgLoader() {
        if (mImgLoader == null)
            mImgLoader = Glide.with(this);
        return mImgLoader;
    }

    /***
     * 从网络中加载数据
     *
     * @param viewId   view的id
     * @param imageUrl 图片地址
     */
    protected void setImageFromNet(int viewId, String imageUrl) {
        setImageFromNet(viewId, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param viewId      view的id
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(int viewId, String imageUrl, int placeholder) {
        ImageView imageView = findView(viewId);
        setImageFromNet(imageView, imageUrl, placeholder);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView imageView
     * @param imageUrl  图片地址
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl) {
        setImageFromNet(imageView, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView   imageView
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl, int placeholder) {
        ImageLoader.loadImage(getImgLoader(), imageView, imageUrl, placeholder);
    }


    protected void setText(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            return;
        }
        textView.setText(text);
    }

    protected void setText(int viewId, String text, String emptyTip) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setText(emptyTip);
            return;
        }
        textView.setText(text);
    }

    protected void setTextEmptyGone(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setText(text);
    }

    protected <T extends View> T setGone(int id) {
        T view = findView(id);
        view.setVisibility(View.GONE);
        return view;
    }

    protected <T extends View> T setVisibility(int id) {
        T view = findView(id);
        view.setVisibility(View.VISIBLE);
        return view;
    }

    protected void setInVisibility(int id) {
        findView(id).setVisibility(View.INVISIBLE);
    }

    protected void onRestartInstance(Bundle bundle) {

    }

    public boolean onBackPressed() {
        return false;
    }

}
