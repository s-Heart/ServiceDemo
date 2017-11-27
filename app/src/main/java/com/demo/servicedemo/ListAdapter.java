package com.demo.servicedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tony.downloadlib.model.DownloadModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>Author: tony(shishaojie@koolearn.com)
 * <br/>Date: 2017/11/27 0027
 * <br/>Time: 14:55
 * <br/>Description:
 * <br/>FIXME
 */

class ListAdapter extends BaseAdapter {
    private Context context;
    private List<DownloadModel> datas = new ArrayList<>();

    public ListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.tvUrl = (TextView) convertView.findViewById(R.id.tv_url);
            viewHolder.tvFileName = (TextView) convertView.findViewById(R.id.tv_file_name);
            viewHolder.tvTotalSize = (TextView) convertView.findViewById(R.id.tv_total_size);
            viewHolder.tvDownloadSize = (TextView) convertView.findViewById(R.id.tv_download_size);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvUrl.setText("url:" + datas.get(position).getUrl());
        viewHolder.tvFileName.setText("fileName:" + datas.get(position).getFileName());
        viewHolder.tvTotalSize.setText("totalSize:" + datas.get(position).getTotalSize());
        viewHolder.tvDownloadSize.setText("downloadSize:" + datas.get(position).getDownloadSize());

        return convertView;
    }

    public void setData(List<DownloadModel> models) {
        this.datas.clear();
        this.datas.addAll(models);
        notifyDataSetChanged();
    }

    public void notifyByModel(DownloadModel model) {
        for (DownloadModel data : this.datas) {
            if (data.getUrl().equals(model.getUrl())) {
                data.setDownloadSize(model.getDownloadSize());
                break;
            }
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView tvUrl;
        TextView tvFileName;
        TextView tvTotalSize;
        TextView tvDownloadSize;
    }
}
