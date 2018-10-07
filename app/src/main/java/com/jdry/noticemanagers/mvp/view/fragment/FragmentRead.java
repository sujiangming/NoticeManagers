package com.jdry.noticemanagers.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.bean.ReadMsgBean;
import com.jdry.noticemanagers.mvp.presenter.ReadMsgPresenter;
import com.jdry.noticemanagers.mvp.view.activity.ActivityDetail;
import com.jdry.noticemanagers.mvp.view.adapter.CommonAdapter;
import com.jdry.noticemanagers.mvp.view.adapter.ViewHolder;
import com.jdry.noticemanagers.utils.GlideImageLoader;
import com.jdry.noticemanagers.utils.JDRYTime;
import com.jdry.noticemanagers.utils.JDRYUtils;
import com.jdry.noticemanagers.utils.JdryHtmlHandler;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentRead extends JDRYBaseFragment {

    @BindView(R.id.lv_read)
    ListView lvRead;
    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout swipeRefreshLayout;

    private CommonAdapter<ReadMsgBean> adapter;
    private List<ReadMsgBean> list = new ArrayList<>();
    private ReadMsgPresenter readMsgPresenter;

    @Override
    public int getResourceId() {
        return R.layout.fragment_read;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(swipeRefreshLayout, false);
        readMsgPresenter = new ReadMsgPresenter(this);
        readMsgPresenter.getReadMsg(getUserMobilePhone());
        initData();
    }

    private void initData() {
        adapter = new CommonAdapter<ReadMsgBean>(getContext(), list, R.layout.read_not_card) {
            @Override
            public void convert(ViewHolder holder, ReadMsgBean info) {
                holder.setText(R.id.tv_title, info.getTitle());
                String content = info.getContent();
                if (TextUtils.isEmpty(content)) {
                    holder.setText(R.id.tv_content, "");
                } else {
                    holder.setText(R.id.tv_content, JdryHtmlHandler.removeHtmlTag(content));
                }
                holder.setText(R.id.tv_time, JDRYTime.transferLongToString(info.getIssueTime(), "yyyy.MM.dd HH:mm:ss"));
                holder.setText(R.id.tv_status, "状态：已读");
                holder.setTextColor(R.id.tv_status, 0xFF209E85);
                ImageView imageView = holder.getView(R.id.iv_msg);
                List<String> imageList = JDRYUtils.getImageSrc(content);
                if (null == imageList || 0 == imageList.size()) {
                    return;
                }
                GlideImageLoader.displayCircleRadius(getContext(), imageList.get(0), imageView, 15);

            }
        };
        lvRead.setAdapter(adapter);
        lvRead.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("flag", 1);
                intent.putExtra("obj", list.get(position));
                openNewActivityByIntent(ActivityDetail.class, intent);
            }
        });
    }


    public <T> void httpSuccess(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        if (commonBean == null || commonBean.getData() == null) {
            toast("暂无已读信息");
            return;
        }
        List<ReadMsgBean> refreshList = JSON.parseArray(commonBean.getData().toString(), ReadMsgBean.class);
        setListData(refreshList);
    }

    public <T> void httpFailure(T t, int order) {
        toast((String) t);
        swipeRefreshLayout.finishRefresh();
    }


    private void setListData(List<ReadMsgBean> refreshList) {
        if (null == refreshList || refreshList.size() == 0) {
            toast("暂无已读信息");
            return;
        }
        list = refreshList;
        adapter.setItems(list);
        swipeRefreshLayout.finishRefresh();
    }

    public void refresh() {
        readMsgPresenter.getReadMsg(getUserMobilePhone());
    }
}
