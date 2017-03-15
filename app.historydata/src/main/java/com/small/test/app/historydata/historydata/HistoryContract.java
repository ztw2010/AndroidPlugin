package com.small.test.app.historydata.historydata;

import com.small.test.app.historydata.historydata.data.CategoryVO;
import com.small.test.appstub.mvp.IBasePresenter;
import com.small.test.appstub.mvp.IBaseView;

import java.util.List;


public interface HistoryContract
{
    interface View extends IBaseView
    {
        /**
         * 当数据加载完成后显示的界面
         * @param views
         */
        public void onAddSystemDataView(List<android.view.View> views);
        
        /**
         * 安装位置、一级系统、二级系统没有数据时展示一个空视图
         */
        public void onCategoryNoData();
        
        /**
         * 总成没有数据时展示一个空视图
         */
        public void onZongChengNoData();
        
        /**
         * 设置三个Tab页的文字
         * @param sysLocationBtnText
         * @param sysFirstBtnText
         */
        public void onSetTabBtnText(String sysLocationBtnText, String sysFirstBtnText, String sysSecondBtnText);
        
        /**
         * 总成数据获取时
         * @param categoryDOs
         */
        public void onZongChengDataLoaded(List<CategoryVO> categoryDOs);
        
        /**
         * 设置选中的Tab的背景色
         * @param selectedTabIndex
         */
        public void onTabSelected(int selectedTabIndex);
    }
    
    interface Presenter extends IBasePresenter
    {
        /**
         * 安装位置接口
         * @param btnIndex
         * @param classCode
         * @param pageNo
         * @param recordNum
         */
        public void getSystemLocationDatas(int btnIndex, int classCode, int pageNo, int recordNum, boolean isRefresh);
        
        /**
         * 得到一级系统、二级系统、总成数据接口
         * @param btnIndex
         * @param classCode
         * @param pageNo
         * @param recordNum
         */
        public void getSysOneAndSysTwoAndZongChengDatas(int btnIndex, int classCode, int pageNo, int recordNum,
                                                        boolean isRefresh);
        
        public void selectedItem(TwoColumnItem twoColumnItem);
        
        public int getBtnIndex();
    }
}
