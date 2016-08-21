package cn.ittiger.app.base.util;

/**
 * @author: laohu on 2016/7/10
 * @site: http://ittiger.cn
 */
public interface Callback<T> {

    void onSuccess(T result);

    void onFailure(Exception exception);
}
