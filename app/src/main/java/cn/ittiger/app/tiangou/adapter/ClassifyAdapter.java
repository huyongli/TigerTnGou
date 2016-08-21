package cn.ittiger.app.tiangou.adapter;

import cn.ittiger.app.tiangou.bean.Classify;
import cn.ittiger.app.tiangou.fragment.FragmentFactory;
import cn.ittiger.app.tiangou.type.TGModelEnum;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 分类数据适配器
 * @author laohu
 */
public class ClassifyAdapter extends FragmentPagerAdapter {

    private TGModelEnum mModelEnum;
    private List<? extends Classify> mList;

    public ClassifyAdapter(FragmentManager fm, List<? extends Classify> list, TGModelEnum mModelEnum) {

        super(fm);
        this.mList = list;
        this.mModelEnum = mModelEnum;
    }

    @Override
    public Fragment getItem(int position) {

        return FragmentFactory.createListFragment(mModelEnum, mList.get(position).getId());
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mList.get(position).getName();
    }

    @Override
    public int getCount() {

        return mList.size();
    }

    public Classify getItemData(int position) {

        return mList.get(position);
    }
}
