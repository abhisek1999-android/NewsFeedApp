package com.abhisekseal.enno2020mscs001.curaj.newsfeedapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.abhisekseal.enno2020mscs001.curaj.newsfeedapp.Bean.News;
import com.abhisekseal.enno2020mscs001.curaj.newsfeedapp.R;
import com.bumptech.glide.Glide;

import org.ocpsoft.prettytime.PrettyTime;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.MyViewHoder>{
    List<News> list = Collections.emptyList();
    Context context;


    public NewsRecyclerAdapter(List<News> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the layout
        View newsView = inflater.inflate(R.layout.news_layout,parent, false);
        return new MyViewHoder(newsView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {

        holder.title.setText(list.get(position).getTitle());
        holder.source.setText(list.get(position).getSource());
        holder.source.setText(list.get(position).getSource());

        try {
            
            Date date=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(list.get(position).getPublishedAt());
            PrettyTime p = new PrettyTime();
            holder.publishedAt.setText(p.format(date));

        } catch (ParseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        holder.source.setText(list.get(position).getSource());






        holder.description.setText(list.get(position).getDescription());
        Glide.with(context).load(list.get(position).getImage()).into(holder.imageNews);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    protected class MyViewHoder extends RecyclerView.ViewHolder {


        View mView;
        TextView title,description,source,publishedAt;
        ImageView imageNews;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);

                mView=itemView;
                title=itemView.findViewById(R.id.titleNews);
                description=itemView.findViewById(R.id.newsDescription);
                source=itemView.findViewById(R.id.sourceNews);
                imageNews=itemView.findViewById(R.id.newsImage);
                publishedAt=itemView.findViewById(R.id.publishedAt);
        }
    }
}

