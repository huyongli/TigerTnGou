package cn.ittiger.app.base.app;

import cn.ittiger.app.base.servicecfg.ServiceManager;
import cn.ittiger.app.base.util.ApplicationHelper;
import cn.ittiger.app.base.util.CrashHandler;
import cn.ittiger.app.base.util.ImageLoaderManager;
import cn.ittiger.database.SQLiteDBConfig;

import android.app.Application;

/**
 * @author laohu
 */
public class TigerApplication extends Application implements IDbApplication {
    /**
     * 本地数据库配置
     */
    private SQLiteDBConfig mDBConfig;

    @Override
    public void onCreate() {

        super.onCreate();

        CrashHandler.getInstance().init(getApplicationContext());
        ApplicationHelper.getInstance().init(this);
        ServiceManager.getInstance().parseServiceCfg();
        ImageLoaderManager.init(this);
    }

    @Override
    public SQLiteDBConfig getGlobalDbConfig() {

        if(mDBConfig == null) {
            synchronized (TigerApplication.class) {
                if(mDBConfig == null) {
                    mDBConfig = new SQLiteDBConfig(getApplicationContext());
                    //本地数据库文件保存在应用文件目录
                    mDBConfig.setDbDirectoryPath(getApplicationContext().getCacheDir().getAbsolutePath());
                }
            }
        }
        return mDBConfig;
    }

    public void onDestory() {

        ApplicationHelper.getInstance().onDestory();
        this.mDBConfig = null;
    }
}
