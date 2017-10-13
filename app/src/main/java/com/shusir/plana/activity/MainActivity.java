package com.shusir.plana.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.shusir.plana.AppContext;
import com.shusir.plana.R;
import com.shusir.plana.base.BaseActivity;
import com.shusir.plana.fragment.MoneyFragment;
import com.shusir.plana.fragment.PlanFragment;
import com.shusir.plana.ui.SimpleBackPage;
import com.shusir.plana.ui.UIHelper;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SuSir on 2017/8/15 0015
 * <p>
 * <br>Explanation :入口
 */

public class MainActivity extends BaseActivity {
    private FragmentManager mFragmentManager; // fragment 管理器
    private Fragment mCurrentFragment;   // 复用的fragment
    @Bind(R.id.nav_money_click)
    RelativeLayout mNavMoneyClick;
    @Bind(R.id.nav_plan_click)
    RelativeLayout mNavPlanClick;
    PlanFragment planFragment = new PlanFragment();
    MoneyFragment moneyFragment = new MoneyFragment();


    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        showFragment(moneyFragment);
        if (AppContext.isFirstStart()){
            UIHelper.showSimpleBack(this, SimpleBackPage.SETTING);
        }

    }

    @Override
    protected void initView() {
        super.initView();
        mNavMoneyClick.setSelected(true);
    }

    @OnClick({R.id.nav_money_click, R.id.nav_plan_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_money_click:
                AppContext.showToast("资产");
                mNavMoneyClick.setSelected(true);
                mNavPlanClick.setSelected(false);
                showFragment(moneyFragment);
                break;
            case R.id.nav_plan_click:
                AppContext.showToast("计划");
                mNavMoneyClick.setSelected(false);
                mNavPlanClick.setSelected(true);
                showFragment(planFragment);
                break;
        }
    }

    /**
     * 解决fragment重复加载
     *
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.id_main_context, fragment);
        }
        if (mCurrentFragment != null) {
            if (mCurrentFragment == fragment) {
                fragmentTransaction.commit();
                return;
            }
            fragmentTransaction.hide(mCurrentFragment);
        }
        fragmentTransaction.commit();
        mCurrentFragment = fragment;
    }

}
