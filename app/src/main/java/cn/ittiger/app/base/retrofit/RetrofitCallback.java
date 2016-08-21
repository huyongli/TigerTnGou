package cn.ittiger.app.base.retrofit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Retrofit异步请求回调
 * @author laohu
 */
public abstract class RetrofitCallback<T> {

    private Type mType;

    public RetrofitCallback() {

        this.mType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public abstract void onSuccess(T result);

    public abstract void onFailure(RetrofitException exception);

    public Type getType() {

        return mType;
    }
}
