package com.qf.android_zhihudaily.ndk;

public class API {
	static {
		System.loadLibrary("api");
	}
	public static native String getLatestUrl();/**��������*/
	public static native String getThemesUrl();/**����*/
	public static native String getStartImgUrl();/**��ӭ����*/
	public static native String getBefore();
	/**���Żع�<br />
	 * ���ص��ַ�����Ҫʹ��String.format();������ʽ��<br />
	 * ���磺String.format(API.getBefore(), "20141201");
	*/
}
