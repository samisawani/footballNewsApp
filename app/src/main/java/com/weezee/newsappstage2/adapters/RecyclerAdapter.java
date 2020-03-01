package com.weezee.newsappstage2.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.weezee.newsappstage2.R;
import com.weezee.newsappstage2.pojo.StoryItem;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private  ArrayList<StoryItem> storyItems;
    private Activity callingActivity;

    public RecyclerAdapter(ArrayList<StoryItem> storyItems, Activity activity) {
        this.storyItems = storyItems;
        this.callingActivity =activity;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.story_item, viewGroup, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final StoryItem storyItem = storyItems.get(i);
        viewHolder.title.setText(storyItem.getTitle());
        viewHolder.date.setText(storyItem.getDate().substring(0,10));
        viewHolder.authorName.setText(storyItem.getAuthorName());
        viewHolder.sectionName.setText(storyItem.getSectionName());
        if(!storyItem.hasNoImageURL()){
            viewHolder.thumbnail.setVisibility(View.VISIBLE);
            Glide.with(callingActivity)
                    .load(storyItem.getImageURL())
                    .into(viewHolder.thumbnail);
        }



    }

    @Override
    public int getItemCount() {
        return storyItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;

    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView sectionName;
        private TextView authorName;
        private TextView date;
        private TextView title;
        private ImageView thumbnail;
        private Button moreButton;

        ViewHolder(View itemView) {
            super(itemView);
            sectionName = itemView.findViewById(R.id.section_name);
            authorName=itemView.findViewById(R.id.author_name);
            date=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.story_title);
            thumbnail=itemView.findViewById(R.id.story_thumbnail);
            moreButton=itemView.findViewById(R.id.more_button);
            moreButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String url = RecyclerAdapter.this.storyItems.get(getAdapterPosition()).getWebURL();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            if (i.resolveActivity(callingActivity.getPackageManager()) != null) {
                callingActivity.startActivity(i);
            }
        }
    }

}
