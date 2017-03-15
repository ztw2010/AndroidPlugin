package com.small.test.app.historydata.suspension;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.small.test.app.historydata.R;
import com.small.test.app.historydata.causeFailure.CauseFailureActivity;
import com.small.test.app.historydata.historydata.RepairActivity;
import com.small.test.app.historydata.suspension.data.FaultListVO;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.appstub.mvp.MvpActivity;
import com.small.test.appstub.widget.multiplestatusview.MultipleStatusView;
import com.small.test.appstub.widget.pull2refresh.PullToRefreshBase;
import com.small.test.appstub.widget.pull2refresh.PullToRefreshListView;

import java.util.List;

public class SuspensionActivity extends MvpActivity<SuspensionPresenter> implements SuspensionContract.View
{
    private PullToRefreshListView allTypeItemRefreshListView;
    
    private FaultListAdapter faultListAdapter;

    private MultipleStatusView multipleStatusView;
    
    private String zongChengCode = "";

    @Override
    protected int getLayoutId() {
        return R.layout.acitivty_suspension;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null)
        {
            zongChengCode = intent.getStringExtra(C.HistoryData.KEY_ZONGCHENG_CODE);
        }
        multipleStatusView = (MultipleStatusView) findViewById(R.id.zongcheng_multiplestatusview);
        allTypeItemRefreshListView = (PullToRefreshListView) findViewById(R.id.type_listview);
        allTypeItemRefreshListView.setOnRefreshListener(new PullToRefreshListViewListener());
        allTypeItemRefreshListView.setOnItemClickListener(new PullToRefreshListViewOnItemClickListener());
        faultListAdapter = new FaultListAdapter(this);
        mPresenter.getSuspensionList(zongChengCode, C.Suspension.VALUE_SERVICE_MANUAL, 1, 1000, false);
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getSuspensionList(zongChengCode, C.Suspension.VALUE_SERVICE_MANUAL, 1, 1000, true);
            }
        });
    }

    @Override
    protected void initTitle() {
        if (titleBarView != null)
        {
            titleBarBackBtn.setVisibility(View.VISIBLE);
            backTxt.setVisibility(View.VISIBLE);
            backTxt.setText(getString(R.string.close));
            titleBarTitleTv.setVisibility(View.VISIBLE);
            titleBarTitleTv.setText(getString(R.string.fault_descriptioin));
            titleBarOtherTv.setVisibility(View.GONE);
        }
    }

    @Override
    public IBaseView getBaseView()
    {
        return this;
    }

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

    private class PullToRefreshListViewListener implements PullToRefreshBase.OnRefreshListener2<ListView>
    {
        
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
        {
            mPresenter.getSuspensionList(zongChengCode, C.Suspension.VALUE_SERVICE_MANUAL, 1, 1000, true);
        }
        
        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
        {
            
        }
        
    }
    
    private class PullToRefreshListViewOnItemClickListener implements OnItemClickListener
    {
        
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(SuspensionActivity.this, CauseFailureActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(C.Suspension.PARAMS_ASSEMBLYID, zongChengCode);
            bundle.putString(C.Suspension.KEY_PHENOMENON,
                    faultListAdapter.getLists().get(position - 1).getPhenomenon());
            bundle.putString(C.Suspension.KEY_MANUALID, faultListAdapter.getLists().get(position - 1).getManualId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
        
    }
    
    @Override
    public void goBack()
    {
        super.goBack();
        Intent intent = new Intent(this, RepairActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
    
    @Override
    public void onGetSuspensionList(List<FaultListVO> faultListVOs)
    {
        allTypeItemRefreshListView.onRefreshComplete();
        faultListAdapter.setLists(faultListVOs);
        faultListAdapter.notifyDataSetChanged();
        allTypeItemRefreshListView.setAdapter(faultListAdapter);
        multipleStatusView.showContent();
    }
    
    @Override
    public void onSuspensionNoDatas()
    {
        allTypeItemRefreshListView.onRefreshComplete();
        multipleStatusView.showEmpty();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        faultListAdapter.releaseContext();
    }
}
