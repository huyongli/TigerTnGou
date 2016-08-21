package cn.ittiger.app.tiangou.healthlore.activity;

import cn.ittiger.app.R;
import cn.ittiger.app.base.activity.BaseActivity;
import cn.ittiger.app.base.inject.InjectHelper;
import cn.ittiger.app.base.inject.InjectView;
import cn.ittiger.app.base.retrofit.RetrofitCallback;
import cn.ittiger.app.base.retrofit.RetrofitException;
import cn.ittiger.app.base.util.HtmlUtil;
import cn.ittiger.app.base.util.ImageLoaderManager;
import cn.ittiger.app.base.util.PicassoUtil;
import cn.ittiger.app.base.util.UIUtil;
import cn.ittiger.app.tiangou.bean.IntentHelper;
import cn.ittiger.app.tiangou.healthlore.bean.HealthLoreListItem;
import cn.ittiger.app.tiangou.http.TGRetrofit;

import com.litesuits.android.async.AsyncTask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HealthLoreDetailActivity extends BaseActivity {

    @InjectView(id=R.id.root)
    private LinearLayout mRoot;
    @InjectView(id=R.id.tv_healthlore_description)
    private TextView mHealthLoreDescription;
    @InjectView(id=R.id.tv_healthlore_keywords)
    private TextView mHealthLoreKeyword;
    @InjectView(id=R.id.tv_healthlore_content)
    private TextView mHealthLoreContent;
    @InjectView(id=R.id.iv_healthlore_image)
    private ImageView mHealthLoreImage;
    private int mHealthLoreId;

    @Override
    public View getContentView(@Nullable Bundle savedInstanceState) {

        mHealthLoreId = getIntent().getIntExtra(IntentHelper.KEY_INTENT_TG_ID, 0);
        setTitle(getIntent().getStringExtra(IntentHelper.KEY_INTENT_TG_NAME));

        View view = LayoutInflater.from(this).inflate(R.layout.activity_health_lore_detail, null);
        InjectHelper.inject(this, view);
        return view;
    }

    @Override
    public void refreshData() {

        TGRetrofit.getInstance().queryHealthLoreDetail(mHealthLoreId, new RetrofitCallback<HealthLoreListItem>() {

            @Override
            public void onSuccess(HealthLoreListItem result) {

                bindView(result);
                refreshSuccess();
            }

            @Override
            public void onFailure(RetrofitException exception) {

                refreshFailed();
            }
        }, String.valueOf(mHealthLoreId));
    }

    private void bindView(final HealthLoreListItem healthLoreListItem) {

        mRoot.setVisibility(View.VISIBLE);
        setTitle(healthLoreListItem.getName());
        mHealthLoreKeyword.setText(healthLoreListItem.getKeywords());
        mHealthLoreDescription.setText(healthLoreListItem.getDescription());
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                final Spanned spanned = HtmlUtil.htmlToText(healthLoreListItem.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHealthLoreContent.setText(spanned);
                    }
                });
            }
        });
        String imgUrl = TGRetrofit.getInstance().getTGImgUrl(healthLoreListItem.getImg());
        ImageLoaderManager.getInstance().displayImage(mHealthLoreImage, imgUrl);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        TGRetrofit.getInstance().cancelRequest(String.valueOf(mHealthLoreId));
    }
}
