package cn.ittiger.app.base.retrofit;

import android.text.TextUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Retrofit请求管理(主要是同步和异步请求后的具体结果解析)
 * @author laohu
 */
public class RetrofitManager {

    private static RetrofitManager sRetrofitManager;
    private ParserConfig mParserConfig;
    private HashMap<String, Call> mCallHashMap;

    private RetrofitManager() {

        mCallHashMap = new HashMap<>();
    }

    public void setParserConfig(ParserConfig parserConfig) {

        mParserConfig = parserConfig;
    }

    public static RetrofitManager getInstance() {

        if(sRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if(sRetrofitManager == null) {
                    sRetrofitManager = new RetrofitManager();
                }
            }
        }
        return sRetrofitManager;
    }

    /**
     * 同步请求
     * @param call
     * @param type  获取的单个数据类型，Javabean的Class对象
     * @param <T>   获取的数据结果，T可以为type的对象或者是List<type>
     * @return
     * @throws RetrofitException
     */
    public <T> T syncExecute(Call<JSONObject> call, Class type, String tag) throws RetrofitException {

        try {
            addCall(tag, call);
            Response<JSONObject> response = call.execute();

            T result = parser(response, type, false);
            if(result == null) {
                throw new RetrofitException("the request response result is null");
            }
            removeCall(tag);
            return result;
        } catch (Exception e) {
            removeCall(tag);
            if(e instanceof RetrofitException) {
                throw (RetrofitException)e;
            } else {
                throw new RetrofitException(e);
            }
        }
    }

    /**
     * 异步请求
     * @param call
     * @param callback
     * @param <T>   T可以为type的对象或者是List<type>
     */
    public <T> void asyncExecute(Call<JSONObject> call, final RetrofitCallback<T> callback, final String tag) {

        addCall(tag, call);
        call.enqueue(new Callback<JSONObject>() {

            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                try {

                    removeCall(tag);
                    T result = parser(response, callback.getType(), true);
                    if(result == null) {
                        throw new RetrofitException("the request response result is null");
                    }
                    if(callback != null) {
                        callback.onSuccess(result);
                    }
                } catch (RetrofitException retrofitExeception) {

                    if(callback != null) {
                        callback.onFailure(retrofitExeception);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

                removeCall(tag);
                if(callback != null) {
                    callback.onFailure(new RetrofitException(t));
                }
            }
        });
    }

    private void addCall(String tag, Call call) {

        if(!TextUtils.isEmpty(tag)) {
            mCallHashMap.put(tag, call);
        }
    }

    /**
     * 请求结束后，移除此请求
     * @param tag
     */
    private void removeCall(String tag) {

        mCallHashMap.remove(tag);
    }

    /**
     * 取消某个未结束的请求
     * @param tag
     */
    public void cancelCall(String tag) {

        if(mCallHashMap.containsKey(tag)) {
            mCallHashMap.get(tag).cancel();
        }
    }

    /**
     * 根据结果状态解析请求结果
     * @param response      请求结果响应体
     * @param type          需要获取的数据类型
     * @param isAsync       是否为异步请求
     * @param <T>
     * @return
     * @throws RetrofitException
     */
    private <T> T parser(Response<JSONObject> response, Type type, boolean isAsync) throws RetrofitException {
        try {
            if(!response.isSuccessful()) {
                throw new RetrofitException("request failed");
            }
            JSONObject jsonObject = response.body();
            if(jsonObject == null) {
                throw new RetrofitException("request failed, because the result is null");
            }
            //请求失败
            if(!mParserConfig.StatusSuccesseValue.equals(jsonObject.getString(mParserConfig.StatusColumn))) {
                String error = jsonObject.getString(mParserConfig.ErrorColumn);
                throw new RetrofitException(TextUtils.isEmpty(error) ? "request result, please check the api or params is right ?" : error);
            }

            String stringResult;
            if(TextUtils.isEmpty(mParserConfig.ResultColumn)) {//没有设置结果字段，则默认以当前JSON为结果集
                stringResult = jsonObject.toString();
            } else {
                stringResult = jsonObject.getString(mParserConfig.ResultColumn);
            }
            if(isAsync) {//异步请求
                T bean = Json.getGson().fromJson(stringResult, type);
                return bean;
            }

            //解析同步请求的具体结果
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(stringResult);
            if(jsonElement.isJsonArray()) {//结果是JSON数组
                List list = new ArrayList();
                JsonArray array = jsonElement.getAsJsonArray();
                for(JsonElement json : array) {
                    list.add(Json.getGson().fromJson(json, type));
                }
                return (T) list;
            } else {//不是JSON数组
                return Json.getGson().fromJson(jsonElement, type);
            }
        } catch (Exception e) {
            if(e instanceof RetrofitException) {
                throw (RetrofitException)e;
            } else {
                throw new RetrofitException(e);
            }
        }
    }

    public static class ParserConfig {
        /**
         * 请求状态字段，代表请求是否成功
         */
        public String StatusColumn;
        /**
         * 请求成功时的状态值
         */
        public String StatusSuccesseValue;
        /**
         * 请求结果字段
         */
        public String ResultColumn;
        /**
         * 请求失败信息字段
         */
        public String ErrorColumn;

        public ParserConfig clone() {

            ParserConfig config = new ParserConfig();
            config.StatusColumn = StatusColumn;
            config.StatusSuccesseValue = StatusSuccesseValue;
            config.ResultColumn = ResultColumn;
            config.ErrorColumn = ErrorColumn;
            return config;
        }
    }

}
