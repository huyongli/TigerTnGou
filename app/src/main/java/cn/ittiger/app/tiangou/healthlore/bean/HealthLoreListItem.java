package cn.ittiger.app.tiangou.healthlore.bean;

import cn.ittiger.app.tiangou.bean.ListItem;

/**
 * 菜谱分类列表展示项
 * @author: laohu on 2016/7/4
 * @site: http://ittiger.cn
 */
public class HealthLoreListItem extends ListItem {
    private String title;//名称
    private String description;//描述
    private String keywords;//关键字
    private String time;//发布时间
    private int loreclass;//分类
    private String message;//信息

    public HealthLoreListItem() {

    }

    @Override
    public String getName() {

        return title;
    }

    public String getDescription() {

        return description;
    }

    public String getKeywords() {

        return keywords;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setKeywords(String keywords) {

        this.keywords = keywords;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public int getLoreclass() {

        return loreclass;
    }

    public void setLoreclass(int loreclass) {

        this.loreclass = loreclass;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}
