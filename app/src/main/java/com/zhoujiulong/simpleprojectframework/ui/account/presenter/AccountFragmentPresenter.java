package com.zhoujiulong.simpleprojectframework.ui.account.presenter;

import com.zhoujiulong.simpleprojectframework.base.BasePresenter;
import com.zhoujiulong.simpleprojectframework.ui.account.contract.AccountFragmentContract;
import com.zhoujiulong.simpleprojectframework.ui.account.model.AccountFragmentModel;

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:34
 */
public class AccountFragmentPresenter extends BasePresenter<AccountFragmentModel, AccountFragmentContract.IView> implements AccountFragmentContract.IPresenter {

    @Override
    public void initModel() {
        mModel = new AccountFragmentModel();
    }

}
