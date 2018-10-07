package com.jdry.noticemanagers.mvp.view.activity;

import android.os.Bundle;

import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.mvp.view.custom.TopBarView;

import butterknife.BindView;

/**
 * Created by JDRY-SJM on 2018/2/23.
 */

public class AboutUsActivity extends JDRYBaseActivity {
    @BindView(R.id.top_bar)
    TopBarView topBar;

    @Override
    public int getResouceId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "关于", 0);
    }



}
