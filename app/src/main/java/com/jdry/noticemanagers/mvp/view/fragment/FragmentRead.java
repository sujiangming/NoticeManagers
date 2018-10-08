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
    @BindView(R.id.view_no_data)
    View viewNoData;

    private CommonAdapter<ReadMsgBean> adapter;
    private List<ReadMsgBean> list = new ArrayList<>();
    private List<ReadMsgBean> listFormat = new ArrayList<>();
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
        adapter = new CommonAdapter<ReadMsgBean>(getContext(), listFormat, R.layout.read_not_card) {
            @Override
            public void convert(ViewHolder holder, ReadMsgBean info) {
                holder.setText(R.id.tv_title, info.getTitle());
                String content = info.getContent();
                if (TextUtils.isEmpty(content)) {
                    holder.setText(R.id.tv_content, "");
                } else {
                    holder.setText(R.id.tv_content, content);
                }
                holder.setText(R.id.tv_time, JDRYTime.transferLongToString(info.getIssueTime(), "yyyy.MM.dd HH:mm:ss"));
                holder.setText(R.id.tv_status, "状态：已读");
                holder.setTextColor(R.id.tv_status, 0xFF209E85);
                ImageView imageView = holder.getView(R.id.iv_msg);
                String imageUrl = info.getImgUrl();
                if (TextUtils.isEmpty(imageUrl)) {
                    imageView.setImageResource(R.drawable.xft);
                } else {
                    GlideImageLoader.displayCircleRadius(getContext(), imageUrl, imageView, 15);
                }
            }
        };
        lvRead.setAdapter(adapter);
        lvRead.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("flag", 1);
                ReadMsgBean readMsgBean = list.get(position);
                intent.putExtra("obj", readMsgBean);
                openNewActivityByIntent(ActivityDetail.class, intent);
            }
        });
    }


    public <T> void httpSuccess(T t, int order) {
        swipeRefreshLayout.finishRefresh();
        CommonBean commonBean = (CommonBean) t;
        if (commonBean == null) {
            toast("暂无已读信息");
            viewNoData.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            return;
        }

        List<ReadMsgBean> refreshList = JSON.parseArray(commonBean.getData().toString(), ReadMsgBean.class);

        if (null == refreshList) {
            viewNoData.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            return;
        }

        if (0 == refreshList.size()) {
            viewNoData.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            return;
        }

        viewNoData.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);

        //先清空
        list.clear();
        listFormat.clear();

        for (int i = 0; i < refreshList.size(); i++) {

            ReadMsgBean info = refreshList.get(i);

            //原始报文保存起来
            ReadMsgBean oldBean = saveOld(info);
            list.add(oldBean);

            String content = info.getContent();
            if (!TextUtils.isEmpty(content)) {
                String newContent = JdryHtmlHandler.removeHtmlTag(content);
                info.setContent(newContent);

                List<String> imageList = JDRYUtils.getImageSrc(content);
                if (null != imageList && imageList.size() > 0) {
                    info.setImgUrl(imageList.get(0));
                }
            }

            listFormat.add(info);
        }

        adapter.setItems(listFormat);
    }

    private ReadMsgBean saveOld(ReadMsgBean info){
        ReadMsgBean newRead = new ReadMsgBean();
        newRead.setContent(info.getContent());
        newRead.setId(info.getId());
        newRead.setStatus(info.getStatus());
        newRead.setTitle(info.getTitle());
        newRead.setAppUserId(info.getAppUserId());
        newRead.setIssueTime(info.getIssueTime());
        newRead.setIssueUserName(info.getIssueUserName());
        newRead.setNoticeId(info.getNoticeId());
        newRead.setReadMark(info.getReadMark());
        newRead.setReadTime(info.getReadTime());

        return newRead;
    }

    public <T> void httpFailure(T t, int order) {
        toast((String) t);
        swipeRefreshLayout.finishRefresh();

        viewNoData.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    public void refresh() {
        readMsgPresenter.getReadMsg(getUserMobilePhone());
    }
}
