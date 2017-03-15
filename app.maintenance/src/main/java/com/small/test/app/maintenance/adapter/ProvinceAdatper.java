package com.small.test.app.maintenance.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.small.test.app.maintenance.R;
import com.small.test.app.maintenance.maintenance.data.ProvinceVO;
import com.small.test.appstub.adapter.AdapterBase;
import com.small.test.appstub.adapter.ViewHolder;

public class ProvinceAdatper extends AdapterBase<ProvinceVO>
{
    private LayoutInflater inflater;
    
    private ProvinceVO selectedProvinceVO;
    
    public ProvinceAdatper(LayoutInflater inflater)
    {
        this.inflater = inflater;
    }
    
    @Override
    protected View getExView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_city_shortname, parent, false);
        }
        TextView nameTxt = ViewHolder.get(convertView, R.id.short_name_btn);
        nameTxt.setText(lists.get(position).getProvinceAbbr());
        nameTxt.setTag(lists.get(position).getProvinceId());
        if (selectedProvinceVO != null)
        {
            if (lists.get(position).getProvinceId().equals(selectedProvinceVO.getProvinceId()))
            {
                convertView.setBackgroundResource(R.drawable.gray_bg);
            }
            else
            {
                convertView.setBackgroundResource(R.drawable.grid_item_bg);
            }
        }
        else
        {
            convertView.setBackgroundResource(R.drawable.grid_item_bg);
        }
        return convertView;
    }
    
    public void setSelectedProvinceVO(ProvinceVO selectedProvinceVO)
    {
        this.selectedProvinceVO = selectedProvinceVO;
    }
}
