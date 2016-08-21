package cn.ittiger.app.tiangou.bean;

import android.util.SparseArray;

/**
 * @author: laohu on 2016/7/9
 * @site: http://ittiger.cn
 */
public class ServiceCfg {

    private int mType;
    private String mBaseUrl;
    private String mImgUrl;
    private SparseArray<ServiceModel> mModelSparseArray = new SparseArray<>();
    private ServiceModel mCurServiceModel;

    public void addServiceModel(ServiceModel serviceModel) {

        mModelSparseArray.put(serviceModel.getType(), serviceModel);
    }

    public ServiceModel getServiceModel(int serviceModelType) {

        if(mModelSparseArray.indexOfKey(serviceModelType) == -1) {
            throw new IllegalArgumentException("not exist this ServiceModel for type is " + serviceModelType);
        }
        return this.mModelSparseArray.get(serviceModelType);
    }

    public int getType() {

        return mType;
    }

    public void setType(int type) {

        mType = type;
    }

    public String getBaseUrl() {

        return mBaseUrl;
    }

    public void setBaseUrl(String baseUrl) {

        mBaseUrl = baseUrl;
    }

    public String getImgUrl() {

        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {

        mImgUrl = imgUrl;
    }

    public ServiceModel getCurServiceModel() {

        return mCurServiceModel;
    }

    public void setCurServiceModel(ServiceModel curServiceModel) {

        mCurServiceModel = curServiceModel;
    }
}
