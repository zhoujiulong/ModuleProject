package com.zhoujiulong.widgetlib.loadmore_refresh_view;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.zhoujiulong.widgetlib.R;

/**
 * @author zhoujiulong
 * @createtime 2019/3/8 13:39
 */
public class NormalLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.loadmore_normalview;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }

}



















