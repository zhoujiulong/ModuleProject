package com.zhoujiulong.simpleprojectframework.ui.home.contract;

import com.zhoujiulong.simpleprojectframework.base.BaseView;

/**
 * @author zhoujiulong
 * @createtime 2019/2/27 11:31
 */
public interface HomeFragmentContract {

    interface IView extends BaseView {
        void loginSuccess();

        void loginFaild(String msg);
    }

    interface IPresenter {
        void login();
    }

}


