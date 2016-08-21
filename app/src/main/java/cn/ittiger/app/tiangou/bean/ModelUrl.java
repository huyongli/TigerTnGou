package cn.ittiger.app.tiangou.bean;

import java.io.Serializable;

/**
 * 服务具体功能的地址
 * @author: laohu on 2016/7/9
 * @site: http://ittiger.cn
 */
public class ModelUrl implements Serializable{
    /**
     * 功能服务的类型
     */
    private int mType;
    /**
     * 功能服务描述
     */
    private String mDesc;
    /**
     * 功能地址
     */
    private String mUrl;

    public ModelUrl(int type, String url, String desc) {

        mType = type;
        mDesc = desc;
        mUrl = url;
    }

    @Override
    public String toString() {

        return "{type:" + mType + ", desc:" + mDesc + ", url:" + mUrl + "}";
    }

    public int getType() {

        return mType;
    }

    public void setType(int type) {

        mType = type;
    }

    public String getDesc() {

        return mDesc;
    }

    public void setDesc(String desc) {

        mDesc = desc;
    }

    public String getUrl() {

        return mUrl;
    }

    public void setUrl(String url) {

        mUrl = url;
    }
}
