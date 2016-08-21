package cn.ittiger.app.tiangou.bean;

import cn.ittiger.database.annotation.PrimaryKey;

/**
 * 分类
 * @author: laohu on 2016/7/3
 * @site: http://ittiger.cn
 */
public class Classify {
    /**
     * 分类id，照片分类表的主键
     */
    @PrimaryKey
    private int id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类标题
     */
    private String title;
    /**
     * 分类关键词
     */
    private String keywords;
    /**
     * 分类描述
     */
    private String description;
    /**
     * 分类的排序，从0。。。。10开始
     */
    private int seq;

    public Classify() {

    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getKeywords() {

        return keywords;
    }

    public void setKeywords(String keywords) {

        this.keywords = keywords;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public int getSeq() {

        return seq;
    }

    public void setSeq(int seq) {

        this.seq = seq;
    }
}
