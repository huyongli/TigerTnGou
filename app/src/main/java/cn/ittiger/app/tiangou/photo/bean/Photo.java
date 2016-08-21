package cn.ittiger.app.tiangou.photo.bean;

import java.io.Serializable;

/**
 * @author laohu
 */
public class Photo implements Serializable {
    private int id;
    private int gallery; //图片库
    private String src; //图片地址

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getGallery() {

        return gallery;
    }

    public void setGallery(int gallery) {

        this.gallery = gallery;
    }

    public String getSrc() {

        return src;
    }

    public void setSrc(String src) {

        this.src = src;
    }
}
