package cn.ittiger.app.tiangou.photo.activity;

import cn.ittiger.app.R;
import cn.ittiger.app.base.inject.InjectHelper;
import cn.ittiger.app.base.inject.InjectView;
import cn.ittiger.app.tiangou.http.TGRetrofit;
import cn.ittiger.app.tiangou.photo.adapter.PhotoDetailAdapter;
import cn.ittiger.app.tiangou.photo.bean.Photo;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * 照片走廊
 */
public class PhotoDetailActivity extends AppCompatActivity {

    private PhotoDetailAdapter mPhotoDetailAdapter;
    private List<Photo> mPhotos;
    @InjectView(id=R.id.tv_photo_detail_index)
    private TextView mTextView;
    @InjectView(id=R.id.viewpager_photo_detail)
    private ViewPager mViewPager;
    @InjectView(id=R.id.toolbar)
    private Toolbar mToolbar;
    private int mPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        InjectHelper.inject(this);

        setSupportActionBar(mToolbar);

        setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        mPhotos = (List<Photo>) getIntent().getSerializableExtra("photos");
        mPosition = getIntent().getIntExtra("position", 0);
        mTextView.setText(getDisplayText(mPosition, mPhotos.size()));

        mPhotoDetailAdapter = new PhotoDetailAdapter(this, mPhotos);
        mViewPager.setAdapter(mPhotoDetailAdapter);
        mViewPager.setCurrentItem(mPosition);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                mPosition = position;
                mTextView.setText(getDisplayText(position, mPhotos.size()));
            }
        });
    }

    private String getDisplayText(int position, int size) {

        return (position + 1) + "/" + size;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_share) {
            sharePhoto();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 分享照片
    public void sharePhoto() {

        String url = TGRetrofit.getInstance().getTGImgUrl(mPhotoDetailAdapter.getItem(mPosition).getSrc());
        File file = DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache());

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if(file != null) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        } else {
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
        }
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.action_share_title)));
    }
}
