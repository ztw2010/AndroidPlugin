package com.small.test.app.historydata.historydata;


import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.test.app.historydata.R;
import com.small.test.app.historydata.historydata.data.CategoryVO;
import com.small.test.appstub.adapter.IViewItem;


public class TwoColumnItem implements IViewItem, View.OnClickListener
{
    private View twoItemView;

    private TextView firstNameTxt;

    private TextView secondNameTxt;

    private ImageView firstNameImg;

    private ImageView secondNameImg;

    private RelativeLayout firstLayout;

    private RelativeLayout secondLayout;
    
    private Context context;
    
    private HistoryContract.Presenter presenter;
    
    public TwoColumnItem(Context context, HistoryContract.Presenter presenter)
    {
        this.context = context;
        this.presenter = presenter;
        this.init();
    }
    
    public void initWithData(CategoryVO systemChildModel, CategoryVO systemChildModel2)
    {
        String name1 = null;
        String name2 = null;
        if (systemChildModel != null)
        {
            name1 = systemChildModel.getComponentName();
        }
        if (systemChildModel2 != null)
        {
            name2 = systemChildModel2.getComponentName();
        }
        if (!TextUtils.isEmpty(name1))
        {
            firstNameTxt.setText(name1);
            firstNameTxt.setTag(systemChildModel.getComponentCode());
        }
        if (!TextUtils.isEmpty(name2))
        {
            secondNameTxt.setText(name2);
            secondNameTxt.setTag(systemChildModel2.getComponentCode());
        }
    }
    
    @Override
    public void init()
    {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.twoItemView = layoutInflater.inflate(R.layout.two_column_item, null);
        firstNameTxt = (TextView) twoItemView.findViewById(R.id.txt_name_first);
        secondNameTxt = (TextView) twoItemView.findViewById(R.id.txt_name_second);
        firstNameImg = (ImageView) twoItemView.findViewById(R.id.name_firt_img);
        secondNameImg = (ImageView) twoItemView.findViewById(R.id.name_second_img);
        firstLayout = (RelativeLayout) twoItemView.findViewById(R.id.first_layout);
        firstLayout.setOnClickListener(this);
        secondLayout = (RelativeLayout) twoItemView.findViewById(R.id.two_layout);
        secondLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.first_layout:
                firstNameImg.setVisibility(View.VISIBLE);
                secondNameImg.setVisibility(View.GONE);
                firstNameTxt.setTextColor(context.getResources().getColor(R.color.sys_type_green));
                secondNameTxt.setTextColor(Color.BLACK);
                presenter.selectedItem(this);
                break;
            case R.id.two_layout:
                if (!TextUtils.isEmpty(secondNameTxt.getText().toString()))
                {
                    firstNameImg.setVisibility(View.GONE);
                    secondNameImg.setVisibility(View.VISIBLE);
                    firstNameTxt.setTextColor(Color.BLACK);
                    secondNameTxt.setTextColor(context.getResources().getColor(R.color.sys_type_green));
                    presenter.selectedItem(this);
                }
                break;
            default:
                break;
        }
    }
    
    @Override
    public void itemSelect()
    {
        
    }
    
    @Override
    public void setVisibility(int visibility)
    {
        twoItemView.setVisibility(visibility);
    }
    
    @Override
    public void resetItemViewData()
    {
        this.firstNameTxt.setText(null);
        this.secondNameTxt.setText(null);
    }

    @Override
    public View getView() {
        return twoItemView;
    }

    public void resetSelected()
    {
        firstNameImg.setVisibility(View.GONE);
        secondNameImg.setVisibility(View.GONE);
        firstNameTxt.setTextColor(Color.BLACK);
        secondNameTxt.setTextColor(Color.BLACK);
    }
    
    public String getSelectedStr()
    {
        if (View.VISIBLE == firstNameImg.getVisibility())
        {
            return firstNameTxt.getText().toString();
        }
        else if (View.VISIBLE == secondNameImg.getVisibility())
        {
            return secondNameTxt.getText().toString();
        }
        return null;
    }
    
    public String getSelectedId()
    {
        if (View.VISIBLE == firstNameImg.getVisibility())
        {
            return String.valueOf(firstNameTxt.getTag().toString());
        }
        else if (View.VISIBLE == secondNameImg.getVisibility())
        {
            return String.valueOf(secondNameTxt.getTag().toString());
        }
        return "";
    }
    
    public void setFirstItemSelected()
    {
        firstNameImg.setVisibility(View.VISIBLE);
        secondNameImg.setVisibility(View.GONE);
        firstNameTxt.setTextColor(context.getResources().getColor(R.color.sys_type_green));
        secondNameTxt.setTextColor(Color.BLACK);
    }
    
    public void setSecondItemSelected()
    {
        firstNameImg.setVisibility(View.GONE);
        secondNameImg.setVisibility(View.VISIBLE);
        firstNameTxt.setTextColor(Color.BLACK);
        secondNameTxt.setTextColor(context.getResources().getColor(R.color.sys_type_green));
    }
    
    public String getFirstItemId()
    {
        Object object = firstNameTxt.getTag();
        if (object != null)
        {
            return String.valueOf(object.toString());
        }
        return null;
    }
    
    public String getSecondItemId()
    {
        Object object = secondNameTxt.getTag();
        if (object != null)
        {
            return String.valueOf(object.toString());
        }
        return "";
    }
    
    public void releaseContext()
    {
        this.context = null;
    }
}
