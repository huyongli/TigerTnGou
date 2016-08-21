package cn.ittiger.app.tiangou.cook.activity;

import cn.ittiger.app.R;
import cn.ittiger.app.base.activity.BaseActivity;
import cn.ittiger.app.base.inject.InjectHelper;
import cn.ittiger.app.base.inject.InjectView;
import cn.ittiger.app.base.retrofit.RetrofitCallback;
import cn.ittiger.app.base.retrofit.RetrofitException;
import cn.ittiger.app.base.util.HtmlUtil;
import cn.ittiger.app.base.util.ImageLoaderManager;
import cn.ittiger.app.base.util.PicassoUtil;
import cn.ittiger.app.tiangou.bean.IntentHelper;
import cn.ittiger.app.tiangou.cook.bean.CookListItem;
import cn.ittiger.app.tiangou.http.TGRetrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 菜谱详情
 * @author: laohu on 2016/7/4
 * @site: http://ittiger.cn
 */
public class CookDetailActivity extends BaseActivity {
    @InjectView(id=R.id.root)
    private View mRoot;
    @InjectView(id=R.id.tv_cook_ingredients)
    private TextView mCookIngredients;
    @InjectView(id=R.id.tv_cook_practice)
    private TextView mCookPractice;
    @InjectView(id=R.id.iv_cook_image)
    private ImageView mCookImage;
    private int mCookId;

    @Override
    public View getContentView(@Nullable Bundle savedInstanceState) {

        mCookId = getIntent().getIntExtra(IntentHelper.KEY_INTENT_TG_ID, 0);
        setTitle(getIntent().getStringExtra(IntentHelper.KEY_INTENT_TG_NAME));

        View view = LayoutInflater.from(this).inflate(R.layout.activity_cook_detail, null);
        InjectHelper.inject(this, view);
        return view;
    }

    @Override
    public void refreshData() {

        TGRetrofit.getInstance().queryCookDetail(mCookId, new RetrofitCallback<CookListItem>() {

            @Override
            public void onSuccess(CookListItem result) {

                bindView(result);
                refreshSuccess();
            }

            @Override
            public void onFailure(RetrofitException exception) {

                refreshFailed();
            }
        }, String.valueOf(mCookId));
    }

    private void bindView(CookListItem cook) {

        mRoot.setVisibility(View.VISIBLE);
        setTitle(cook.getName());
        mCookIngredients.setText(cook.getFood());
        mCookPractice.setText(HtmlUtil.htmlToText(cook.getMessage()));
//        PicassoUtil.displayPhoto(mCookImage, TGRetrofit.getInstance().getTGImgUrl(cook.getImg()), "cook_detail");
        ImageLoaderManager.getInstance().displayImage(mCookImage, TGRetrofit.getInstance().getTGImgUrl(cook.getImg()));
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        TGRetrofit.getInstance().cancelRequest(String.valueOf(mCookId));
    }
}
