package com.musicdownman.d.musicdownman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zzy on 2018/2/14.
 */

public class songAdapter extends BaseAdapter {
    private Context context;
    private List<SongData> SongInfoList;
    public songAdapter(Context context,List<SongData> songInfoList ) {
        this.context=context;
        this.SongInfoList=songInfoList;
    }

    @Override
    public int getCount() {
        return SongInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return SongInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.musicview,null);
        }
        TextView TextViewSongname=(TextView)view.findViewById(R.id.textView_SongName);
        TextView TextViewSingername=(TextView)view.findViewById(R.id.textView_SingerName);
        ImageView ImageViewImageView_album=(ImageView) view.findViewById(R.id.ImageView_album);
        SongData songDatainfo=SongInfoList.get(i);
        TextViewSongname.setText(songDatainfo.getSongname());
        TextViewSingername.setText(songDatainfo.getSingername());
        Qmusic.getalbummidim(songDatainfo.getAlbum_mid(),ImageViewImageView_album);
        return view;

    }
}
