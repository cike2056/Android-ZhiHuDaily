package com.qf.android_zhihudaily.ndk;

public class API {
	static {
		System.loadLibrary("api");
	}
	public static native String getLatestUrl();/**今日热闻*/
	public static native String getThemesUrl();/**主题*/
	public static native String getStartImgUrl();/**欢迎动画*/
	public static native String getBefore();
	/**新闻回顾<br />
	 * 返回的字符串需要使用String.format();方法格式化<br />
	 * 例如：String.format(API.getBefore(), "20141201");
	*/
}
