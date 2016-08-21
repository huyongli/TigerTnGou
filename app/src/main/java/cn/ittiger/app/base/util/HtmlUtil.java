package cn.ittiger.app.base.util;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author: laohu on 2016/7/8
 * @site: http://ittiger.cn
 */
public class HtmlUtil {

    public static Spanned htmlToText(String html) {
        return Html.fromHtml(html, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {

                Drawable drawable = null;
                try {
                    URL url = new URL(source);
                    drawable = Drawable.createFromStream(url.openStream(), "");
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return drawable;
            }
        }, null);
    }
}
