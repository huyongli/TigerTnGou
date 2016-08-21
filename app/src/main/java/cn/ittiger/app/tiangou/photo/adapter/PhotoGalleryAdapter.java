package cn.ittiger.app.tiangou.photo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.ittiger.app.R;
import cn.ittiger.app.base.util.DisplayUtil;
import cn.ittiger.app.base.util.ImageLoaderManager;
import cn.ittiger.app.base.util.PicassoUtil;
import cn.ittiger.app.tiangou.http.TGRetrofit;
import cn.ittiger.app.tiangou.photo.activity.PhotoDetailActivity;
import cn.ittiger.app.tiangou.photo.bean.Photo;

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryAdapter.GalleryPhotoViewHolder>{

    private static final String TAG_IMAGE_LOAD = "gallery_photo";
    private ArrayList<Photo> mPhotos;
    private Context mContext;

    public PhotoGalleryAdapter(Context context, ArrayList<Photo> list) {

        mPhotos = list;
        mContext = context;
    }

    @Override
    public GalleryPhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_gallery_photo_item, viewGroup, false);
        return new GalleryPhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryPhotoViewHolder viewHolder, int position) {

        viewHolder.imageView.setTag(position);
        String url = TGRetrofit.getInstance().getTGImgUrl(mPhotos.get(position).getSrc());
//        PicassoUtil.displayPhoto(viewHolder.imageView, url, TAG_IMAGE_LOAD);
        ImageLoaderManager.getInstance().displayImage(viewHolder.imageView, url);
    }


    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public class GalleryPhotoViewHolder extends  RecyclerView.ViewHolder{

        private ImageView imageView;

        public GalleryPhotoViewHolder(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.imageView_gallery_photo );
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, PhotoDetailActivity.class);
                    intent.putExtra("photos", mPhotos);
                    intent.putExtra("position", (Integer) v.getTag());
                    mContext.startActivity(intent);
                }
            });

        }
    }
    public void setList(ArrayList<Photo> list) {

        mPhotos = list;
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Photo> list) {

        mPhotos.addAll(list);
        notifyDataSetChanged();
    }

    public void cancelDisplay() {

//        PicassoUtil.cancelTag(TAG_IMAGE_LOAD);
    }
}
