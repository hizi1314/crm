package com.tw.crm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tw.crm.R;
import com.tw.crm.entity.TrackEntity;

import java.util.List;

/**
 * Created by hizi on 2017/7/20.
 */

public class TrackAdapter extends ArrayAdapter<TrackEntity> {
    private int resourceId;
    public TrackAdapter(Context content ,int textViewResourceId,List<TrackEntity> objects){
        super(content,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TrackEntity trackEntity=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tracakPname=(TextView) view.findViewById(R.id.track_pname);
        TextView tracakRfidtid=(TextView) view.findViewById(R.id.track_rfidtid);
        TextView tracakTime0=(TextView) view.findViewById(R.id.track_time0);
        TextView tracakTime1=(TextView) view.findViewById(R.id.track_time1);
        TextView tracakTime2=(TextView) view.findViewById(R.id.track_time2);
        TextView tracakTime3=(TextView) view.findViewById(R.id.track_time3);
        tracakPname.setText(trackEntity.getPname());
        tracakRfidtid.setText(trackEntity.getRfidtid());
        tracakTime0.setText(trackEntity.getTime0());
        tracakTime1.setText(trackEntity.getTime1());
        tracakTime2.setText(trackEntity.getTime2());
        tracakTime3.setText(trackEntity.getTime3());
        return view ;
    }

}
