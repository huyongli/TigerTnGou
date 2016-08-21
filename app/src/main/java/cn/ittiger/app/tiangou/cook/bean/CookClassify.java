package cn.ittiger.app.tiangou.cook.bean;

import cn.ittiger.app.tiangou.bean.Classify;

/**
 * 菜谱分类
 * @author: laohu on 2016/7/3
 * @site: http://ittiger.cn
 */
public class CookClassify extends Classify {
    /**
     * 上级分类ID，0为顶级
     */
    private String cookclass;

    public CookClassify() {

        super();
    }

    public String getCookclass() {

        return cookclass;
    }

    public void setCookclass(String cookclass) {

        this.cookclass = cookclass;
    }
}
