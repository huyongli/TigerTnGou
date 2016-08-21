package cn.ittiger.app.base.util;

import cn.ittiger.app.base.app.IDbApplication;

import android.app.Application;

/**
 * 缓存帮助类。请在自定义Application中调用init进行初始化
 * @auther: laohu
 * @time: 2016-2-15下午6:03:32
 */
public final class ApplicationHelper {
	private static final String DATA_KEEPER_FILENAME = "ittiger_cn";
	/**
	 * 
	 */
	private static ApplicationHelper mApplicationHelper;
	/**
	 * 当前应用
	 */
	private Application mApplication;
	/**
	 * 数据首选项存储器
	 */
	private DataKeeper mDataKeeper;
	
	private ApplicationHelper() {
		
	}
	
	public static ApplicationHelper getInstance() {

		if(mApplicationHelper == null) {
			synchronized (ApplicationHelper.class) {
				if(mApplicationHelper == null) {
					mApplicationHelper = new ApplicationHelper();
				}
			}
		}
		return mApplicationHelper;
	}
	
	/**
	 * 对该帮助类进行初始化
	 * @param application
	 */
	public <T extends Application> void init(T application) {

		this.mApplication = application;
		this.mDataKeeper = new DataKeeper(mApplication, DATA_KEEPER_FILENAME);
	}
	
	public Application getApplication() {

		checkApplication();
		return mApplication;
	}
	
	/**
	 * 数据库上下文
	 * @return
	 */
	public IDbApplication getDbApplication() {

		checkApplication();
		if(mApplication instanceof IDbApplication) {
			return (IDbApplication) mApplication;
		}
		throw new NullPointerException("the Application must be implements interface IDbApplication");
	}
	
	/**
	 * 检查是否初始化
	 */
	private void checkApplication() {

		if(mApplication == null) {
			throw new NullPointerException("please invoke method init() in Application.onCreate first");
		}
	}
	
	/**
	 * 获取数据存储器
	 * @return
	 */
	public DataKeeper getDataKeeper() {

		if(mDataKeeper == null) {
			throw new NullPointerException("please invoke method init() in Application.onCreate first");
		}
		return mDataKeeper;
	}

	public void onDestory() {

		this.mApplication = null;
		this.mDataKeeper = null;
	}
}
