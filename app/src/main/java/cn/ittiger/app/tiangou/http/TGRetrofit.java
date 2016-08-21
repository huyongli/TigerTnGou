package cn.ittiger.app.tiangou.http;

import android.util.Log;

import cn.ittiger.app.base.servicecfg.ServiceEnum;
import cn.ittiger.app.base.servicecfg.ServiceManager;
import cn.ittiger.app.base.retrofit.RetrofitCallback;
import cn.ittiger.app.base.retrofit.RetrofitException;
import cn.ittiger.app.base.retrofit.RetrofitManager;
import cn.ittiger.app.base.retrofit.converter.JsonConverterFactory;
import cn.ittiger.app.tiangou.bean.Classify;
import cn.ittiger.app.tiangou.bean.ListItem;
import cn.ittiger.app.tiangou.bean.ServiceCfg;
import cn.ittiger.app.tiangou.cook.bean.CookListItem;
import cn.ittiger.app.tiangou.healthlore.bean.HealthLoreListItem;
import cn.ittiger.app.tiangou.photo.bean.Photo;
import cn.ittiger.app.tiangou.type.TGModelEnum;
import cn.ittiger.app.tiangou.type.TGUrlEnum;
import cn.ittiger.app.tiangou.util.ModelClassMapUtil;
import retrofit2.Retrofit;

import java.util.List;

/**
 * 照片相关请求服务
 * @author laohu
 */
public class TGRetrofit {

    private static final String TAG = "TGRetrofit";

    private static TGRetrofit sPhotoRetrofit;

    private TGService mTGService;

    private RetrofitManager.ParserConfig mParserConfig;

    private ServiceCfg mServiceCfg;

    private TGRetrofit() {

        mServiceCfg = ServiceManager.getInstance().getServiceCfg(ServiceEnum.SERVICE_TG.value());

        mParserConfig = new RetrofitManager.ParserConfig();
        mParserConfig.StatusColumn = "status";
        mParserConfig.StatusSuccesseValue = "true";
        mParserConfig.ResultColumn = "tngou";
        mParserConfig.ErrorColumn = "";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mServiceCfg.getBaseUrl())
                .addConverterFactory(JsonConverterFactory.create())//使用自定义转换器将结果转换成JSON对象
                .build();

        mTGService = retrofit.create(TGService.class);
    }

    public static TGRetrofit getInstance() {

        if(sPhotoRetrofit == null) {
            synchronized (TGRetrofit.class) {
                if(sPhotoRetrofit == null) {
                    sPhotoRetrofit = new TGRetrofit();
                }
            }
        }
        return sPhotoRetrofit;
    }

    public String getTGImgUrl(String url) {

        return mServiceCfg.getImgUrl() + url;
    }

    public List<? extends Classify> queryClassify(TGModelEnum modelEnum, String tag) {

        try {
            String url = mServiceCfg.getServiceModel(modelEnum.value()).getModelUrl(TGUrlEnum.URL_CLASSIFY.value()).getUrl();
            RetrofitManager.getInstance().setParserConfig(mParserConfig);
            Class claxx = ModelClassMapUtil.getClassifyClaxx(modelEnum);
            return RetrofitManager.getInstance().syncExecute(mTGService.getClassify(url), claxx, tag);
        } catch (RetrofitException exception) {
            Log.d(TAG, exception.getMessage());
            return null;
        }
    }

    public List<ListItem> queryList(TGModelEnum modelEnum, int classifyId, int page, int pageSize, String tag) {

        try {
            String url = mServiceCfg.getServiceModel(modelEnum.value()).getModelUrl(TGUrlEnum.URL_LIST.value()).getUrl();
            RetrofitManager.getInstance().setParserConfig(mParserConfig);
            Class claxx = ModelClassMapUtil.getListClaxx(modelEnum);
            return RetrofitManager.getInstance().syncExecute(mTGService.getList(url, String.valueOf(classifyId),
                    String.valueOf(page), String.valueOf(pageSize)), claxx, tag);
        } catch (RetrofitException exception) {
            Log.d(TAG, exception.getMessage());
            return null;
        }
    }

    /**
     * 获取某个图库的照片列表
     * @param galleryId
     * @param callback
     */
    public void queryGalleryPhotos(int galleryId, RetrofitCallback<List<Photo>> callback, String tag) {

        RetrofitManager.ParserConfig config = mParserConfig.clone();
        config.ResultColumn = "list";
        RetrofitManager.getInstance().setParserConfig(config);

        String url = mServiceCfg.getServiceModel(TGModelEnum.MODEL_PHOTO.value()).getModelUrl(TGUrlEnum.URL_DETAIL.value()).getUrl();
        RetrofitManager.getInstance().asyncExecute(mTGService.getDetail(url, String.valueOf(galleryId)), callback, tag);
    }

    /**
     * 异步获取菜谱详情
     * @param cookId
     * @param callback
     */
    public void queryCookDetail(int cookId, RetrofitCallback<CookListItem> callback, String tag) {

        RetrofitManager.ParserConfig parserConfig = new RetrofitManager.ParserConfig();
        parserConfig.StatusColumn = "status";
        parserConfig.StatusSuccesseValue = "true";
        parserConfig.ResultColumn = "";
        parserConfig.ErrorColumn = "";
        RetrofitManager.getInstance().setParserConfig(parserConfig);
        String url = mServiceCfg.getServiceModel(TGModelEnum.MODEL_COOK.value()).getModelUrl(TGUrlEnum.URL_DETAIL.value()).getUrl();
        RetrofitManager.getInstance().asyncExecute(mTGService.getDetail(url, String.valueOf(cookId)), callback, tag);
    }

    /**
     * 获取某个健康知识的详情
     * @param healthLoreId
     * @param callback
     * @param tag
     */
    public void queryHealthLoreDetail(int healthLoreId, RetrofitCallback<HealthLoreListItem> callback, String tag) {

        RetrofitManager.ParserConfig parserConfig = new RetrofitManager.ParserConfig();
        parserConfig.StatusColumn = "status";
        parserConfig.StatusSuccesseValue = "true";
        parserConfig.ResultColumn = "";
        parserConfig.ErrorColumn = "";
        RetrofitManager.getInstance().setParserConfig(parserConfig);
        String url = mServiceCfg.getServiceModel(TGModelEnum.MODEL_HEALTH_LORE.value()).getModelUrl(TGUrlEnum.URL_DETAIL.value()).getUrl();
        RetrofitManager.getInstance().asyncExecute(mTGService.getDetail(url, String.valueOf(healthLoreId)), callback, tag);
    }

    /**
     * 取消指定tags的请求
     * @param tags
     */
    public void cancelRequest(String ... tags) {

        for(String tag : tags) {
            RetrofitManager.getInstance().cancelCall(tag);
        }
    }
}
