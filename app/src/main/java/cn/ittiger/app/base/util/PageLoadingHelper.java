package cn.ittiger.app.base.util;

import cn.ittiger.app.R;
import cn.ittiger.app.base.ui.LoadingView;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 页面加载的时候，页面加载进度条及加载失败后的处理
 * @author: laohu on 2016/7/3
 * @site: http://ittiger.cn
 */
public class PageLoadingHelper {
    private FrameLayout mLoadingFailedLayout;
    private LoadingView mLoadingView;
    private TextView mLoadingFailedText;

    public PageLoadingHelper(View rootView) {

        mLoadingFailedLayout = (FrameLayout) rootView.findViewById(R.id.loading_failed_layout);
        mLoadingView = (LoadingView) rootView.findViewById(R.id.loading_view);
        mLoadingFailedText = (TextView) rootView.findViewById(R.id.tv_loading_failed);
    }

    public void setOnLoadingClickListener(final View.OnClickListener onClickListener) {

        this.mLoadingFailedText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(onClickListener != null) {
                    mLoadingFailedText.setVisibility(View.GONE);
                    onClickListener.onClick(v);
                }
            }
        });
    }

    public void setLoadingFailedText(String failedText) {

        mLoadingFailedText.setText(failedText);
    }

    /**
     * 开始加载数据
     */
    public void startRefresh() {

        if(mLoadingView.getVisibility() == View.GONE) {
            mLoadingFailedLayout.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.VISIBLE);
            mLoadingView.start();
        }
    }

    /**
     * 加载失败
     */
    public void refreshFailed() {

        if(mLoadingView.getVisibility() == View.VISIBLE) {
            mLoadingView.stop();
            mLoadingView.setVisibility(View.GONE);
            mLoadingFailedText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载成功
     */
    public void refreshSuccess() {

        if(mLoadingFailedLayout.getVisibility() == View.VISIBLE) {
            mLoadingView.stop();
            mLoadingFailedLayout.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.GONE);
            mLoadingFailedText.setVisibility(View.GONE);
        }
    }
}
