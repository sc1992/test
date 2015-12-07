package com.example.sc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sc.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sc on 15-9-30.
 */
public class AutoAdapter extends BaseAdapter{
    private Context mContext;

    private LayoutInflater mInflater;

    private List<String> list=new ArrayList<>();

    private int hidePos=-1;

    private int movePos=-1;

    public AutoAdapter(Context context) {
        mContext=context;
        mInflater=LayoutInflater.from(context);
    }

    public void setDatas(List<String> list){
        if(list==null){
            this.list=new ArrayList<>();
        }else{
            this.list=list;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override

    public String getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setHidePos(final int pos){
        this.hidePos=pos;
        this.movePos=pos;
        notifyDataSetChanged();
    }

    public int getHidePos(){
        return  hidePos;
    }

    public boolean movePos(final int pos){
        if(pos!=movePos&&pos>-1&&pos<getCount()&&hidePos>-1){
            String item=list.remove(hidePos);
            list.add(pos,item);
            setHidePos(pos);
            return  true;
        }
        return false;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=mInflater.inflate(R.layout.item_grid,null);
            holder.mTextView=(TextView)view.findViewById(R.id.num);
            view.setTag(holder);
        }else {
            holder=(ViewHolder)view.getTag();
        }
        String title=getItem(i);
        holder.mTextView.setText(title);
        if(hidePos==i){
            view.setVisibility(View.INVISIBLE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
        return view;
    }
    class ViewHolder{
        TextView mTextView;
    }

}
