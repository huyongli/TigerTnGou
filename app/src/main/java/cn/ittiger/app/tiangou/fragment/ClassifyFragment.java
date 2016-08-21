package cn.ittiger.app.tiangou.fragment;

import cn.ittiger.app.R;
import cn.ittiger.app.base.db.DBManager;
import cn.ittiger.app.base.fragment.BaseFragment;
import cn.ittiger.app.base.inject.InjectHelper;
import cn.ittiger.app.base.inject.InjectView;
import cn.ittiger.app.tiangou.bean.Classify;
import cn.ittiger.app.tiangou.adapter.ClassifyAdapter;
import cn.ittiger.app.tiangou.http.TGRetrofit;
import cn.ittiger.app.tiangou.type.TGModelEnum;
import cn.ittiger.app.tiangou.util.ModelClassMapUtil;

import com.litesuits.android.async.SimpleTask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Tab分类Fragment
 * @author: laohu on 2016/7/3
 * @site: http://ittiger.cn
 */
public class ClassifyFragment extends BaseFragment {

    private static final String CALL_TAG = "ClassifyFragment";

    private TGModelEnum mModelEnum;
    @InjectView(id=R.id.viewpager_classify)
    private ViewPager mViewPager;
    @InjectView(id=R.id.indicator_classify)
    private TabLayout mTabPageIndicator;
    private ClassifyAdapter mClassifyAdapter;
    private String mCallTag = CALL_TAG;

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable Bundle savedInstanceState) {

        mModelEnum = (TGModelEnum) getArguments().getSerializable(FragmentFactory.KEY_BUNDLE_MODEL_ENUM);
        mCallTag = CALL_TAG + mModelEnum.value();

        View view = inflater.inflate(R.layout.fragment_classify_layout, null);
        InjectHelper.inject(this, view);

        mTabPageIndicator.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActivity().setTitle(mClassifyAdapter.getItemData(position).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @Override
    public void refreshData() {

        SimpleTask<List<? extends Classify>> task = new SimpleTask<List<? extends Classify>>() {

            @Override
            protected List<? extends Classify> doInBackground() {

                return queryClassify();
            }

            @Override
            protected void onPostExecute(List<? extends Classify> list) {

                super.onPostExecute(list);
                if(list == null) {
                    refreshFailed();
                } else {//数据加载成功
                    if(list.size() > 0) {
                        getActivity().setTitle(list.get(0).getName());
                    }
                    //此处使用getChildFragmentManager()解决因主Fragment布局相同导致缓存相同从而在显示子Fragment时错乱的问题
                    mClassifyAdapter = new ClassifyAdapter(getChildFragmentManager(), list, mModelEnum);
                    mViewPager.setAdapter(mClassifyAdapter);
                    mTabPageIndicator.setVisibility(View.VISIBLE);
                    mTabPageIndicator.setupWithViewPager(mViewPager);

                    refreshSuccess();
                }
            }
        };
        task.execute();
    }

    public List<? extends Classify> queryClassify() {

        Class claxx = ModelClassMapUtil.getClassifyClaxx(mModelEnum);
        List<? extends Classify> classifies;
        long total = DBManager.getInstance().getSQLiteDB().queryTotal(claxx);
        if(total > 0) {
            classifies = DBManager.getInstance().getSQLiteDB().queryAll(claxx);
        } else {
            classifies = TGRetrofit.getInstance().queryClassify(mModelEnum, mCallTag);
            if(classifies != null) {//第一次加载的数据保存到本地数据库
                DBManager.getInstance().getSQLiteDB().save(classifies);
            }
        }
        return classifies;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        TGRetrofit.getInstance().cancelRequest(mCallTag);
    }
}
