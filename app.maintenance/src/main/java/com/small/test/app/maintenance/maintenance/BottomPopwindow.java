package com.small.test.app.maintenance.maintenance;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.small.test.app.maintenance.R;
import com.small.test.app.maintenance.adapter.ProvinceAdatper;
import com.small.test.app.maintenance.maintenance.data.ProvinceVO;
import com.small.test.appstub.mvp.Mvp;
import com.small.test.appstub.widget.pull2refresh.PullToRefreshBase;
import com.small.test.appstub.widget.pull2refresh.PullToRefreshGridView;

import java.util.List;

/**
 * Created by ztw on 2016/12/30.
 */

public class BottomPopwindow extends PopupWindow
{
    private static BottomPopwindow INSTANCE = null;

    private Button finishBtn;

    private View conentView;

    private FrameLayout outSideHideLayout;

    private PullToRefreshGridView pullToRefreshGridView;

    private ProvinceAdatper provinceAdatper;

    private MaintenanceRegistrationContract.View view;

    private ProvinceVO selectedProvinceVO;

    public static BottomPopwindow getInstance(LayoutInflater inflater)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BottomPopwindow(inflater);
        }
        return INSTANCE;
    }

    private BottomPopwindow()
    {
    }

    private BottomPopwindow(final LayoutInflater inflater)
    {
        Context context = inflater.getContext();
        conentView = inflater.inflate(R.layout.popwindow_choicecity, null);
        conentView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_ins));
        outSideHideLayout = (FrameLayout)conentView.findViewById(R.id.total_container);
        outSideHideLayout.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        finishBtn = (Button)conentView.findViewById(R.id.id_choice_yes);
        finishBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (BottomPopwindow.this.view != null)
                {
                    BottomPopwindow.this.view.onProvinceSelected(selectedProvinceVO);
                }
                if (selectedProvinceVO != null)
                {
                    dismiss();
                }
            }
        });
        pullToRefreshGridView = (PullToRefreshGridView)conentView.findViewById(R.id.province_pull_to_refresh_grid);
        provinceAdatper = new ProvinceAdatper(inflater);
        pullToRefreshGridView.setAdapter(provinceAdatper);
        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                selectedProvinceVO = provinceAdatper.getLists().get(position);
                provinceAdatper.setSelectedProvinceVO(selectedProvinceVO);
                provinceAdatper.notifyDataSetChanged();
            }
        });
        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView)
            {
                if (BottomPopwindow.this.view != null)
                {
                    BottomPopwindow.this.view.onProvincePull2Refresh();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView)
            {

            }
        });
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        pullToRefreshGridView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_bottom_in_2));
        setContentView(conentView);
        update();
    }

    public void showOrHidePopwindow(View targetView, List<ProvinceVO> provinceVOs, ProvinceVO selectedProvinceVO)
    {
        MaintenanceRegistrationContract.View view =
                (MaintenanceRegistrationContract.View) Mvp.getInstance().getView(MaintenanceRegistrationActivity.class);
        this.selectedProvinceVO = selectedProvinceVO;
        provinceAdatper.setLists(provinceVOs);
        provinceAdatper.setSelectedProvinceVO(selectedProvinceVO);
        provinceAdatper.notifyDataSetChanged();
        if (!isShowing())
        {
            showAtLocation(targetView, Gravity.BOTTOM, 0, 0);
        }
        pullToRefreshGridView.onRefreshComplete();
        this.view = view;
    }

    public void setProvincesOnNoDatas(View emptyView)
    {
        pullToRefreshGridView.setEmptyView(emptyView);
    }
}
