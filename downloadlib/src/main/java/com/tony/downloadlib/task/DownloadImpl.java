package com.tony.downloadlib.task;

import android.os.Environment;
import android.util.Log;

import com.tony.downloadlib.TDownloadManager;
import com.tony.downloadlib.db.DBProxy;
import com.tony.downloadlib.db.TDBManager;
import com.tony.downloadlib.greendao.DownloadModelDao;
import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: shishaojie
 * Date: 2017/11/20 0020 10:17
 * Description:
 */
public class DownloadImpl extends BaseTask {

    private DownloadModel model;
    private final Vector<DownloadCallbacks> uiListeners;
    private final DownloadModelDao modelDao;
    private boolean mIsCanceled;
    private OkHttpClient client;

    public DownloadImpl(DownloadModel model, Vector<DownloadCallbacks> uiListeners) {
        this.modelDao = TDBManager.getInstance().getDaoSession().getDownloadModelDao();
        this.model = modelDao.load(model.getUrl());
        if (this.model == null) {
            modelDao.insert(model);
            this.model = model;
        }
        this.uiListeners = uiListeners;

        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        Log.d("=T=DownloadImpl", "DownloadImpl: ");
        notifyUIFromWorkThread(DownloadCallbacks.CallbackType.METHOD_ON_WAIT, uiListeners, this.model, null, null);
    }

    @Override
    public void run() {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file;
        long downloadLength = 0;   //记录已经下载的文件长度
        //文件下载地址
        String downloadUrl = model.getUrl();
        //下载文件的名称
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
//        Log.d("=T=DownloadImpl", "run: " + fileName);
        //下载文件存放的目录
        String directory = TDownloadManager.getInstance().getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath();
        DBProxy.updateModelDownloadPath(model, directory + fileName);
//        Log.d("=T=DownloadImpl", "run: " + directory + fileName);
        //创建一个文件
        file = new File(directory + fileName);
        if (file.exists()) {
            //如果文件存在的话，得到文件的大小
            downloadLength = file.length();
        }
        //得到下载内容的大小
        long contentLength = getContentLength(downloadUrl);
        if (contentLength == 0) {
            notifyUIFromWorkThread(DownloadCallbacks.CallbackType.METHOD_ON_FAILED, uiListeners, model, new Exception("请检查网络连接"), null);
            return;
        } else if (contentLength == downloadLength) {
            //已下载字节和文件总字节相等，说明已经下载完成了
            notifyUIFromWorkThread(DownloadCallbacks.CallbackType.METHOD_ON_COMPLETE, uiListeners, model, null, null);
            return;
        } else {
            DBProxy.updateModelTotalSize(model, contentLength);
        }
        /**
         * HTTP请求是有一个Header的，里面有个Range属性是定义下载区域的，它接收的值是一个区间范围，
         * 比如：Range:bytes=0-10000。这样我们就可以按照一定的规则，将一个大文件拆分为若干很小的部分，
         * 然后分批次的下载，每个小块下载完成之后，再合并到文件中；这样即使下载中断了，重新下载时，
         * 也可以通过文件的字节长度来判断下载的起始点，然后重启断点续传的过程，直到最后完成下载过程。
         */
        Request request = new Request.Builder()
                .addHeader("RANGE", "bytes=" + downloadLength + "-")  //断点续传要用到的，指示下载的区间
                .url(downloadUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadLength);//跳过已经下载的字节
                //读写效率，如果只是1k的buffer，下载速度显示在100k左右
                //如果是100k的buffer，下载速度显示在1M左右
                // TODO: 2017/11/22 继续测试
                byte[] buffer = new byte[1024 * 100];
                long total = 0;
                int len;
                while ((len = is.read(buffer)) != -1) {
                    if (isCanceled()) {
                        notifyUIFromWorkThread(DownloadCallbacks.CallbackType.METHOD_ON_CANCELED, uiListeners, model, null, null);
                        break;
                    } else {
                        total += len;
                        savedFile.write(buffer, 0, len);
                        //计算已经下载的百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        DBProxy.updateModelDownloadSize(model, total + downloadLength);
                        notifyUIFromWorkThread(DownloadCallbacks.CallbackType.METHOD_ON_PROGRESS, uiListeners, model, null, progress + "");
                    }

                }
                response.body().close();
                if (!isCanceled()) {
                    notifyUIFromWorkThread(DownloadCallbacks.CallbackType.METHOD_ON_COMPLETE, uiListeners, model, null, null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            notifyUIFromWorkThread(DownloadCallbacks.CallbackType.METHOD_ON_FAILED, uiListeners, model, e, null);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                if (isCanceled() && file != null) {
                    notifyUIFromWorkThread(DownloadCallbacks.CallbackType.METHOD_ON_CANCELED, uiListeners, model, null, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 得到下载内容的大小
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder().url(downloadUrl).build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.body().close();
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean isCanceled() {
        return mIsCanceled;
    }

    public void cancel(boolean isCanceled) {
        this.mIsCanceled = isCanceled;
    }
}
