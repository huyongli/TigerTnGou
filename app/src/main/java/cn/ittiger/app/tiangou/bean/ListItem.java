package cn.ittiger.app.tiangou.bean;

/**
 * @author: laohu on 2016/7/4
 * @site: http://ittiger.cn
 */
public abstract class ListItem {
    private int id;
    private int count ;//访问次数
    private int fcount;//收藏数
    private int rcount;//评论读数
    /**
     * 图库封面img，字段返回的是不完整的图片路径src
     * 需要在前面添加【http://tnfs.tngou.net/image】或者【http://tnfs.tngou.net/img】
     */
    private String img;

    public int getId() {

        return id;
    }

    public int getCount() {

        return count;
    }

    public int getFcount() {

        return fcount;
    }

    public int getRcount() {

        return rcount;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setCount(int count) {

        this.count = count;
    }

    public void setFcount(int fcount) {

        this.fcount = fcount;
    }

    public void setRcount(int rcount) {

        this.rcount = rcount;
    }

    public String getImg() {

        return img;
    }

    public void setImg(String img) {

        this.img = img;
    }

    public abstract String getName();
}
