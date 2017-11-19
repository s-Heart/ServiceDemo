package com.tony.downloadlib.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tony on 2017/11/18.
 */
@Entity
public class DownloadModel {
    @Id
    private String url;
    private int downloadState;
    private int totalSize;
    private int downloadSize;
    private String downloadPath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownloadPath() {
        return this.downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public int getDownloadSize() {
        return this.downloadSize;
    }

    public void setDownloadSize(int downloadSize) {
        this.downloadSize = downloadSize;
    }

    public int getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getDownloadState() {
        return this.downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    @Generated(hash = 1945830206)
    public DownloadModel(String url, int downloadState, int totalSize,
            int downloadSize, String downloadPath) {
        this.url = url;
        this.downloadState = downloadState;
        this.totalSize = totalSize;
        this.downloadSize = downloadSize;
        this.downloadPath = downloadPath;
    }

    @Generated(hash = 1665448439)
    public DownloadModel() {
    }

    //region builder

    private DownloadModel(Builder builder) {
        setUrl(builder.url);
        setDownloadState(builder.downloadState);
        setTotalSize(builder.totalSize);
        setDownloadSize(builder.downloadSize);
        setDownloadPath(builder.downloadPath);
    }


    public static final class Builder {
        private String url;
        private int downloadState;
        private int totalSize;
        private int downloadSize;
        private String downloadPath;

        public Builder() {
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder downloadState(int val) {
            downloadState = val;
            return this;
        }

        public Builder totalSize(int val) {
            totalSize = val;
            return this;
        }

        public Builder downloadSize(int val) {
            downloadSize = val;
            return this;
        }

        public Builder downloadPath(String val) {
            downloadPath = val;
            return this;
        }

        public DownloadModel build() {
            return new DownloadModel(this);
        }
    }

    //endregion
}
