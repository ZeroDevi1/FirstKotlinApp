#include <jni.h>
#include <string>
#include <android/log.h>
#include <iostream>
#include "aes_utils.h"
#include "tools.h"
#include "junk.h"
#include <unistd.h>
#include <sys/stat.h>
#include <ctime>
#include <cstdlib>
#include <fcntl.h>
#include <cstdio>
#include <sys/types.h>
#include <sys/stat.h>


#define LOG_TAG  "C_TAG"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)


void exitApp(JNIEnv *env, const _jobject *context);

#ifdef __cplusplus

void exitApp(JNIEnv *env, jobject context) {// 获取context
    jclass context_clazz = env->GetObjectClass(context);
    // 得到 getPackageManager 方法的 ID
    jmethodID methodID_getPackageManager = env->GetMethodID(context_clazz, "getPackageManager",
                                                            "()Landroid/content/pm/PackageManager;");
    // 得到 getPackageName 方法的 ID
    jmethodID methodID_getPackageName = env->GetMethodID(context_clazz, "getPackageName",
                                                         "()Ljava/lang/String;");
    // 获得当前应用的包名
    jobject application_package_obj = env->CallObjectMethod(context, methodID_getPackageName);
    jstring application_package = static_cast<jstring>(application_package_obj);
    // 获取 AppUtils
    jclass appUtils_clazz = env->FindClass("com/xuexiang/xutil/app/AppUtils");
    // 获取方法 exitApp
    jmethodID exitApp_methodId = env->GetStaticMethodID(appUtils_clazz, "exitApp", "()V");
    // 执行方法
    env->CallStaticVoidMethod(appUtils_clazz, exitApp_methodId);
}

extern "C" {
#endif
JNIEXPORT jint JNICALL
Java_com_zerodevi1_tools_AesTools_method02(JNIEnv *env, jobject thiz, jobject context) {
    // key : 密钥文件打开
    int key;
    key = open("/sdcard/key.txt", O_RDONLY);
    LOGD("key:%d", key);
    if (key == -1) {
        LOGD("密钥文件不存在,生成ID...");
        // 关闭key
        close(key);
        // 保存设备ID
        int id;
        id = open("/sdcard/id.txt", O_WRONLY | O_CREAT | O_TRUNC, 0644);
        if (id != -1) {
            LOGD("开始执行设备ID保存...");
            // 调用 PhoneIdHelper 获取密钥
            jclass phoneIdHelper_clazz = env->FindClass(
                    "com/mobile/mobilehardware/uniqueid/PhoneIdHelper");
            // 获取 getPsuedoUniqueID() 方法的ID
            jmethodID methodId_getPsuedoUniqueID = env->GetStaticMethodID(phoneIdHelper_clazz,
                                                                          "getUniqueID",
                                                                          "()Ljava/lang/String;");
            jobject uniqueId = env->CallStaticObjectMethod(phoneIdHelper_clazz,
                                                           methodId_getPsuedoUniqueID);
            jstring uniqueIdStr = static_cast<jstring>(uniqueId);
            const char *uniqueIdChar = env->GetStringUTFChars(uniqueIdStr, 0);
            LOGD("uniqueIdChar:%s", uniqueIdChar);
            write(id, uniqueIdChar, strlen(uniqueIdChar));
            close(id);
        }
        exitApp(env, context);
    } else {
        char str[255] = "";
        read(key, str, 255);
        LOGD("buf:%s", str);

        char *result = AES_128_CBC_PKCS5_Decrypt(str);
        LOGD("密钥: %s", result);
        // 调用 PhoneIdHelper 获取密钥
        jclass phoneIdHelper_clazz = env->FindClass(
                "com/mobile/mobilehardware/uniqueid/PhoneIdHelper");
        // 获取 getPsuedoUniqueID() 方法的ID
        jmethodID methodId_getPsuedoUniqueID = env->GetStaticMethodID(phoneIdHelper_clazz,
                                                                      "getUniqueID",
                                                                      "()Ljava/lang/String;");
        jobject uniqueId = env->CallStaticObjectMethod(phoneIdHelper_clazz,
                                                       methodId_getPsuedoUniqueID);
        jstring uniqueIdStr = static_cast<jstring>(uniqueId);
        const char *uniqueIdChar = env->GetStringUTFChars(uniqueIdStr, 0);
        LOGD("uniqueIdStr:%s", uniqueIdChar);
        int cmpResult = strcmp(result, uniqueIdChar);
        if (cmpResult != 0) {
            exitApp(env, context);
        } else {
            LOGD("密钥相等");
        }
        close(key);
        return cmpResult;
    }
    return -1;
}


#ifdef __cplusplus
}
#endif

#ifdef __cplusplus
uint16_t CRC_TA[16] = {0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7, 0x8108,
                       0x9129,
                       0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef};

uint16_t Crc16(uint16_t crc_ini, const uint8_t *buf, uint32_t len) {
    uint16_t crc = crc_ini;
    uint8_t da;
    while (len-- != 0) {
        da = ((uint8_t) (crc / 256)) / 16;
        crc <<= 0x04;
        crc ^= CRC_TA[da ^ (*buf / 16)];
        da = ((uint8_t) (crc / 256)) / 16;
        crc <<= 0x04;
        crc ^= CRC_TA[da ^ (*buf & 0x0f)];
        buf++;
    }
    return (crc);
}

uint16_t CrcOne(uint8_t buf, uint16_t crx) {
    uint8_t da;
    da = ((uint8_t) (crx / 256)) / 16;
    crx <<= 0x04;
    crx ^= CRC_TA[da ^ (buf / 16)];
    da = ((uint8_t) (crx / 256)) / 16;
    crx <<= 0x04;
    crx ^= CRC_TA[da ^ (buf & 0x0f)];
    return (crx);
}

//jstring to uint8_t*

uint8_t *jstringTostring(JNIEnv *env, jstring jstr) {

    uint8_t *rtn = NULL;

    jclass clsstring = env->FindClass("java/lang/String");

    jstring strencode = env->NewStringUTF("utf-8");

    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");

    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);

    jsize alen = env->GetArrayLength(barr);

    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);

    if (alen > 0) {

        rtn = (uint8_t *) malloc(alen + 1);

        memcpy(rtn, ba, alen);

        rtn[alen] = 0;

    }

    env->ReleaseByteArrayElements(barr, ba, 0);

    return rtn;

}


extern "C"
#endif
JNIEXPORT jint JNICALL
Java_com_zerodevi1_tools_AesTools_crc16(JNIEnv *env, jobject thiz, jobject context,
                                               jstring data) {
    uint8_t *str = jstringTostring(env, data);
    return Crc16(0, str, strlen(reinterpret_cast<const char *const>(str)));

#ifdef __cplusplus
}
#endif