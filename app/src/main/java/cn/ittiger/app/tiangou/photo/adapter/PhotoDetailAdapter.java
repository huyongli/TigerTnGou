package cn.ittiger.app.tiangou.photo.adapter;

import cn.ittiger.app.base.util.ImageLoaderManager;
import cn.ittiger.app.base.util.PicassoUtil;
import cn.ittiger.app.tiangou.http.TGRetrofit;
import cn.ittiger.app.tiangou.photo.bean.Photo;

import uk.co.senab.photoview.PhotoView;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 照片详情适配器
 * @author laohu
 */
public class PhotoDetailAdapter extends PagerAdapter {

    private List<Photo> mList;
    private Activity mActivity;

    public PhotoDetailAdapter(Activity activity, List<Photo> list) {

        this.mActivity = activity;
        this.mList = list;
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    public Photo getItem(int position) {

        return mList.get(position);
    }

    @Override
    public int getCount() {

        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        PhotoView photoView = new PhotoView(mActivity);
        String url = TGRetrofit.getInstance().getTGImgUrl(mList.get(position).getSrc());
//        PicassoUtil.displayPhoto(photoView, url);
        ImageLoaderManager.getInstance().displayImage(photoView, url);
        view.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
