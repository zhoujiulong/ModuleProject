package com.zhoujiulong.simpleprojectframework.ui.account.fragment

import android.view.View
import com.zhoujiulong.simpleprojectframework.R
import com.zhoujiulong.simpleprojectframework.base.BaseFragment
import com.zhoujiulong.simpleprojectframework.ui.account.contract.AccountFragmentContract
import com.zhoujiulong.simpleprojectframework.ui.account.presenter.AccountFragmentPresenter
import kotlinx.android.synthetic.main.fragment_account.view.*

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 13:59
 */
class AccountFragment : BaseFragment<AccountFragmentPresenter>(), AccountFragmentContract.IView {

    override fun getLayoutId(): Int {
        return R.layout.fragment_account
    }

    override fun initView(rootView: View) {

    }

    override fun initPresenter() {

    }

    override fun initListener() {
        mRootView.loadingView.setOnBottomTvListener {
            mRootView.loadingView.showLoading()
            getData()
        }
    }

    override fun initData() {

    }

    override fun getData() {
        mRootView.loadingView.postDelayed({
            mRootView.loadingView.showEmpty()
        }, 3000)
    }

    override fun onClick(view: View) {

    }
}
