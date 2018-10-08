package com.jdry.noticemanagers.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.bean.CommonBean;
import com.jdry.noticemanagers.bean.ReadMsgBean;
import com.jdry.noticemanagers.mvp.presenter.ReadMsgDetailPresenter;
import com.jdry.noticemanagers.mvp.view.custom.RichText;
import com.jdry.noticemanagers.rxbus.RxBus;
import com.jdry.noticemanagers.utils.HtmlTextView;
import com.jdry.noticemanagers.utils.JDRYTime;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityDetail extends JDRYBaseActivity {
    @BindView(R.id.tv_video_name)
    TextView tvVideoName;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.rl_comment)
    RelativeLayout rlComment;
    @BindView(R.id.tv_line)
    TextView tvLine;
    @BindView(R.id.tv_brief)
    HtmlTextView tvBrief;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.back_image)
    RichText backImage;
    @BindView(R.id.right_image)
    ImageView rightImage;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.btn_read)
    Button btnRead;

    private ReadMsgDetailPresenter presenter;
    private ReadMsgBean readMsgBean;

    public final static String CSS_STYLE = "<style>* {font-size:16px;line-height:26px;} p {color:#1E1E1E;} a {color:#1E1E1E;} img {max-width:200px;} pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;}</style>";

    @Override
    public int getResouceId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        textTitle.setText("公告详情");
        presenter = new ReadMsgDetailPresenter(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 0);
        readMsgBean = (ReadMsgBean) intent.getSerializableExtra("obj");
        if (flag == 0) {
            btnRead.setVisibility(View.VISIBLE);
        } else {
            btnRead.setVisibility(View.GONE);
        }
        tvVideoName.setText(readMsgBean.getTitle());
        String content = readMsgBean.getContent();
        if (null == content || "".equals(content)) {
            tvBrief.setHtmlFromString("没有内容哦", false);
        } else {
            //tvBrief.loadDataWithBaseURL(null, CSS_STYLE + content, "text/html", "utf-8", null);
            tvBrief.setHtmlFromString(content, false);
        }
        String issueTime = JDRYTime.transferLongToString(readMsgBean.getIssueTime(), "yyyy-MM-dd HH:mm:ss");
        setTextViewValue(tvComment, "发布时间:  " + issueTime);
        setTextViewValue(tvZan, "发布人:  " + readMsgBean.getIssueUserName());

    }

    @OnClick({R.id.back_image, R.id.btn_read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                closeActivity();
                break;
            case R.id.btn_read:
                if (readMsgBean == null || TextUtils.isEmpty(readMsgBean.getNoticeId())) {
                    return;
                }
                presenter.setUnReadMsgStatus(readMsgBean.getId());
                break;
        }
    }

    @Override
    public <T> void httpSuccess(T t, int order) {
        CommonBean commonBean = (CommonBean) t;
        toast(commonBean.getMessage());
        btnRead.setVisibility(View.GONE);
        post(commonBean.getMessage());
    }

    @Override
    public <T> void httpFailure(T t, int order) {
        toast((String) t);
    }

    private void post(String dataBean) {
        RxBus.getDefault().post(dataBean);
    }
}
