package cn.ittiger.app.tiangou.photo.activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.ittiger.app.R;
import cn.ittiger.app.base.activity.BaseActivity;
import cn.ittiger.app.base.inject.InjectHelper;
import cn.ittiger.app.base.inject.InjectView;
import cn.ittiger.app.base.retrofit.RetrofitCallback;
import cn.ittiger.app.base.retrofit.RetrofitException;
import cn.ittiger.app.tiangou.bean.IntentHelper;
import cn.ittiger.app.tiangou.photo.adapter.PhotoGalleryAdapter;
import cn.ittiger.app.tiangou.photo.bean.Photo;
import cn.ittiger.app.tiangou.decoration.SpacesItemDecoration;
import cn.ittiger.app.tiangou.http.TGRetrofit;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 某个图库的照片列表
 */
public class PhotoGalleryActivity extends BaseActivity {

    private static final String CALL_TAG = "PhotoGalleryActivity";
    @InjectView(id=R.id.swipeRefreshLayout_photo_gallery)
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(id=R.id.recyclerView_gallery_photo)
    private RecyclerView mRecyclerView;
    private int mGalleryId;
    private PhotoGalleryAdapter mPhotoGalleryAdapter;
    private boolean isRefresh = true;
    private String mCallTag = CALL_TAG;

    @Override
    public View getContentView(@Nullable Bundle savedInstanceState) {

        mGalleryId = getIntent().getIntExtra(IntentHelper.KEY_INTENT_TG_ID, 0);
        mCallTag = CALL_TAG + mGalleryId;

        View view = LayoutInflater.from(this).inflate(R.layout.activity_gallery_photo, null);
        InjectHelper.inject(this, view);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

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
//                switch (newState) {
//                    case RecyclerView.SCROLL_STATE_IDLE:
//                        ImageLoader.getInstance().resume();
//                        break;
//                    case RecyclerView.SCROLL_STATE_DRAGGING:
//                        break;
//                    case RecyclerView.SCROLL_STATE_SETTLING:
//                        ImageLoader.getInstance().pause();
//                        break;
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
            }
        });

        setTitle(getIntent().getStringExtra(IntentHelper.KEY_INTENT_TG_NAME));
        return view;
    }

    @Override
    public void refreshData() {

        TGRetrofit.getInstance().queryGalleryPhotos(mGalleryId, new RetrofitCallback<List<Photo>>() {

            @Override
            public void onSuccess(List<Photo> result) {

                if(result == null) {
                    refreshFailed();
                } else {
                    if(isRefresh) {
                        if(mPhotoGalleryAdapter == null) {
                            mPhotoGalleryAdapter = new PhotoGalleryAdapter(PhotoGalleryActivity.this, (ArrayList<Photo>) result);
                            mRecyclerView.setAdapter(mPhotoGalleryAdapter);
                            SpacesItemDecoration decoration=new SpacesItemDecoration((int)getResources().getDimension(R.dimen.dp_5));
                            mRecyclerView.addItemDecoration(decoration);
                        } else {
                            mPhotoGalleryAdapter.setList((ArrayList<Photo>) result);
                        }
                    } else {
                        if(result.size() > 0) {
                            mPhotoGalleryAdapter.addAll((ArrayList<Photo>) result);
                        }
                    }
                    refreshSuccess();
                }
            }

            @Override
            public void onFailure(RetrofitException exception) {

                refreshFailed();
            }
        }, mCallTag);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        TGRetrofit.getInstance().cancelRequest(mCallTag);
        if(mPhotoGalleryAdapter != null) {
            mPhotoGalleryAdapter.cancelDisplay();
        }
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
