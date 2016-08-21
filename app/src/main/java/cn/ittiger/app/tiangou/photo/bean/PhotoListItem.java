package cn.ittiger.app.tiangou.photo.bean;

import cn.ittiger.app.tiangou.bean.ListItem;

/**
 * 照片图库(每个照片分类中有多个照片图库)
 * @author laohu
 */
public class PhotoListItem extends ListItem {
    /**
     * 图库所属分类id
     */
    private int  galleryclass;
    /**
     * 图库的标题
     */
    private String title;
    /**
     * 图库有多少张图片
     */
    private int size;

    public int getGalleryclass() {

        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {

        this.galleryclass = galleryclass;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public int getSize() {

        return size;
    }

    public void setSize(int size) {

        this.size = size;
    }

    @Override
    public String getName() {

        return title;
    }
}
