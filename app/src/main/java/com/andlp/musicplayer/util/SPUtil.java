package com.andlp.musicplayer.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtil {
	public SPUtil() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}


	public static final String FILE_NAME = "ttpod";


	private static String SP_NAME = FILE_NAME;


	public static String getSP_NAME() {

		return SP_NAME;
	}


	public static void setSP_NAME(String name) {
		SP_NAME = name;
	}


	public void defauleSP_NAME() {

		SP_NAME = FILE_NAME;

	}


	public static void put(Context context, String key, Object object) {

		SharedPreferences sp = context.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}


	public static Object get(Context context, String key, Object defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);

		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}


	public static void remove(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}


	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);//
	}


	public static boolean contains(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}


	public static Map<String, ?> getAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}


	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();


		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}


		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

}