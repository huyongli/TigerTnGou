package cn.ittiger.app.tiangou.fragment;

import cn.ittiger.app.base.fragment.BaseFragment;
import cn.ittiger.app.tiangou.type.TGModelEnum;

import android.os.Bundle;

/**
 * @author: laohu on 2016/7/4
 * @site: http://ittiger.cn
 */
public class FragmentFactory {
    public static final String KEY_BUNDLE_MODEL_ENUM = "classifyType";
    public static final String KEY_BUNDLE_CLASSIFY_ID = "classifyId";

    public static BaseFragment createClassifyFragment(TGModelEnum classifyType) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_MODEL_ENUM, classifyType);

        BaseFragment fragment = new ClassifyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static BaseFragment createListFragment(TGModelEnum classifyType, int classifyId) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_MODEL_ENUM, classifyType);
        bundle.putInt(KEY_BUNDLE_CLASSIFY_ID, classifyId);

        BaseFragment fragment = new ListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
