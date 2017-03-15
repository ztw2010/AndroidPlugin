package com.small.test.app.historydata.historydata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.small.test.app.historydata.R;
import com.small.test.app.historydata.historydata.data.CategoryVO;
import com.small.test.app.historydata.suspension.SuspensionActivity;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.appstub.mvp.MvpActivity;
import com.small.test.appstub.widget.multiplestatusview.MultipleStatusView;
import com.small.test.appstub.widget.pull2refresh.PullToRefreshBase;
import com.small.test.appstub.widget.pull2refresh.PullToRefreshListView;
import com.small.test.appstub.widget.pull2refresh.PullToRefreshScrollView;

import java.util.List;

/**
 * Created by zhongruan on 2016/12/9.
 */

public class RepairActivity extends MvpActivity<HistoryDataPresenter> implements HistoryContract.View{

    private LinearLayout sysLocationContainer;

    private LinearLayout sysFirstTxtContainer;

    private LinearLayout sysSecondTxtContainer;

    private Button installLocationBtn;

    private Button sysFirstBtn;

    private Button sysSecondBtn;

    private FrameLayout typeChildContainer;

    private PullToRefreshListView allTypeItemRefreshListView;

    private PullToRefreshScrollView mPullToRefreshScrollView;

    private LinearLayout scrollViewContainer;

    private RelativeLayout tipsLayout;

    private MultipleStatusView categoryMultipleStatusView, zongChengMultipleStatusView;

    private ZongChengAdapter zongChengAdapter;

    @Override
    public void showLoading() {
        svProgressHUD.showWithStatus(getString(R.string.loading_data));
    }

    @Override
    public void hideLoading() {
        svProgressHUD.dismissImmediately();
    }

