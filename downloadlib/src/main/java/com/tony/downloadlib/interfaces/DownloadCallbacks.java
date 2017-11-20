package com.tony.downloadlib.interfaces;

import com.tony.downloadlib.model.DownloadModel;

import java.io.Serializable;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/11/19
 * Time: 下午8:37
 * Description:
 * 1.客户端被动接受响应接口；
 * 2.对应页面需要实现该接口接受回调；
 * 3.页面创建时需要注册，销毁时需要取消注册；
 */
public interface DownloadCallbacks extends Serializable {
    void callback(DownloadModel model, String... args);

    void callback2(DownloadModel model, Exception e);

    public class CallbackType {
        public static final int METHOD_CALLBACK = 0;
        public static final int METHOD_CALLBACK2 = 1;

        public int methodCode;

    }
}
