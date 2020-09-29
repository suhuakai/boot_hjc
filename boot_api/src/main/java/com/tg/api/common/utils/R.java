package com.tg.api.common.utils;


import java.util.HashMap;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 200);
		put("msg", "success");
		put("data","");
	}
	
	public static R error() {
		return error("500", "未知异常！");
	}
	
	public static R error(String msg) {
		return error("201", msg);
	}
	
	public static R error(String code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}



	public static R ok(String msg, Object obj) {
		R r = new R();
		r.put("msg", msg);
		r.put("data", obj);
		return r;
	}
	
	public static R ok(Object obj) {
		R r = new R();
		r.put("data", obj);
		return r;
	}


	public static R ok(String code , String msg, Object obj) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		r.put("data",obj.toString());
		return r;
	}


	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
