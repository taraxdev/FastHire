package com.wtw.waytoworkers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class postAdapter extends RecyclerView.Adapter<postAdapter.postViewHolder> {

    private Context ctx;
    private List<Post> postList;
    private  OnItemClickListner mListner;

    public postAdapter(Context ctx, List<Post> postList) {
        this.ctx = ctx;
        this.postList = postList;
    }
    public  interface  OnItemClickListner{
        void  onDeleteClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner=listner;
    }
    @NonNull
    @Override
    public postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.post_view, parent, false);
        return new postAdapter.postViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull postViewHolder holder, int position) {
        Post post=postList.get(position);
        holder.username.setText(post.getCuname());
        holder.userid.setText(post.getCuid());
        holder.caption.setText(post.getCaption());
        //LINK FOR DP
        String driveLink=post.dpurl;
        if (driveLink.length()!=0 && driveLink.length()>=65) {
            String substr = " ";
            substr = driveLink.substring(32, 65);
            String loadUrl = "https://docs.google.com/uc?id=" + substr;
            Glide.with(holder.itemView.getContext()).load(loadUrl).circleCrop().into(holder.dp);
        }
        else
        {
            holder.dp.setImageResource(R.drawable.name_vector);
        }
        //LINK FOR POST
        String drivePostLink=post.posturl;
        if (drivePostLink!=null) {
            String substr = " ";
            substr = drivePostLink.substring(32,65);
            String loadUrl = "https://docs.google.com/uc?id=" + substr;
            Glide.with(holder.itemView.getContext()).load(loadUrl).centerCrop().into(holder.postPic);
        }
        else
        {
            holder.postPic.setImageResource(R.drawable.wtw);
        }

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class postViewHolder extends RecyclerView.ViewHolder{

        TextView username,userid,caption;
        ImageView dp,postPic;
        ImageButton deletebtn;

        public postViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);
            username=itemView.findViewById(R.id.postUserName);
            userid=itemView.findViewById(R.id.postUserId);
            caption=itemView.findViewById(R.id.postViewCaption);
            dp=itemView.findViewById(R.id.postUserDp);
            postPic=itemView.findViewById(R.id.postPicture);
            deletebtn=itemView.findViewById(R.id.postDelete);

            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner!=null)
                    {
                        int position =getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listner.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
