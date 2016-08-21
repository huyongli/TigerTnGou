package cn.ittiger.app.tiangou.type;

/**
 * 天狗服务模块枚举
 * @author: laohu on 2016/7/8
 * @site: http://ittiger.cn
 */
public enum TGModelEnum {
    MODEL_NONE(0),
    /**
     * 照片类别
     */
    MODEL_PHOTO(1),
    /**
     * 菜谱类别
     */
    MODEL_COOK(2),
    /**
     * 健康知识
     */
    MODEL_HEALTH_LORE(3);

    private int value;
    TGModelEnum(int value) {

        this.value = value;
    }

    public int value() {

        return value;
    }
}
