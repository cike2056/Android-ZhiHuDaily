#include "com_qf_android_zhihudaily_ndk_API.h"
JNIEXPORT jstring JNICALL Java_com_qf_android_1zhihudaily_ndk_API_getLatestUrl
  (JNIEnv *env, jclass thiz){
	return (*env)->NewStringUTF(env,"http://news-at.zhihu.com/api/3/stories/latest");
}
JNIEXPORT jstring JNICALL Java_com_qf_android_1zhihudaily_ndk_API_getThemesUrl
  (JNIEnv *env, jclass thiz){
	return (*env)->NewStringUTF(env,"http://news-at.zhihu.com/api/3/themes");
}
JNIEXPORT jstring JNICALL Java_com_qf_android_1zhihudaily_ndk_API_getStartImgUrl
  (JNIEnv *env, jclass thiz){
	return (*env)->NewStringUTF(env,"http://news-at.zhihu.com/api/3/start-image/1080*1920");
}
JNIEXPORT jstring JNICALL Java_com_qf_android_1zhihudaily_ndk_API_getBefore
  (JNIEnv *env, jclass thiz){
	return (*env)->NewStringUTF(env,"http://news-at.zhihu.com/api/3/stories/before/%s");
}
