package cn.ittiger.app.tiangou.bean;

import android.util.SparseArray;

/**
 * 服务模块
 * @author: laohu on 2016/7/9
 * @site: http://ittiger.cn
 */
public class ServiceModel {
    /**
     * 服务模块的类型
     */
    private int mType;
    /**
     * 服务模块描述
     */
    private String mDesc;
    /**
     * 当前服务模块下的所有服务地址
     */
    private SparseArray<ModelUrl> mUrls = new SparseArray<>();

    public ServiceModel(int type, String desc) {

        mType = type;
        mDesc = desc;
    }

    public int getType() {

        return mType;
    }

    public void addModelUrl(ModelUrl modelUrl) {

        this.mUrls.put(modelUrl.getType(), modelUrl);
    }

    public ModelUrl getModelUrl(int modelUrlType) {

        if(mUrls.indexOfKey(modelUrlType) == -1) {
            throw new IllegalArgumentException("not exist this ModelUrl for type is " + modelUrlType);
        }
        return this.mUrls.get(modelUrlType);
    }
}
