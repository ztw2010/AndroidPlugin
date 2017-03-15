package com.small.test.app.historydata.treatmentmeasures;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.small.test.app.historydata.R;
import com.small.test.app.historydata.causeFailure.CauseFailureActivity;
import com.small.test.app.historydata.historydata.RepairActivity;
import com.small.test.app.historydata.suspension.SuspensionActivity;
import com.small.test.app.historydata.treatmentmeasures.data.MeaDetailVO;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.appstub.mvp.ManagerActivity;
import com.small.test.appstub.mvp.MvpActivity;


public class TreatmentMeasuresActivity extends MvpActivity<TreatmentMeasuresPresenter>
    implements TreatmentMeasuresContract.View
{
    private String manualId = "";
    
    private String faltReason = "";

    private TextView measureContentTxt;

    private FrameLayout contentContaier;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_treatment_measures;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null)
        {
            manualId = intent.getStringExtra(C.Suspension.KEY_MANUALID);
            faltReason = intent.getStringExtra(C.CauseFailure.KEY_FAULT_REASON);
        }
        measureContentTxt = (TextView) findViewById(R.id.txt_content);
        contentContaier = (FrameLayout) findViewById(R.id.content_contaier);
        mPresenter.getTreatmentMeasures(manualId, faltReason);
    }

    @Override
    protected void initTitle() {
        if (titleBarView != null)
        {
            titleBarBackBtn.setVisibility(View.VISIBLE);
            backTxt.setVisibility(View.GONE);
            titleBarTitleTv.setVisibility(View.VISIBLE);
            titleBarTitleTv.setText(getString(R.string.mainten_treatment));
            titleBarOtherTv.setVisibility(View.VISIBLE);
            titleBarOtherTv.setText(getString(R.string.close));
        }
    }

    @Override
    public IBaseView getBaseView()
    {
        return this;
    }
    
    @Override
    public void onGetTreatmentMeasures(MeaDetailVO meaDetailVO)
    {
        String measure = meaDetailVO.getMeasure();
        measureContentTxt.setText(String.format("%s%s", "  ", measure));
    }
    
    @Override
    public void onGetTreatmentMeasuresNoDatas()
    {
    }
    
    @Override
    public void goBack()
    {
        super.goBack();
        Intent intent = new Intent(this, CauseFailureActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
    
    @Override
    public void processOther()
    {
        super.processOther();
        ManagerActivity.getInstance().killActivity(CauseFailureActivity.class, SuspensionActivity.class);
        Intent intent = new Intent(this, RepairActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
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
}
