package com.example.v_player;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MyAdaptor extends RecyclerView.Adapter<VideoHolder>{

    Context context;
    ArrayList<File> myVideoList;

    public MyAdaptor(Context context, ArrayList<File> myVideoList) {
        this.context = context;
        this.myVideoList = myVideoList;
    }

    @Override
    public VideoHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_layout,parent,false);
        return new VideoHolder(mView);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        holder.fileName.setText(MainActivity.fileArrayList.get(position).getName());
        Bitmap thambBit= ThumbnailUtils.createVideoThumbnail(myVideoList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.imageThambnail.setImageBitmap(thambBit);

        holder.cardView.setOnClickListener(x->{
            Intent i=new Intent(context,videoPlayerActivity.class);
            i.putExtra("position",holder.getAdapterPosition());
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        if(myVideoList.size()>0)
        {
            return myVideoList.size();
        }
        else
        {
            return 1;
        }
    }
}
class VideoHolder extends RecyclerView.ViewHolder{

    TextView fileName;
    ImageView imageThambnail;
    CardView cardView;
    public VideoHolder(@NonNull View itemView) {
        super(itemView);
        fileName=itemView.findViewById(R.id.text);
        imageThambnail=itemView.findViewById(R.id.timage);
        cardView=itemView.findViewById(R.id.mycard);
    }
}
