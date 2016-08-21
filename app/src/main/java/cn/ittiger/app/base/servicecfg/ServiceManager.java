package cn.ittiger.app.base.servicecfg;

import cn.ittiger.app.base.util.ApplicationHelper;
import cn.ittiger.app.tiangou.bean.ModelUrl;
import cn.ittiger.app.tiangou.bean.ServiceCfg;
import cn.ittiger.app.tiangou.bean.ServiceModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.AssetManager;
import android.util.SparseArray;

import java.io.InputStream;

/**
 * @author: laohu on 2016/7/9
 * @site: http://ittiger.cn
 */
public class ServiceManager {

    private static final String TAG_NAME_SERVICE = "Service";
    private static final String TAG_NAME_MODEL = "model";
    private static final String TAG_NAME_URL = "url";

    private static final String ATT_TYPE = "type";
    private static final String ATT_BASEURL = "baseUrl";
    private static final String ATT_IMGURL = "imgUrl";
    private static final String ATT_DESC = "desc";
    private static final String ATT_VALUE = "value";

    private static volatile ServiceManager sServiceManager;
    private SparseArray<ServiceCfg> mCfgSparseArray = new SparseArray<>();
    private int mCurType = 0;

    private ServiceManager() {

    }

    public static ServiceManager getInstance() {

        if(sServiceManager == null) {
            synchronized (ServiceManager.class) {
                if(sServiceManager == null) {
                    sServiceManager = new ServiceManager();
                }
            }
        }
        return sServiceManager;
    }

    public void addServiceCfg(ServiceCfg serviceCfg) {

        mCfgSparseArray.put(serviceCfg.getType(), serviceCfg);
    }

    public ServiceCfg getServiceCfg(int serviceCfgType) {

        return mCfgSparseArray.get(serviceCfgType);
    }

    public void parseServiceCfg() {
        AssetManager assetManager = ApplicationHelper.getInstance().getApplication().getAssets();
        try {
            InputStream is = assetManager.open("service_cfg.xml");
            XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
            pullParser.setInput(is, "UTF-8");

            int event = pullParser.getEventType();
            while(event != XmlPullParser.END_DOCUMENT) {
                switch(event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        startTagHandler(pullParser);
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        endTagHandler(pullParser);
                        break;
                }
                event = pullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTagHandler(XmlPullParser parser) {

        if(TAG_NAME_SERVICE.equals(parser.getName())) {
            mCurType = Integer.parseInt(parser.getAttributeValue(null, ATT_TYPE));
            if(mCurType == ServiceEnum.SERVICE_TG.value()) {//天狗服务
                ServiceCfg serviceCfg = new ServiceCfg();
                serviceCfg.setType(mCurType);
                serviceCfg.setBaseUrl(parser.getAttributeValue(null, ATT_BASEURL));
                serviceCfg.setImgUrl(parser.getAttributeValue(null, ATT_IMGURL));
                addServiceCfg(serviceCfg);
            }
        } else if(TAG_NAME_MODEL.equals(parser.getName())) {//模块
            if(mCurType == ServiceEnum.SERVICE_TG.value()) {//天狗服务
                int type = Integer.parseInt(parser.getAttributeValue(null, ATT_TYPE));
                String desc = parser.getAttributeValue(null, ATT_DESC);
                ServiceModel serviceModel = new ServiceModel(type, desc);
                getServiceCfg(mCurType).addServiceModel(serviceModel);
                getServiceCfg(mCurType).setCurServiceModel(serviceModel);
            }
        } else if(TAG_NAME_URL.equals(parser.getName())) {
            if(mCurType == ServiceEnum.SERVICE_TG.value()) {//天狗服务
                int type = Integer.parseInt(parser.getAttributeValue(null, ATT_TYPE));
                String desc = parser.getAttributeValue(null, ATT_DESC);
                String value = parser.getAttributeValue(null, ATT_VALUE);
                getServiceCfg(mCurType).getCurServiceModel().addModelUrl(new ModelUrl(type, value, desc));
            }
        }
    }

    private void endTagHandler(XmlPullParser parser) {

        if(TAG_NAME_SERVICE.equals(parser.getName())) {
            if(mCurType == ServiceEnum.SERVICE_TG.value()) {//天狗服务
                //none
            }
        } else if(TAG_NAME_MODEL.equals(parser.getName())) {//模块
            if(mCurType == ServiceEnum.SERVICE_TG.value()) {//天狗服务
                getServiceCfg(mCurType).setCurServiceModel(null);
            }
        } else if(TAG_NAME_URL.equals(parser.getName())) {
            if(mCurType == ServiceEnum.SERVICE_TG.value()) {//天狗服务
                //none
            }
        }
    }
}
