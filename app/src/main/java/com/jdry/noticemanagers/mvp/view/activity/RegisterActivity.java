package com.jdry.noticemanagers.mvp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jdry.noticemanagers.R;
import com.jdry.noticemanagers.bean.DeptBean;
import com.jdry.noticemanagers.mvp.presenter.DeptPresenter;
import com.jdry.noticemanagers.mvp.presenter.RegisterPresenter;
import com.jdry.noticemanagers.mvp.view.custom.TopBarView;
import com.jdry.noticemanagers.utils.JDRYUtils;
import com.jdry.noticemanagers.utils.MD5Util;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JDRY-SJM on 2018/1/30.
 */

public class RegisterActivity extends JDRYBaseActivity {

    @BindView(R.id.top_bar)
    TopBarView topBar;
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.tv_user_dept)
    Spinner spinner1;
    @BindView(R.id.btnRegister)
    Button tvLogin;

    @OnClick(R.id.btnRegister)
    public void onViewClicked() {

        if (spinnerBeanSelected == null || spinnerBeanSelected.getId() == null
                || spinnerBeanSelected.getId().equals("all")) {
            toast("请选择部门");
            return;
        }

        String phone = etUserPhone.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }

        if(!JDRYUtils.isMobile(phone)){
            toast("请输入正确的手机号");
            return;
        }

        String userName = etUserName.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            toast("请输入用户名");
            return;
        }

        String pwd = etUserPwd.getText().toString();

        if (TextUtils.isEmpty(pwd)) {
            toast("请输入用户密码");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobilePhone", phone);
        jsonObject.put("password", MD5Util.encrypt(pwd));
        jsonObject.put("name", JDRYUtils.URLEncode(userName));
        jsonObject.put("deptId", spinnerBeanSelected.getId());

        registerPresenter.register(jsonObject.toJSONString());
    }

    private DeptPresenter deptPresenter;
    private RegisterPresenter registerPresenter;

    @Override
    public int getResouceId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreateByMe(Bundle savedInstanceState) {
        setTopBar(topBar, "注册", 0);
        deptPresenter = new DeptPresenter(this);
        registerPresenter = new RegisterPresenter(this);

        deptPresenter.getDept();
    }

    @Override
    public <T> void httpSuccess(T t, int order) {
        if (order == 1) { //获取部门接口返
            List<DeptBean> deptBean = (List<DeptBean>) t;
            initSpinnerByArea(deptBean);
        } else {
            toast((String) t);
            closeActivity();
        }
    }

    @Override
    public <T> void httpFailure(T t, int order) {
        if (order == 1) { //获取部门接口返回
            toast("获取部门信息失败~");
            initFailSpinnerByArea();
        } else {
            toast("注册失败~");
        }
    }

    private DeptBean spinnerBeanSelected = null;

    private void initSpinnerByArea(List<DeptBean> list) {
        DeptBean[] spinnerBeans = null;
        if (null == list || 0 == list.size()) {
            spinnerBeans = new DeptBean[1];
            DeptBean deptBean = new DeptBean();
            deptBean.setId("all");
            deptBean.setName("请选择部门");
            deptBean.setDesc("请选择部门");
            spinnerBeans[0] = deptBean;
        } else {
            spinnerBeans = new DeptBean[list.size() + 1];
            for (int i = 0; i < spinnerBeans.length; ++i) {

                if (i == 0) {
                    DeptBean deptBean = new DeptBean();
                    deptBean.setId("all");
                    deptBean.setName("请选择部门");
                    deptBean.setDesc("请选择部门");
                    spinnerBeans[0] = deptBean;
                } else {
                    //如果是超级管理员的话 就增加全部地区，否则就不显示
                    int j = i - 1;
                    DeptBean deptBean = new DeptBean();
                    deptBean.setOrder(list.get(j).getOrder());
                    deptBean.setId(list.get(j).getId());
                    deptBean.setName(list.get(j).getName());
                    deptBean.setDesc(list.get(j).getDesc());
                    spinnerBeans[i] = deptBean;
                }
            }
        }

        setSpinnerByArea(spinner1, spinnerBeans);
    }

    private void initFailSpinnerByArea() {
        DeptBean[] spinnerBeans = new DeptBean[1];
        DeptBean deptBean = new DeptBean();
        deptBean.setId("all");
        deptBean.setName("请选择部门");
        deptBean.setDesc("请选择部门");
        spinnerBeans[0] = deptBean;
        setSpinnerByArea(spinner1, spinnerBeans);
    }

    private void setSpinnerByArea(final Spinner spinner, final DeptBean[] spinnerBeanList) {
        ArrayAdapter<DeptBean> mArrayAdapter = new ArrayAdapter<DeptBean>(this, R.layout.trans_activity_spinner, spinnerBeanList) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.trans_activity_spinner_item, parent, false);
                }
                TextView spinnerText = convertView.findViewById(R.id.trans_spinner_textView);
                spinnerText.setText(getItem(position).getName());

                return convertView;
            }
        };
        spinner.setAdapter(mArrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                textView.setTextColor(0xFF353535);
                textView.setText(spinnerBeanList[position].getName());
                spinnerBeanSelected = spinnerBeanList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
