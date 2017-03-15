package com.small.test.appstub.adapter;

import android.view.View;

/**
 * Created by ztw on 2016/12/12.
 */

public interface IViewItem
{
    public void init();

    public void itemSelect();

    public void setVisibility(int visibility);

    public void resetItemViewData();

    public View getView();
}
