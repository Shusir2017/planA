package com.shusir.plana.fragment;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shusir.plana.R;
import com.shusir.plana.base.BaseFragment;
import com.shusir.plana.bean.UserInfo;
import com.shusir.plana.databinding.FragmentMoneyBinding;

import butterknife.Bind;

/**
 * Created by SuSir on  2017/8/15 0015
 * <br>Explanation:资产
 */
public class MoneyFragment extends BaseFragment {
    UserInfo userInfo = new UserInfo();
    @Bind(R.id.image_back)
    ImageView mBack;
    @Bind(R.id.action_title)
    TextView mTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_money;
    }

    @Override
    protected void onBindViewBefore(View root) {
        super.onBindViewBefore(root);
        //        FragmentMoneyBinding viewDataBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_money);
        //        FragmentMoneyBinding viewDataBinding = DataBindingUtil.inflate(mInflater, R.layout.fragment_money, mContainer, false);
        FragmentMoneyBinding viewDataBinding = DataBindingUtil.bind(root);
        viewDataBinding.setUser(userInfo);
        
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mBack.setVisibility(View.GONE);
        mTitle.setText(R.string.nav_property);


        //        userInfo.setUserName("66666");
//        userInfo.setUserTotalMoney("ffff");


    }

    @Override
    protected void initData() {
        super.initData();
    }


}
