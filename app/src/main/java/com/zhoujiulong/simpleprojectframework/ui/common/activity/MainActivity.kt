package com.zhoujiulong.simpleprojectframework.ui.common.activity

import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import com.zhoujiulong.simpleprojectframework.R
import com.zhoujiulong.simpleprojectframework.base.BaseActivity
import com.zhoujiulong.simpleprojectframework.ui.account.fragment.AccountFragment
import com.zhoujiulong.simpleprojectframework.ui.common.contract.MainContract
import com.zhoujiulong.simpleprojectframework.ui.common.presenter.MainPresenter
import com.zhoujiulong.simpleprojectframework.ui.home.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainContract.IView {

    internal enum class TAB {
        HOME, ACCOUNT
    }

    private var mCurrentTab: TAB? = null
    private val mFragments: HashMap<TAB, Fragment> = HashMap()
    private val mTabTextUnselectedColor: Int by lazy {
        ContextCompat.getColor(mContext, R.color.main_activity_tab_tv_unselected)
    }
    private val mTabTextSelectedColor: Int by lazy {
        ContextCompat.getColor(this, R.color.main_activity_tab_tv_selected)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
    }

    override fun initPresenter() {
        mPresenter = MainPresenter()
        mPresenter?.attachView(this@MainActivity, ReTag)
    }

    override fun initListener() {
        setOnClick(rlHome, rlAccount)
    }

    override fun initData() {
        showTab(TAB.HOME)
    }

    override fun getData() {

    }

    override fun onClick(view: View) {
        when (view) {
            rlHome -> showTab(TAB.HOME)
            rlAccount -> showTab(TAB.ACCOUNT)
        }
    }

    private fun showTab(tabIndex: TAB) {
        if (tabIndex == mCurrentTab) return
        val transaction = supportFragmentManager.beginTransaction()
        val fragment: Fragment?
        if (mFragments.containsKey(tabIndex) && mFragments[tabIndex] != null) {
            fragment = mFragments[tabIndex]
        } else {
            when (tabIndex) {
                TAB.HOME -> fragment = HomeFragment()
                TAB.ACCOUNT -> fragment = AccountFragment()
            }
            mFragments[tabIndex] = fragment
            transaction.add(R.id.framelayout, fragment, tabIndex.name)
        }
        if (fragment != null) {
            if (mFragments.containsKey(mCurrentTab)) {
                val baseFragmentInner = mFragments[mCurrentTab]
                if (baseFragmentInner != null) {
                    transaction.hide(baseFragmentInner)
                }
            }
            transaction.show(fragment).commitAllowingStateLoss()
            initTab(tabIndex)
            mCurrentTab = tabIndex
        }
    }

    private fun initTab(tabIndex: TAB) {
        ivHome.setImageResource(R.mipmap.ic_home_unselected)
        tvHome.setTextColor(mTabTextUnselectedColor)
        ivAccount.setImageResource(R.mipmap.ic_account_unselected)
        tvAccount.setTextColor(mTabTextUnselectedColor)
        when (tabIndex) {
            TAB.HOME -> {
                ivHome.setImageResource(R.mipmap.ic_home_selected)
                tvHome.setTextColor(mTabTextSelectedColor)
            }
            TAB.ACCOUNT -> {
                ivAccount.setImageResource(R.mipmap.ic_account_selected)
                tvAccount.setTextColor(mTabTextSelectedColor)
            }
        }
    }

}



















