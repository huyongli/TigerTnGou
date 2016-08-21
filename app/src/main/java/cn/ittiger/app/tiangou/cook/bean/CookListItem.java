package cn.ittiger.app.tiangou.cook.bean;

import cn.ittiger.app.tiangou.bean.ListItem;

/**
 * 菜谱分类列表展示项
 * @author: laohu on 2016/7/4
 * @site: http://ittiger.cn
 */
public class CookListItem extends ListItem {
    private String name;//名称
    private String  food;//食物
    private String images;//图片,
    private String description;//描述
    private String keywords;//关键字
    private String message;//资讯内容

    public CookListItem() {

    }

    @Override
    public String getName() {

        return name;
    }

    public String getFood() {

        return food;
    }

    public String getImages() {

        return images;
    }

    public String getDescription() {

        return description;
    }

    public String getKeywords() {

        return keywords;
    }

    public String getMessage() {

        return message;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setFood(String food) {

        this.food = food;
    }

    public void setImages(String images) {

        this.images = images;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setKeywords(String keywords) {

        this.keywords = keywords;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}
