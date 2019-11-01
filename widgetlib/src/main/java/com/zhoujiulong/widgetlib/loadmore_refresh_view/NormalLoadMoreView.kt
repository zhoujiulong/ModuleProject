package com.zhoujiulong.widgetlib.loadmore_refresh_view

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.zhoujiulong.widgetlib.R

/**
 * @author zhoujiulong
 * @createtime 2019/3/8 13:39
 */
class NormalLoadMoreView : LoadMoreView() {

    override fun getLayoutId(): Int {
        return R.layout.loadmore_normalview
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }

}



















