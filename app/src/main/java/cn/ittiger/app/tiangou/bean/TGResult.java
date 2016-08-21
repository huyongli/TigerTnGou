package cn.ittiger.app.tiangou.bean;

import cn.ittiger.app.tiangou.photo.bean.PhotoClassify;

import java.util.List;

/**
 * 天狗API结果
 * @author: laohu on 2016/7/3
 * @site: http://ittiger.cn
 */
public class TGResult {
    private String status;
    private List<PhotoClassify> tngou;

    public TGResult() {}

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public List<PhotoClassify> getTngou() {

        return tngou;
    }

    public void setTngou(List<PhotoClassify> tngou) {

        this.tngou = tngou;
    }
}
