package com.small.test.app.historydata.suspension;


import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.small.test.app.historydata.R;
import com.small.test.app.historydata.suspension.data.FaultListVO;
import com.small.test.appstub.adapter.AdapterBase;
import com.small.test.appstub.adapter.ViewHolder;

public class FaultListAdapter extends AdapterBase<FaultListVO>
{
    private Activity context;
    
    private LinearLayout.LayoutParams params;
    
    public FaultListAdapter(Activity context)
    {
        this.context = context;
        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 3);
        params.setMargins(60, 0, 0, 0);
    }
    
    @Override
    protected View getExView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.repaire_common_item, parent, false);
        }
        TextView textName = ViewHolder.get(convertView, R.id.txt_name);
        textName.setTextColor(ContextCompat.getColor(context, R.color.black));//6.0以后getResources().getColor()方法被废弃了
        textName.setText(lists.get(position).getPhenomenon());
        View diverView = ViewHolder.get(convertView, R.id.title_line);
        diverView.setBackgroundColor(ContextCompat.getColor(context, R.color.black_line));
        diverView.setLayoutParams(params);
        ImageView rightArrowImg = ViewHolder.get(convertView, R.id.right_arrow_img);
        rightArrowImg.setVisibility(View.VISIBLE);
        ImageView rightCheckBoxImg = ViewHolder.get(convertView, R.id.right_checkbox_img);
        rightCheckBoxImg.setVisibility(View.GONE);
        return convertView;
    }
    
    public void releaseContext()
    {
        this.context = null;
    }
    
}
