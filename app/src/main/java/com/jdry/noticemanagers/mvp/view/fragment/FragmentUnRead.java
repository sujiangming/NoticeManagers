package com.jdry.noticemanagers.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.bean.AutoRunBean;
import com.jdry.noticemanagers.bean.ReadMsgBean;
import com.jdry.noticemanagers.global.JDRYApplication;
import com.jdry.noticemanagers.mvp.presenter.UnReadMsgPresenter;
import com.jdry.noticemanagers.mvp.view.activity.ActivityDetail;
import com.jdry.noticemanagers.mvp.view.adapter.CommonAdapter;
import com.jdry.noticemanagers.mvp.view.adapter.ViewHolder;
import com.jdry.noticemanagers.mvp.view.custom.CommonTipDialog;
import com.jdry.noticemanagers.utils.GlideImageLoader;
import com.jdry.noticemanagers.utils.JDRYTime;
import com.jdry.noticemanagers.utils.JDRYUtils;
import com.jdry.noticemanagers.utils.JdryHtmlHandler;
import com.jdry.noticemanagers.utils.OpenLauncherProcessUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentUnRead extends JDRYBaseFragment {
    @BindView(R.id.lv_read)
    ListView lvRead;
    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout swipeRefreshLayout;

    private CommonAdapter<ReadMsgBean> adapter;
    private List<ReadMsgBean> list = new ArrayList<>();
    private UnReadMsgPresenter unReadMsgPresenter;

    private CommonTipDialog selfDialog = null;

    private void showDeleteDialog() {
        selfDialog = new CommonTipDialog(getContext());
        selfDialog.setTitle("温馨提示");
        //selfDialog.setMessage("APP需要您手动打开自启动权限，请您设置下，谢谢！");
        selfDialog.setYesOnclickListener("确定", new CommonTipDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {

                AutoRunBean autoRunBean = JDRYApplication.getDaoSession().getAutoRunBeanDao().queryBuilder().unique();
                if (autoRunBean == null) {
                    autoRunBean = new AutoRunBean();
                }
                autoRunBean.setIsAutoRun(true);
                autoRunBean.setId(1l);
                JDRYApplication.getDaoSession().getAutoRunBeanDao().insertOrReplace(autoRunBean);

                selfDialog.dismiss();
                OpenLauncherProcessUtils.enterWhiteListSetting(getContext());
            }
        });
        //设置点击返回键不消失
        selfDialog.setCanceledOnTouchOutside(false);
        selfDialog.setCancelable(false);
        selfDialog.show();
    }

    /**
     * Jump Start Interface
     * 提示是否跳转设置自启动界面
     */
    private void jumpStartInterface() {
        AutoRunBean autoRunBean = JDRYApplication.getDaoSession().getAutoRunBeanDao().queryBuilder().unique();
        if (autoRunBean == null) {
            showDeleteDialog();
            return;
        }
        boolean isAutoLauncher = autoRunBean.getIsAutoRun();
        if (isAutoLauncher) {
            return;
        }
        showDeleteDialog();
    }


    @Override
    public int getResourceId() {
        return R.layout.fragment_unread;
    }

    @Override
    protected void onCreateViewByMe(Bundle savedInstanceState) {
        initSmartRefreshLayout(swipeRefreshLayout, false);
        unReadMsgPresenter = new UnReadMsgPresenter(this);
        unReadMsgPresenter.getUnReadMsg(getUserMobilePhone());
        initData();
        jumpStartInterface();
    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            ReadMsgBean readMsgBean = new ReadMsgBean();
            readMsgBean.setTitle("测试" + i);
            readMsgBean.setStatus(0);
            readMsgBean.setId("test000000" + i);
            readMsgBean.setContent("<p style=\"margin-bottom: 18px; color: rgb(77, 79, 83); font-family: &quot;Microsoft Yahei&quot;, &quot;\\\\5FAE软雅黑&quot;, &quot;STHeiti Light&quot;, &quot;\\\\534E文细黑&quot;, SimSun, &quot;\\\\5B8B体&quot;, Arial, sans-serif; font-size: 18px; letter-spacing: 1px;\"><span style=\"font-size: 12px;\">\uFEFF</span>习近平在视察79集团军时强调</p><p style=\"margin-bottom: 18px; color: rgb(77, 79, 83); font-family: &quot;Microsoft Yahei&quot;, &quot;\\\\5FAE软雅黑&quot;, &quot;STHeiti Light&quot;, &quot;\\\\534E文细黑&quot;, SimSun, &quot;\\\\5B8B体&quot;, Arial, sans-serif; font-size: 18px; letter-spacing: 1px;\">　　全面加强练兵备战 加快提升打赢能力</p><div class=\"img_wrapper\" style=\"text-align: center; color: rgb(77, 79, 83); font-family: &quot;Microsoft Yahei&quot;, &quot;\\\\5FAE软雅黑&quot;, &quot;STHeiti Light&quot;, &quot;\\\\534E文细黑&quot;, SimSun, &quot;\\\\5B8B体&quot;, Arial, sans-serif; font-size: 18px; letter-spacing: 1px;\"><img src=\"https://n.sinaimg.cn/news/transform/128/w550h378/20180929/0U79-hkmwytp9639203.jpg\" alt=\"9月27日至28日，中共中央总书记、国家主席、中央军委主席习近平视察79集团军，接见驻辽宁部队副师职以上领导干部。这是27日下午，习近平听取79集团军工作汇报，并发表重要讲话。新华社记者李刚摄\" data-mcesrc=\"http://www.xinhuanet.com/politics/leaders/2018-09/29/1123505155_15382188667201n.jpg\" data-mceselected=\"1\" data-link=\"\" style=\"margin: 0px auto; padding: 0px; border-style: none; vertical-align: top; display: block; max-width: 640px;\"><span class=\"img_descr\" style=\"margin: 5px auto; padding: 6px 0px; line-height: 20px; font-size: 16px; display: inline-block; zoom: 1; text-align: left; font-weight: 700;\">　　9月27日至28日，中共中央总书记、国家主席、中央军委主席习近平视察79集团军，接见驻辽宁部队副师职以上领导干部。这是27日下午，习近平听取79集团军工作汇报，并发表重要讲话。新华社记者李刚摄</span></div><p style=\"margin-bottom: 18px; color: rgb(77, 79, 83); font-family: &quot;Microsoft Yahei&quot;, &quot;\\\\5FAE软雅黑&quot;, &quot;STHeiti Light&quot;, &quot;\\\\534E文细黑&quot;, SimSun, &quot;\\\\5B8B体&quot;, Arial, sans-serif; font-size: 18px; letter-spacing: 1px;\">　　新华社沈阳9月29日电（记者李宣良、王天德）中共中央总书记、国家主席、中央军委主席习近平27日视察79集团军，强调要深入贯彻新时代党的强军思想，贯彻新形势下军事战略方针，坚持政治建军、改革强军、科技兴军、依法治军，坚持贯彻实战要求、紧贴使命任务、深化改革转型、扭住官兵主体，全面加强练兵备战，加快提升打赢能力，履行好党和人民赋予的使命任务。</p><p style=\"margin-bottom: 18px; color: rgb(77, 79, 83); font-family: &quot;Microsoft Yahei&quot;, &quot;\\\\5FAE软雅黑&quot;, &quot;STHeiti Light&quot;, &quot;\\\\534E文细黑&quot;, SimSun, &quot;\\\\5B8B体&quot;, Arial, sans-serif; font-size: 18px; letter-spacing: 1px;\">　　金秋时节，辽沈大地天高气爽，满目生机。下午3时许，习近平来到79集团军某陆航旅训练场。他首先听取了部队执行相关任务情况汇报。得知部队官兵大胆解放思想，在指挥协同、战法运用、器材革新等方面进行了许多创新创造，为各项任务完成提供了有力保障，习近平很高兴。</p><div class=\"img_wrapper\" style=\"text-align: center; color: rgb(77, 79, 83); font-family: &quot;Microsoft Yahei&quot;, &quot;\\\\5FAE软雅黑&quot;, &quot;STHeiti Light&quot;, &quot;\\\\534E文细黑&quot;, SimSun, &quot;\\\\5B8B体&quot;, Arial, sans-serif; font-size: 18px; letter-spacing: 1px;\"><img src=\"https://n.sinaimg.cn/news/transform/112/w550h362/20180929/7WFy-hkmwytp9639034.jpg\" alt=\"9月27日至28日，中共中央总书记、国家主席、中央军委主席习近平视察79集团军，接见驻辽宁部队副师职以上领导干部。这是27日下午，习近平登上直-10武装直升机，亲自操控机载武器及观瞄系统，了解有关装备情况。新华社记者李刚摄\" data-mcesrc=\"http://www.xinhuanet.com/politics/leaders/2018-09/29/1123505155_15382188895121n.jpg\" data-mceselected=\"1\" data-link=\"\" style=\"margin: 0px auto; padding: 0px; border-style: none; vertical-align: top; display: block; max-width: 640px;\"><span class=\"img_descr\" style=\"margin: 5px auto; padding: 6px 0px; line-height: 20px; font-size: 16px; display: inline-block; zoom: 1; text-align: left; font-weight: 700;\">　　9月27日至28日，中共中央总书记、国家主席、中央军委主席习近平视察79集团军，接见驻辽宁部队副师职以上领导干部。这是27日下午，习近平登上直-10武装直升机，亲自操控机载武器及观瞄系统，了解有关装备情况。新华社记者李刚摄</span></div><p style=\"margin-bottom: 18px; color: rgb(77, 79, 83); font-family: &quot;Microsoft Yahei&quot;, &quot;\\\\5FAE软雅黑&quot;, &quot;STHeiti Light&quot;, &quot;\\\\534E文细黑&quot;, SimSun, &quot;\\\\5B8B体&quot;, Arial, sans-serif; font-size: 18px; letter-spacing: 1px;\">　　习近平察看了部队主战武器装备。武装直升机技战术性能怎么样？实际操作中好不好用？有没有需要改进的地方？习近平问得很仔细。直-10武装直升机是我国自主研制的第三代战机，习近平登上该型直升机，佩戴专用头盔，亲自操控机载武器及观瞄系统，了解有关装备情况。</p><p style=\"margin-bottom: 18px; color: rgb(77, 79, 83); font-family: &quot;Microsoft Yahei&quot;, &quot;\\\\5FAE软雅黑&quot;, &quot;STHeiti Light&quot;, &quot;\\\\534E文细黑&quot;, SimSun, &quot;\\\\5B8B体&quot;, Arial, sans-serif; font-size: 18px; letter-spacing: 1px;\">　　习近平对实战化军事训练高度重视，他登上机场塔台，实地观看部队行动演练。数十架直升机按战斗编队组成先遣侦察群、火力突击群、机降突击群与空中保障队、火力支援群依次通过塔台，超低空隐蔽机动、分散突防及空中格斗、战术规避突防、战术编队抵近侦察、机降突击、空中近距火力支援等战术课目轮番展开。一时间，战鹰呼啸，铁翼飞旋，特战队员从天而降、迅捷行动。习近平不时拿起望远镜仔细观看，对部队展示出的过硬素质给予肯定。训练结束时，部队指挥员驾驶武装直升机面向塔台摆动机头，以特有的方式向习主席致敬。</p>");
            list.add(readMsgBean);
        }
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
                holder.setText(R.id.tv_status, "状态：未读");
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
                intent.putExtra("flag", 0);
                intent.putExtra("obj", list.get(position));
                openNewActivityByIntent(ActivityDetail.class, intent);
            }
        });
    }

    public <T> void httpSuccess(T t, int order) {
        list = (List<ReadMsgBean>) t;
        adapter.setItems(list);
        swipeRefreshLayout.finishRefresh();
    }

    public <T> void httpFailure(T t, int order) {
        toast((String) t);
        swipeRefreshLayout.finishRefresh();
    }

    @Override
    public void refresh() {
        unReadMsgPresenter.getUnReadMsg(getUserMobilePhone());
    }
}
