package com.baidu.ueditor.define;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义请求action类型
 * @author hancong03@baidu.com
 *
 */
@SuppressWarnings("serial")
public final class ActionMap {

	public static final Map<String, Integer> mapping;
	// 获取配置请求
	public static final int CONFIG = 0;
	public static final int UPLOAD_IMAGE = 1;
	public static final int UPLOAD_SCRAWL = 2;
	public static final int UPLOAD_VIDEO = 3;
	public static final int UPLOAD_FILE = 4;
	public static final int CATCH_IMAGE = 5;
	public static final int LIST_FILE = 6;
	public static final int LIST_IMAGE = 7;
	
	static {
		mapping = new HashMap<String, Integer>(){{
			put( "config", CONFIG);
			put( "uploadimage", UPLOAD_IMAGE);
			put( "uploadscrawl", UPLOAD_SCRAWL);
			put( "uploadvideo", UPLOAD_VIDEO);
			put( "uploadfile", UPLOAD_FILE);
			put( "catchimage", CATCH_IMAGE);
			put( "listfile", LIST_FILE);
			put( "listimage", LIST_IMAGE);
		}};
	}
	
	public static int getType ( String key ) {
		return mapping.get( key );
	}
	
}
