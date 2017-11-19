package com.tony.downloadlib.interfaces;

import com.tony.downloadlib.model.DownloadModel;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/11/19
 * Time: 下午8:37
 * Description:
 * 1.客户端被动接受响应接口；
 * 2.对应页面需要实现该接口接受回调；
 * 3.页面创建时需要注册，销毁时需要取消注册；
 */
public interface DownloadCallbacks {
    void callback(DownloadModel model);
}
