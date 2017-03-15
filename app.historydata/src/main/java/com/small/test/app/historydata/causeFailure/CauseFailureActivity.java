package com.small.test.app.historydata.causeFailure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.small.test.app.historydata.R;
import com.small.test.app.historydata.causeFailure.data.CauseFailureVO;
import com.small.test.app.historydata.suspension.SuspensionActivity;
import com.small.test.app.historydata.treatmentmeasures.TreatmentMeasuresActivity;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.appstub.mvp.MvpActivity;
import com.small.test.appstub.widget.multiplestatusview.MultipleStatusView;
import com.small.test.appstub.widget.pull2refresh.PullToRefreshBase;
import com.small.test.appstub.widget.pull2refresh.PullToRefreshListView;

import java.util.List;

public class CauseFailureActivity extends MvpActivity<CauseFailurePresenter> implements CauseFailureContract.View
{
    private PullToRefreshListView allTypeItemRefreshListView;

    private MultipleStatusView multipleStatusView;
    
    private String assemblyId = "";
    
    private String phenomenon = "";
    
    private String manualId = "";
    
    private CauseFailureAdapter causeFailureAdapter;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_cause_failure;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null)
        {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                manualId = bundle.getString(C.Suspension.KEY_MANUALID);
                assemblyId = bundle.getString(C.Suspension.PARAMS_ASSEMBLYID);
                phenomenon = bundle.getString(C.Suspension.KEY_PHENOMENON);
            }
        }
        allTypeItemRefreshListView = (PullToRefreshListView) findViewById(R.id.type_listview);
        multipleStatusView = (MultipleStatusView) findViewById(R.id.causefailure_multiplestatusview);
        allTypeItemRefreshListView.setOnRefreshListener(new PullToRefreshListViewListener());
        allTypeItemRefreshListView.setOnItemClickListener(new PullToRefreshListViewOnItemClickListener());
        causeFailureAdapter = new CauseFailureAdapter(this);
        mPresenter.getCauseFailureData(manualId, assemblyId, phenomenon, 1, 1000, false);
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCauseFailureData(manualId, assemblyId, phenomenon, 1, 1000, false);
            }
        });
    }

    @Override
    protected void initTitle() {
        if (titleBarView != null)
        {
            titleBarBackBtn.setVisibility(View.VISIBLE);
            backTxt.setVisibility(View.GONE);
            titleBarTitleTv.setVisibility(View.VISIBLE);
            titleBarTitleTv.setText(getString(R.string.cause_of_failure));
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
            mPresenter.getCauseFailureData(manualId, assemblyId, phenomenon, 1, 1000, true);
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
            Intent intent = new Intent(CauseFailureActivity.this, TreatmentMeasuresActivity.class);
            intent.putExtra(C.Suspension.KEY_MANUALID, causeFailureAdapter.getLists().get(position - 1).getManualId());
            intent.putExtra(C.CauseFailure.KEY_FAULT_REASON,
                    causeFailureAdapter.getLists().get(position - 1).getReason());
            startActivity(intent);
        }
    }
    
    @Override
    public void goBack()
    {
        super.goBack();
        Intent intent = new Intent(this, SuspensionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
    
    @Override
    public void onGetCauseFailureList(List<CauseFailureVO> causeFailureVOs)
    {
        multipleStatusView.showContent();
        allTypeItemRefreshListView.onRefreshComplete();
        causeFailureAdapter.setLists(causeFailureVOs);
        causeFailureAdapter.notifyDataSetChanged();
        allTypeItemRefreshListView.setAdapter(causeFailureAdapter);
    }
    
    @Override
    public void onCauseFailureNoDatas()
    {
        allTypeItemRefreshListView.onRefreshComplete();
        multipleStatusView.showEmpty();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        causeFailureAdapter.releaseContext();
    }
}
