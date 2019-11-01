package com.zhoujiulong.simpleprojectframework.ui.account.fragment

import android.view.View
import com.zhoujiulong.simpleprojectframework.R
import com.zhoujiulong.simpleprojectframework.base.BaseFragment
import com.zhoujiulong.simpleprojectframework.base.emptyimpl.EmptyContract
import com.zhoujiulong.simpleprojectframework.base.emptyimpl.EmptyPresenter
import kotlinx.android.synthetic.main.fragment_account.*

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 13:59
 */
class AccountFragment : BaseFragment<EmptyPresenter>(), EmptyContract.IView {

    override fun getLayoutId(): Int {
        return R.layout.fragment_account
    }

    override fun initView() {

    }

    override fun initPresenter() {

    }

    override fun initListener() {
        loadingView.setOnBottomTvListener {
            loadingView.showLoading()
            getData()
        }
    }

    override fun initData() {

    }

    override fun getData() {
        loadingView.postDelayed({
            loadingView.showEmpty()
        }, 3000)
    }

    override fun onClick(view: View) {

    }
}
