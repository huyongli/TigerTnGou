package cn.ittiger.app.tiangou.adapter;

import cn.ittiger.app.R;
import cn.ittiger.app.base.adapter.HeaderAndFooterAdapter;
import cn.ittiger.app.base.util.DisplayUtil;
import cn.ittiger.app.base.util.ImageLoaderManager;
import cn.ittiger.app.tiangou.bean.IntentHelper;
import cn.ittiger.app.tiangou.bean.ListItem;
import cn.ittiger.app.tiangou.http.TGRetrofit;
import cn.ittiger.app.tiangou.type.TGModelEnum;
import cn.ittiger.app.tiangou.util.ModelClassMapUtil;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author laohu
 */
public class ListAdapter extends HeaderAndFooterAdapter<RecyclerView.ViewHolder> {
    private static final String TAG_IMAGE_LOAD = "classis_gallery";
    /**
     * 每隔Item项的宽度
     */
    private int mWidth;
    private Context mContext;
    private List<ListItem> mList;
    private TGModelEnum mModelEnum;

    public ListAdapter(Context context, List<ListItem> list, TGModelEnum modelEnum) {

        mContext = context;
        mList = list;
        mModelEnum = modelEnum;

        int screenWidth = DisplayUtil.getWindowWidth(context);
        mWidth = (int)(screenWidth / 2 + 0.5f);
    }

    public void setList(List<ListItem> list) {

        mList = list;
        notifyDataSetChanged();
    }

    public void addAll(List<ListItem> list) {

        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.footer_layout, null);
        return new FooterViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        RelativeLayout view = (RelativeLayout) View.inflate(mContext, R.layout.fragment_list_item_layout, null);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position, LoadStatus loadStatus) {

    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

        ListItem listItem = mList.get(position);
        PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
        photoViewHolder.mTextView.setText(listItem.getName());
        photoViewHolder.mRoot.setTag(position);

        String imgUrl = TGRetrofit.getInstance().getTGImgUrl(listItem.getImg());
        ImageLoaderManager.getInstance().displayImage(photoViewHolder.mImageView, imgUrl);
    }

    @Override
    public int getDataCount() {

        return mList.size();
    }

    public void cancelDisplay() {

    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout mRoot;
        public ImageView mImageView;
        public TextView mTextView;

        public PhotoViewHolder(View itemView) {

            super(itemView);
            mRoot = (RelativeLayout) itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.iv_classify);
            mTextView = (TextView) itemView.findViewById(R.id.tv_classify);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mWidth, mWidth);
            mRoot.setLayoutParams(layoutParams);
            mRoot.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    ListItem listItem = mList.get((Integer) v.getTag());
                    Class claxx = ModelClassMapUtil.getDetailClaxx(mModelEnum);
                    Intent intent = new Intent(mContext, claxx);
                    intent.putExtra(IntentHelper.KEY_INTENT_TG_ID, listItem.getId());
                    intent.putExtra(IntentHelper.KEY_INTENT_TG_NAME, listItem.getName());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {

            super(itemView);
            ((TextView)itemView.findViewById(R.id.loading_more_txt))
                    .setText(ModelClassMapUtil.getPullUpLoadText(mContext, mModelEnum));
        }
    }
}
