package cn.ittiger.app.base.servicecfg;

/**
 * @author: laohu on 2016/7/9
 * @site: http://ittiger.cn
 */
public enum ServiceEnum {
    SERVICE_TG(1);

    private int value;
    ServiceEnum(int value) {
        this.value = value;
    }

    public int value() {

        return value;
    }
}
