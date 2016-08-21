package cn.ittiger.app.tiangou.fragment;

import cn.ittiger.app.R;
import cn.ittiger.app.base.fragment.BaseFragment;
import cn.ittiger.app.base.inject.InjectHelper;
import cn.ittiger.app.base.inject.InjectView;
import cn.ittiger.app.base.util.Callback;
import cn.ittiger.app.tiangou.adapter.ListAdapter;
import cn.ittiger.app.tiangou.bean.ListItem;
import cn.ittiger.app.tiangou.http.TGRetrofit;
import cn.ittiger.app.tiangou.type.TGModelEnum;

import com.litesuits.android.async.SimpleTask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * @author: laohu on 2016/7/10
 * @site: http://ittiger.cn
 */
public class ListFragment extends BaseFragment {
    @InjectView(id=R.id.swipeRefreshLayout_list)
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(id=R.id.recyclerView_list)
    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int mLastVisibleItemPosition = 0;
    private int mPage = 1;
    private int mPageSize = 10;
    private int mClassifyId;
    protected TGModelEnum mModelEnum;
    private String mCallTag = "ListFragment";

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable Bundle savedInstanceState) {

        mClassifyId = getArguments().getInt(FragmentFactory.KEY_BUNDLE_CLASSIFY_ID);
        mModelEnum = (TGModelEnum) getArguments().getSerializable(FragmentFactory.KEY_BUNDLE_MODEL_ENUM);
        mCallTag = mCallTag + mModelEnum.value() + "_" + mClassifyId;

        View view = inflater.inflate(R.layout.fragment_list_layout, null);
        InjectHelper.inject(this, view);

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                refreshData();
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mListAdapter != null
                        && mLastVisibleItemPosition + 1 == mListAdapter.getItemCount()) {
                    loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = mGridLayoutManager.findLastVisibleItemPosition();
            }
        });

        return view;
    }

    @Override
    public void refreshData() {

        mPage = 1;
        query(new Callback<List<ListItem>>() {

            @Override
            public void onSuccess(List<ListItem> result) {

                if(mListAdapter == null) {
                    mListAdapter = new ListAdapter(getActivity(), result, mModelEnum);
                    mRecyclerView.setAdapter(mListAdapter);
                } else {
                    mListAdapter.setList(result);
                }
                mPage ++;
                refreshSuccess();
            }

            @Override
            public void onFailure(Exception exception) {

                refreshFailed();
            }
        });
    }

    private void loadMore() {

        if(mListAdapter == null) {
            return;
        }
        mListAdapter.startLoadMore();
        query(new Callback<List<ListItem>>() {

            @Override
            public void onSuccess(List<ListItem> result) {

                if(result.size() > 0) {
                    mListAdapter.addAll(result);
                }
                if(result.size() == mPageSize) {
                    mPage ++;
                }
                mListAdapter.stopLoadMore();
            }

            @Override
            public void onFailure(Exception exception) {

                mListAdapter.stopLoadMore();
            }
        });
    }

    private void query(final Callback<List<ListItem>> callback) {

        SimpleTask<List<ListItem>> task = new SimpleTask<List<ListItem>>() {

            @Override
            protected List<ListItem> doInBackground() {

                return TGRetrofit.getInstance().queryList(mModelEnum,mClassifyId, mPage, mPageSize, mCallTag);
            }

            @Override
            protected void onPostExecute(List<ListItem> result) {

                if(result == null) {
                    if(callback != null) {
                        callback.onFailure(new Exception("the request failure"));
                    }
                } else {
                    if(callback != null) {
                        callback.onSuccess(result);
                    }
                }
            }
        };
        task.execute();
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
        TGRetrofit.getInstance().cancelRequest(mCallTag);
        if(mListAdapter != null) {
            mListAdapter.cancelDisplay();
        }
        mListAdapter = null;
        mPage = 1;
    }

    @Override
    public void refreshFailed() {

        super.refreshFailed();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshSuccess() {

        super.refreshSuccess();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
