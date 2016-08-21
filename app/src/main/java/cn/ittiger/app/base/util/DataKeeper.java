package cn.ittiger.app.base.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author MaTianyu
 * @date 14-7-10
 */
public class DataKeeper {
    private SharedPreferences sp;
    public static final  String KEY_PK_HOME = "msg_pk_home";
    public static final  String KEY_PK_NEW  = "msg_pk_new";
    private static final String TAG         = DataKeeper.class.getSimpleName();

    public DataKeeper(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * *************** get ******************
     */

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }
    
    public String getString(String key) {
    	return getString(key, "");
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }
    
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }
    
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }
    
    public int getInt(String key) {
    	return getInt(key, 0);
    }

    public long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }
    
    public long getLong(String key) {
    	return getLong(key, 0);
    }

    /**
     * *************** put ******************
     */

    public void put(String key, String value) {
        if (value == null) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().putString(key, value).commit();
        }
    }

    public void put(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public void put(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public void put(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

}
