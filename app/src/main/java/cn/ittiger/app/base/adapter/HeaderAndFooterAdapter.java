package cn.ittiger.app.base.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author laohu
 */
public abstract class HeaderAndFooterAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private LoadStatus mLoadStatus = LoadStatus.CLICK_LOAD_MORE;
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;

    private boolean mIsHeaderViewEnable = false;
    private boolean mIsFooterViewEnable = true;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        } else if(viewType == TYPE_HEADER) {
            return onCreateHeadViewHolder(parent, viewType);
        } else if(viewType == TYPE_ITEM) {
            return onCreateItemViewHolder(parent, viewType);
        }
        return null;
    }

    public VH onCreateFooterViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    public VH onCreateHeadViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    public void onBindFooterViewHolder(VH holder, int position, LoadStatus loadStatus) {

    }

    public abstract void onBindItemViewHolder(VH holder, int position);

    public void onBindHeaderViewHolder(VH holder, int position) {

    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                onBindItemViewHolder(holder, isHeaderViewEnable() ? position - 1 : position);
                break;
            case TYPE_FOOTER:
                onBindFooterViewHolder(holder, position, mLoadStatus);
                break;
            case TYPE_HEADER:
                onBindHeaderViewHolder(holder, position);
                break;
            default:
                break;
        }
    }

    @Override
    public final int getItemCount() {

        return getDataCount() + (isFooterViewEnable() ? 1 : 0) + (isHeaderViewEnable() ? 1 : 0);
    }

    public abstract int getDataCount();

    @Override
    public int getItemViewType(int position) {

        if(isFooterViewEnable() && position + 1 == getItemCount()) {//最后一条为Footer
            return TYPE_FOOTER;
        }
        if(isHeaderViewEnable() && position == 0){//第一条为Header
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public enum LoadStatus {

        CLICK_LOAD_MORE,//点击加载更多
        LOADING_MORE//正在加载更多
    }

    private void setLoadStatus(LoadStatus loadStatus) {

        this.mLoadStatus = loadStatus;
        notifyItemChanged(getItemCount() - 1);
    }

    public void startLoadMore() {

        setLoadStatus(LoadStatus.LOADING_MORE);
    }

    public void stopLoadMore() {

        setLoadStatus(LoadStatus.CLICK_LOAD_MORE);
    }

    public boolean isHeaderViewEnable() {

        return mIsHeaderViewEnable;
    }

    public void setHeaderViewEnable(boolean headerEnable) {

        mIsHeaderViewEnable = headerEnable;
    }

    public boolean isFooterViewEnable() {

        return mIsFooterViewEnable;
    }

    public void setFooterViewEnable(boolean footerViewEnable) {

        mIsFooterViewEnable = footerViewEnable;
    }

    public boolean isFooterView(int position) {

        return isFooterViewEnable() && getItemViewType(position) == TYPE_FOOTER ? true : false;
    }

    public boolean isHeaderView(int position) {

        return isHeaderViewEnable() && getItemViewType(position) == TYPE_HEADER ? true : false;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    return getNewSpanSize(((GridLayoutManager) layoutManager).getSpanCount(), position);
                }
            });
        }
    }

    private int getNewSpanSize(int spanCount, int position) {

        if(isHeaderView(position)) {
            return spanCount;
        }

        if(isFooterView(position)) {
            return spanCount;
        }
        return 1;
    }
}

