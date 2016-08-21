package cn.ittiger.app.tiangou.type;

/**
 * 天狗服务模块的不同服务枚举
 * @author: laohu on 2016/7/9
 * @site: http://ittiger.cn
 */
public enum TGUrlEnum {

    /**
     * 照片类别
     */
    URL_CLASSIFY(1),
    /**
     * 菜谱类别
     */
    URL_LIST(2),
    /**
     * 健康知识
     */
    URL_DETAIL(3);

    private int value;
    TGUrlEnum(int value) {

        this.value = value;
    }

    public int value() {

        return value;
    }
}
