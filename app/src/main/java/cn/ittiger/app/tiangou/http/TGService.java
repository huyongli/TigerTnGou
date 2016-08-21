package cn.ittiger.app.tiangou.http;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 照片相关服务接口
 * @author laohu
 */
public interface TGService {

    /**
     * 获取分类列表
     * @return
     */
    @GET("{path}")
    Call<JSONObject> getClassify(@Path("path") String path);

    /**
     * 获取某个分类的所有数据
     * @return
     */
    @GET("{path}")
    Call<JSONObject> getList(@Path("path") String path, @Query("id") String classifyId, @Query("page") String page, @Query("rows") String pageSize);

    @GET("{path}")
    Call<JSONObject> getDetail(@Path("path") String path, @Query("id") String id);
}
