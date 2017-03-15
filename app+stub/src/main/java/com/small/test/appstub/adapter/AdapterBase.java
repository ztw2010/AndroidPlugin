package com.small.test.appstub.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AdapterBase<T> extends BaseAdapter
{
    
    protected List<T> lists = new ArrayList<T>();
    
    public List<T> getLists()
    {
        return lists;
    }
    
    public void setLists(List<T> lists)
    {
        this.lists = lists;
    }
    
    public void appendToTopLists(List<T> list)
    {
        if (list != null && lists != null)
        {
            lists.addAll(0, list);
            notifyDataSetChanged();
        }
    }
    
    public void appendToLists(T t)
    {
        if (t != null && lists != null)
        {
            lists.add(t);
            notifyDataSetChanged();
        }
    }
    
    public void appendToLists(List<T> list)
    {
        if (list != null && lists != null)
        {
            lists.addAll(list);
            notifyDataSetChanged();
        }
    }
    
    public void appendToTopLists(T t)
    {
        if (t != null && lists != null)
        {
            lists.add(0, t);
            notifyDataSetChanged();
        }
    }
    
    public void clear()
    {
        if (lists == null)
        {
            return;
        }
        lists.clear();
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount()
    {
        return lists != null ? lists.size() : 0;
    }
    
    @Override
    public Object getItem(int position)
    {
        if (position > lists.size() - 1)
        {
            return null;
        }
        return lists.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getExView(position, convertView, parent);
    }
    
    protected abstract View getExView(int position, View convertView, ViewGroup parent);
    
}
