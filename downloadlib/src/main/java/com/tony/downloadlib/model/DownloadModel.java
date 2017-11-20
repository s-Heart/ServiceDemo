package com.tony.downloadlib.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by tony on 2017/11/18.
 */
@Entity
public class DownloadModel implements Serializable {
    @Id
    private String url;
    private long downloadState;
    private long totalSize;
    private long downloadSize;
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


    public void setDownloadSize(long downloadSize) {
        this.downloadSize = downloadSize;
    }


    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }


    public void setDownloadState(long downloadState) {
        this.downloadState = downloadState;
    }

    public long getDownloadSize() {
        return this.downloadSize;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public long getDownloadState() {
        return this.downloadState;
    }

    @Generated(hash = 1986461061)
    public DownloadModel(String url, long downloadState, long totalSize,
                         long downloadSize, String downloadPath) {
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
        private long downloadState;
        private long totalSize;
        private long downloadSize;
        private String downloadPath;

        public Builder() {
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder downloadState(long val) {
            downloadState = val;
            return this;
        }

        public Builder totalSize(long val) {
            totalSize = val;
            return this;
        }

        public Builder downloadSize(long val) {
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
