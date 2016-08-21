package cn.ittiger.app.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ittiger.app.R;
import cn.ittiger.app.base.util.PageLoadingHelper;

public abstract class BaseActivity extends AppCompatActivity {
    protected PageLoadingHelper mPageLoadingHelper;
    protected boolean mIsInitRefresh = true;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.base_activity_layout, null);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        view.addView(getContentView(savedInstanceState), 2);

        setContentView(view);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPageLoadingHelper = new PageLoadingHelper(view);
        mPageLoadingHelper.setOnLoadingClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clickToRefresh();
            }
        });

        if(mIsInitRefresh) {
            clickToRefresh();
        }
    }

    /**
     * Fragment数据视图
     * @param savedInstanceState
     * @return
     */
    public abstract View getContentView(@Nullable Bundle savedInstanceState);

    /**
     * 点击刷新加载
     */
    private void clickToRefresh() {

        startRefresh();
        refreshData();
    }

    /**
     * 初始化数据
     */
    public abstract void refreshData();

    /**
     * 开始加载数据
     */
    private void startRefresh() {

        mPageLoadingHelper.startRefresh();
    }

    /**
     * 加载失败
     */
    public void refreshFailed() {

        mPageLoadingHelper.refreshFailed();
    }

    /**
     * 加载成功
     */
    public void refreshSuccess() {

        mPageLoadingHelper.refreshSuccess();
    }
}