    @Override
    public void showTip(String content) {
        svProgressHUD.showInfoWithStatus(content);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        sysLocationContainer = (LinearLayout) findViewById(R.id.install_location_container);
        sysLocationContainer.setOnClickListener(this);
        sysFirstTxtContainer  = (LinearLayout) findViewById(R.id.sys_first_txt_container);
        sysFirstTxtContainer.setOnClickListener(this);
        sysSecondTxtContainer = (LinearLayout) findViewById(R.id.sys_second_txt_container);
        sysSecondTxtContainer.setOnClickListener(this);
        installLocationBtn = (Button) findViewById(R.id.install_location_btn);
        sysFirstBtn  = (Button) findViewById(R.id.sys_first_btn);
        sysSecondBtn = (Button) findViewById(R.id.sys_second_btn);
        typeChildContainer = (FrameLayout) findViewById(R.id.type_child_container);
        typeChildContainer.setOnClickListener(this);
        allTypeItemRefreshListView = (PullToRefreshListView) findViewById(R.id.type_listview);
        mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.scroll_view);
        scrollViewContainer = (LinearLayout) findViewById(R.id.scroll_view_container);
        tipsLayout = (RelativeLayout) findViewById(R.id.tips_container);
        categoryMultipleStatusView = (MultipleStatusView) findViewById(R.id.category_multiplestatusview);
        categoryMultipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatas(mPresenter.getBtnIndex());
            }
        });
        zongChengMultipleStatusView = (MultipleStatusView) findViewById(R.id.zongcheng_multiplestatusview);
        zongChengMultipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getSysOneAndSysTwoAndZongChengDatas(C.HistoryData.C_INDEX_ZONGCHENG_REFRESH,
                        C.HistoryData.VALUE_CLASSCODE_3,
                        1,
                        1000,
                        true);
            }
        });
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshScrollViewListener());
        allTypeItemRefreshListView.setOnRefreshListener(new PullToRefreshListViewListener());
        allTypeItemRefreshListView.setOnItemClickListener(new PullToRefreshListViewOnItemClickListener());
        zongChengAdapter = new ZongChengAdapter(this);
    }

    @Override
    public void onAddSystemDataView(List<View> views) {
        categoryMultipleStatusView.showContent();
        typeChildContainer.setVisibility(View.VISIBLE);
        scrollViewContainer.removeAllViews();
        for (View cView : views)
        {
            scrollViewContainer.addView(cView);
        }
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onCategoryNoData() {
        typeChildContainer.setVisibility(View.VISIBLE);
        categoryMultipleStatusView.showEmpty();
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onZongChengNoData() {
        zongChengMultipleStatusView.setVisibility(View.VISIBLE);
        typeChildContainer.setVisibility(View.GONE);
        tipsLayout.setVisibility(View.GONE);
        allTypeItemRefreshListView.setVisibility(View.VISIBLE);
        zongChengMultipleStatusView.showEmpty();
    }

    @Override
    public void onSetTabBtnText(String sysLocationBtnText, String sysFirstBtnText, String sysSecondBtnText) {
        installLocationBtn.setText(sysLocationBtnText);
        sysFirstBtn.setText(sysFirstBtnText);
        sysSecondBtn.setText(sysSecondBtnText);
    }

    @Override
    public void onZongChengDataLoaded(List<CategoryVO> categoryDOs) {
        zongChengMultipleStatusView.setVisibility(View.VISIBLE);
        typeChildContainer.setVisibility(View.GONE);
        tipsLayout.setVisibility(View.GONE);
        allTypeItemRefreshListView.setVisibility(View.VISIBLE);
        allTypeItemRefreshListView.setEmptyView(null);
        allTypeItemRefreshListView.onRefreshComplete();
        zongChengAdapter.setLists(categoryDOs);
        allTypeItemRefreshListView.setAdapter(zongChengAdapter);
        zongChengAdapter.notifyDataSetChanged();
        zongChengMultipleStatusView.showContent();
    }

    @Override
    public void onTabSelected(int selectedTabIndex) {
        switch (selectedTabIndex)
        {
            case C.HistoryData.C_INDEX_SYSTYPE:
                sysLocationContainer.setBackgroundResource(R.color.sys_btn_press);
                sysFirstTxtContainer.setBackgroundResource(R.color.sys_color);
                sysSecondTxtContainer.setBackgroundResource(R.color.sys_color);
                break;
            case C.HistoryData.C_INDEX_SYSFIRST:
                sysLocationContainer.setBackgroundResource(R.color.sys_color);
                sysFirstTxtContainer.setBackgroundResource(R.color.sys_btn_press);
                sysSecondTxtContainer.setBackgroundResource(R.color.sys_color);
                break;
            case C.HistoryData.C_INDEX_SYSSECOND:
                sysLocationContainer.setBackgroundResource(R.color.sys_color);
                sysFirstTxtContainer.setBackgroundResource(R.color.sys_color);
                sysSecondTxtContainer.setBackgroundResource(R.color.sys_btn_press);
                break;
            default:
                break;
        }
    }

    private class PullToRefreshScrollViewListener implements PullToRefreshBase.OnRefreshListener2<ScrollView>
    {

        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView)
        {
            getDatas(mPresenter.getBtnIndex());
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView)
        {

        }
    }

    private void getDatas(int btnIndex){
        switch (mPresenter.getBtnIndex())
        {
            case C.HistoryData.C_INDEX_SYSTYPE:
                mPresenter.getSystemLocationDatas(C.HistoryData.C_INDEX_SYSTYPE,
                        C.HistoryData.VALUE_CLASSCODE_0,
                        1,
                        1000,
                        true);
                break;
            case C.HistoryData.C_INDEX_SYSFIRST:
                mPresenter.getSysOneAndSysTwoAndZongChengDatas(C.HistoryData.C_INDEX_SYSFIRST,
                        C.HistoryData.VALUE_CLASSCODE_1,
                        1,
                        1000,
                        true);
                break;
            case C.HistoryData.C_INDEX_SYSSECOND:
                mPresenter.getSysOneAndSysTwoAndZongChengDatas(C.HistoryData.C_INDEX_SYSSECOND,
                        C.HistoryData.VALUE_CLASSCODE_2,
                        1,
                        1000,
                        true);
                break;
            default:
                break;
        }
    }

    private class PullToRefreshListViewListener implements PullToRefreshBase.OnRefreshListener2<ListView>
    {

        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
        {
            mPresenter.getSysOneAndSysTwoAndZongChengDatas(C.HistoryData.C_INDEX_ZONGCHENG_REFRESH,
                    C.HistoryData.VALUE_CLASSCODE_3,
                    1,
                    1000,
                    true);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
        {
        }
    }

    private class PullToRefreshListViewOnItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(RepairActivity.this, SuspensionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(C.HistoryData.KEY_ZONGCHENG_CODE,
                    zongChengAdapter.getLists().get(position - 1).getComponentCode());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void initTitle() {
        if (titleBarView != null){
            backContainer.setVisibility(View.VISIBLE);
            titleBarBackBtn.setVisibility(View.VISIBLE);
            backTxt.setVisibility(View.GONE);
            titleBarTitleTv.setVisibility(View.VISIBLE);
            titleBarTitleTv.setText(getString(R.string.history_data));
            titleBarOtherTv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void goBack() {
        super.goBack();
        finish();
    }

    @Override
    public IBaseView getBaseView() {
        return this;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.install_location_container:
                mPresenter.getSystemLocationDatas(C.HistoryData.C_INDEX_SYSTYPE,
                        C.HistoryData.VALUE_CLASSCODE_0,
                        1,
                        1000,
                        false);
                break;
            case R.id.sys_first_txt_container:
                mPresenter.getSysOneAndSysTwoAndZongChengDatas(C.HistoryData.C_INDEX_SYSFIRST,
                        C.HistoryData.VALUE_CLASSCODE_1,
                        1,
                        1000,
                        false);
                break;
            case R.id.sys_second_txt_container:
                mPresenter.getSysOneAndSysTwoAndZongChengDatas(C.HistoryData.C_INDEX_SYSSECOND,
                        C.HistoryData.VALUE_CLASSCODE_2,
                        1,
                        1000,
                        false);
                break;
            case R.id.type_child_container:
                typeChildContainer.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(zongChengAdapter != null){
            zongChengAdapter.releaseContext();
        }
    }
}
