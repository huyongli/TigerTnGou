package cn.ittiger.app.tiangou.util;

import cn.ittiger.app.R;
import cn.ittiger.app.tiangou.bean.Classify;
import cn.ittiger.app.tiangou.bean.ListItem;
import cn.ittiger.app.tiangou.cook.activity.CookDetailActivity;
import cn.ittiger.app.tiangou.cook.bean.CookClassify;
import cn.ittiger.app.tiangou.cook.bean.CookListItem;
import cn.ittiger.app.tiangou.healthlore.activity.HealthLoreDetailActivity;
import cn.ittiger.app.tiangou.healthlore.bean.HealthLoreClassify;
import cn.ittiger.app.tiangou.healthlore.bean.HealthLoreListItem;
import cn.ittiger.app.tiangou.photo.activity.PhotoGalleryActivity;
import cn.ittiger.app.tiangou.photo.bean.PhotoClassify;
import cn.ittiger.app.tiangou.photo.bean.PhotoListItem;
import cn.ittiger.app.tiangou.type.TGModelEnum;

import android.content.Context;

/**
 * 模块与class之间的映射
 * @author: laohu on 2016/7/10
 * @site: http://ittiger.cn
 */
public class ModelClassMapUtil {

    public static Class<? extends Classify> getClassifyClaxx(TGModelEnum modelEnum) {

        switch (modelEnum) {
            case MODEL_PHOTO:
                return PhotoClassify.class;
            case MODEL_COOK:
                return CookClassify.class;
            case MODEL_HEALTH_LORE:
                return HealthLoreClassify.class;
        }
        return null;
    }

    public static Class<? extends ListItem> getListClaxx(TGModelEnum modelEnum) {

        switch (modelEnum) {
            case MODEL_PHOTO:
                return PhotoListItem.class;
            case MODEL_COOK:
                return CookListItem.class;
            case MODEL_HEALTH_LORE:
                return HealthLoreListItem.class;
        }
        return null;
    }

    public static Class getDetailClaxx(TGModelEnum modelEnum) {

        switch (modelEnum) {
            case MODEL_PHOTO:
                return PhotoGalleryActivity.class;
            case MODEL_COOK:
                return CookDetailActivity.class;
            case MODEL_HEALTH_LORE:
                return HealthLoreDetailActivity.class;
        }
        return null;
    }

    public static String getPullUpLoadText(Context context, TGModelEnum modelEnum) {

        switch (modelEnum) {
            case MODEL_PHOTO:
                return context.getString(R.string.load_more_beauty);
            case MODEL_COOK:
                return context.getString(R.string.load_more_cook);
            case MODEL_HEALTH_LORE:
                return context.getString(R.string.load_more_health_lore);
        }
        return context.getString(R.string.load_more_txt);
    }
}
